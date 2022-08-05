package com.roms.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.roms.auth.config.CustomUserDetailsService;
import com.roms.auth.config.JwtUtil;
import com.roms.auth.exception.LoginFailureException;
import com.roms.auth.model.AuthenticationRequest;
import com.roms.auth.model.AuthenticationResponse;

@RestController
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Autowired
	private JwtUtil jwtUtil;
	
	@PostMapping("/auth/authenticate")
	public ResponseEntity<AuthenticationResponse> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
			throws LoginFailureException {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		}
		catch (Exception e) {
			throw new LoginFailureException("Login Failed");
		}
		
		UserDetails userdetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		String token = jwtUtil.generateToken(userdetails);
		return ResponseEntity.ok(new AuthenticationResponse(token));
	}
	
	@PostMapping("/auth/validate")
    public String validate(@RequestHeader("Authorization") String token){
		return jwtUtil.validateToken(token);
    }
	@PostMapping("/auth/decode")
    public ResponseEntity<String> decodeToken(@RequestBody String token) {
		return ResponseEntity.ok(jwtUtil.decodeToken(token).getId());
    }
}
