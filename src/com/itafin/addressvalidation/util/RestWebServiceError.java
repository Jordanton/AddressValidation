/*
 * Copyright 2018 LATAX, Inc. All rights reserved.
 *
*/
package com.itafin.addressvalidation.util;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;

/**
 * REST Web Service Error
 * 
 * 
 * @author 375149
 *
 */
public class RestWebServiceError {

	private HttpStatus status;
	private String message;
	private List<String> errors;

	//

	public RestWebServiceError() {
		super();
	}

	/**
	 * 
	 * @param status
	 * @param message
	 * @param errors
	 */
	public RestWebServiceError(final HttpStatus status, final String message, final List<String> errors) {
		super();
		this.status = status;
		this.message = message;
		this.errors = errors;
	}

	/**
	 * 
	 * @param status
	 * @param message
	 * @param error
	 */
	public RestWebServiceError(final HttpStatus status, final String message, final String error) {
		super();
		this.status = status;
		this.message = message;
		errors = Arrays.asList(error);
	}

	/**
	 * 
	 * @return
	 */
	public HttpStatus getStatus() {
		return status;
	}

	/**
	 * 
	 * @param status
	 */
	public void setStatus(final HttpStatus status) {
		this.status = status;
	}

	/**
	 * 
	 * @return
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * 
	 * @param message
	 */
	public void setMessage(final String message) {
		this.message = message;
	}

	/**
	 * 
	 * @return
	 */
	public List<String> getErrors() {
		return errors;
	}

	/**
	 * 
	 * @param errors
	 */
	public void setErrors(final List<String> errors) {
		this.errors = errors;
	}

	/**
	 * 
	 * @param error
	 */
	public void setError(final String error) {
		errors = Arrays.asList(error);
	}

}
