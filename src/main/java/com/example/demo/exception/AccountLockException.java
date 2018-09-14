package com.example.demo.exception;

public class AccountLockException extends RuntimeException {
	
	private String account;
	private String type;
	
	
	public AccountLockException(String account, String type) {
		super();
		this.account = account;
		this.type = type;
	}
	
	public String toString() {
		return "AccountLockException: account = " + account
				 + " type = " + type;
		
	}
	
	

}
