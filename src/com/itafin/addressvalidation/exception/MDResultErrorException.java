package com.itafin.addressvalidation.exception;

/**
 * 
 * @author 375149
 *
 */
public class MDResultErrorException extends Exception {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public MDResultErrorException() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public MDResultErrorException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public MDResultErrorException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public MDResultErrorException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public MDResultErrorException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	

}