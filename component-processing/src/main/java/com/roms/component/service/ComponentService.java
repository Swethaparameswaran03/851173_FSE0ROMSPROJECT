package com.roms.component.service;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.roms.component.entity.CompleteProcess;
import com.roms.component.entity.ProcessRequest;
import com.roms.component.entity.ProcessResponse;
import com.roms.component.repository.CompleteProcessRepository;
import com.roms.component.repository.ProcessRequestRepository;
import com.roms.component.repository.ProcessResponseRepository;
import com.roms.component.wf.RepairIntegralWF;
import com.roms.component.wf.ReplacementAccessoryWF;

@Service
public class ComponentService implements RepairIntegralWF, ReplacementAccessoryWF {

	@Autowired
	Environment env;

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();

	}

	@Autowired
	EntityManager em;

	@Autowired
	private ProcessRequestRepository processRequestRepository;
	@Autowired
	private ProcessResponseRepository processResponseRepository;
	@Autowired
	private CompleteProcessRepository completeProcessRepository;
	Logger logger = LoggerFactory.getLogger(ComponentService.class);

	public int validateToken(String token) {
		URL url = null;
		HttpURLConnection urlConnection = null;
		try {
			url = new URL(env.getProperty("api.url.common") + "auth/validate");
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setConnectTimeout(5000);
			urlConnection.setRequestProperty("Content-Type", "application/json");
			urlConnection.setRequestProperty("Accept", "application/json");
			urlConnection.setDoInput(true);
			urlConnection.setDoOutput(true);
			urlConnection.setRequestProperty("Authorization", token);
			urlConnection.setRequestMethod("POST");
			OutputStream wr = urlConnection.getOutputStream();
			wr.flush();
			wr.close();

			String result;
			BufferedInputStream bis = new BufferedInputStream(urlConnection.getInputStream());
			ByteArrayOutputStream buf = new ByteArrayOutputStream();
			int result2 = bis.read();
			while (result2 != -1) {
				buf.write((byte) result2);
				result2 = bis.read();

			}
			result = buf.toString();
			if (result.contains("Valid"))
				return 1;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (urlConnection != null)
				urlConnection.disconnect();
		}
		return 0;
	}

	public String decodeToken(String token) {

		URL url = null;
		HttpURLConnection urlConnection = null;
		try {
			url = new URL(env.getProperty("api.url.common") + "auth/decode");
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setConnectTimeout(5000);
			urlConnection.setRequestProperty("Content-Type", "application/json");
			urlConnection.setRequestProperty("Accept", "application/json");
			urlConnection.setDoInput(true);
			urlConnection.setDoOutput(true);
			urlConnection.setRequestMethod("POST");

			OutputStream wr = urlConnection.getOutputStream();
			wr.write((token.contains("Bearer") ? token.substring(6, token.length()) : token).getBytes());
			wr.flush();
			wr.close();
			String result;
			BufferedInputStream bis = new BufferedInputStream(urlConnection.getInputStream());
			ByteArrayOutputStream buf = new ByteArrayOutputStream();
			int result2 = bis.read();
			while (result2 != -1) {
				buf.write((byte) result2);
				result2 = bis.read();
			}
			result = buf.toString();
			logger.info(result);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (urlConnection != null)
				urlConnection.disconnect();
		}
		return null;

	}

	public ProcessResponse processReturnRequest(ProcessRequest processRequestInput) {
		int processingDuration = 5;
		int quantity = Integer.parseInt(processRequestInput.getQuantity());
		LocalDate date;

		ProcessResponse processResponse = new ProcessResponse();

		String componentType = processRequestInput.getComponentType();

		if (componentType.equals("Integral")) {
			processingDuration = RepairIntegralWF.PROCESSINGDURATION;
			processResponse.setProcessingCharge(RepairIntegralWF.PROCESSINGCHARGE * quantity);
			if (processRequestInput.isPriority()) {
				processingDuration = 2;
				processResponse.setProcessingCharge(processResponse.getProcessingCharge() + 200.0 * quantity);
			}
		}
		if (componentType.equals("Accessory")) {
			processingDuration = ReplacementAccessoryWF.PROCESSINGDURATION;
			processResponse.setProcessingCharge(ReplacementAccessoryWF.PROCESSINGCHARGE * quantity);

		}
		double packagingAndDeliveryCharge = getPackagingAndDelivery(processRequestInput.getComponentType(),
				Integer.parseInt(processRequestInput.getQuantity()));

		date = LocalDate.now().plusDays(processingDuration);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		processResponse.setDateOfDelivery(formatter.format(date));
		processResponse.setRequestId(UUID.randomUUID().toString());
		processResponse.setUserid(processRequestInput.getUserid());
		processResponse.setProcessid(processRequestInput.getProcessid());
		processResponse.setPackagingAndDeliveryCharge(packagingAndDeliveryCharge);
		processRequestInput = processRequestRepository.save(processRequestInput);
		processResponse.setProcessid(processRequestInput.getProcessid());
		processResponseRepository.save(processResponse);
		return processResponse;
	}

	private double getPackagingAndDelivery(String componentType, int quantity) {
		try {
			String result = restTemplate().getForObject(env.getProperty("api.url.common")
					+ "compute/packagingdeliverycost?componentType=" + componentType + "&quantity=" + quantity,
					String.class);
			return Double.parseDouble(result);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return 0;
	}

	public String completeProcessing(CompleteProcess completeProcess) {
		Query q = em.createNativeQuery("SELECT C.REQUESTID FROM COMPLETEPROCESS  C WHERE C.REQUESTID=:requestid");
		q.setParameter("requestid", completeProcess.getRequestid());
		if (!q.getResultList().isEmpty()) {
			return "Transaction has already completed!";
		}
		if (completeProcess.getCreditLimit() > completeProcess.getProcessingCharge()) {
			completeProcessRepository.save(completeProcess);
			return "Payment Completed Successfully";
		} else
			return "Oops! You have exceeded the limit of your card";
	}

	public List<CompleteProcess> completeReturnRequestList(String userid) {

		Query q = em.createNativeQuery(
				"SELECT C.* FROM COMPLETEPROCESS  C INNER JOIN PROCESSRESPONSE P ON C.REQUESTID=P.REQUESTID WHERE P.USERID=:userid",
				CompleteProcess.class);
		q.setParameter("userid", userid);
		@SuppressWarnings("unchecked")
		List<CompleteProcess> result = q.getResultList();

		for (CompleteProcess c : result) {
			String cardno = c.getCardnumber();
			c.setCardnumber("XXXX-XXXX-" + cardno.split("-")[2]);
		}

		return result;
	}

}
