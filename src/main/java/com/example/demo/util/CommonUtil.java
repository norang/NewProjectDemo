package com.example.demo.util;

import java.util.regex.Pattern;

public class CommonUtil {
	
	public static String ACCOUNT_STATUS_BLOCKED_IP = "IP is blocked";
	public static String ACCOUNT_STATUS_BLOCKED_ACCOUNT = "Account is blocked";
	public static String DELIMITER = "~";
	
	public static char FALSE = 'N';
	public static char TRUE  = 'Y';
	
	public static char SUCCESS = 'S';
	public static char FAIL	   = 'F';
	
	public static int MAX_FAIL_ATTEMPT = 3;
	
	
	public static boolean stringContainsNumber( String s )
	{
	    return Pattern.compile( "[0-9]" ).matcher( s ).find();
	}

}
