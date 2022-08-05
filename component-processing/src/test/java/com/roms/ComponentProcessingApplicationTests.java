package com.roms;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.roms.component.entity.CompleteProcess;

import com.roms.component.entity.ProcessRequest;
import com.roms.component.entity.ProcessResponse;
import com.roms.component.service.ComponentService;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ComponentProcessingApplicationTests {

	@Autowired
	ComponentService componentService;
	@Test
	void tokenFailTest() {
		assertEquals(0,componentService.validateToken("demo"));
	}
	@Test
	void decodeTokenTest() {
		assertEquals("1",componentService.decodeToken("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhYmMiLCJpc1VzZXIiOnRydWUsImV4cCI6MTY1MjU1NDI1NiwiaWF0IjoxNjUyNTM2MjU2fQ.hjFXkuhZL2Gubtfn3OnDezdWAXhUIjtU8UEuaK6TJIhyhTRWcWPjuVRfYf84PIgAN0NIoQ_2kNf2WoQ4QFyIzA"));
	}
	
	@Test
	void processReturnRequestTest() {
		 ProcessRequest p=new ProcessRequest("1","abc","1","89897767","Integral","abc","2",true);
		 
		 ProcessResponse pr=componentService.processReturnRequest(p);
		 
		 assertEquals(true,pr!=null);
	}

@Test
	void completeProcessingTest() {
	CompleteProcess c=new CompleteProcess();
             c.setCardnumber("ABC-DEF-GHI");
String reqId="one";
c.setRequestid(reqId);
c.setCreditLimit(100);
c.setProcessingCharge(1000);
	 assertEquals("Oops! You have exceeded the limit of your card",componentService.completeProcessing(c));
c.setCreditLimit(100000);
assertEquals("Payment Completed Successfully",componentService.completeProcessing(c));
assertEquals("Transaction has already completed!",componentService.completeProcessing(c));


	}	 
@Test
void completeReturnRequestListTest()
{
assertNotNull(componentService.completeReturnRequestList("1"));
	}

}