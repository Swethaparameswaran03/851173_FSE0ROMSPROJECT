package com.roms.auth.exception;

public class LoginFailureException extends Exception {
	private static final long serialVersionUID = 1L;

	public LoginFailureException(String message){
		super(message);
	}
}
