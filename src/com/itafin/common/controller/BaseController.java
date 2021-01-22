/*
 * Copyright 2018 LATAX, Inc. All rights reserved.
 *
 */
package com.itafin.common.controller;

import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.itafin.addressvalidation.util.RestWebServiceError;




/**
 * Simple Base Class added in case if anything common needs to be provided to
 * all controller classes
 *
 */
public class BaseController {
	private static final Logger logger = Logger.getLogger(BaseController.class);
	/**
	 * To be deleted or leave it for debug printing of Model, request, and
	 * session variable
	 * 
	 * @param modelMap
	 * @param request
	 * @param session
	 * @return
	 */
	public String displayAttribuesInfo(Map modelMap, HttpServletRequest request, HttpSession session) {
		System.out.println(
				"BaseController.displayAttribuesInfo() to print out model, request attribute, and session attribute:");

		System.out.println();
		System.out.println("=== Request Param data ===");
		java.util.Enumeration<String> reqParaEnum = request.getParameterNames();
		while (reqParaEnum.hasMoreElements()) {
			String s = reqParaEnum.nextElement();
			System.out.println("Name:" + s + "=\n" + request.getParameter(s));
			System.out.println();
		}

		System.out.println("--- Model data ---");
		// Map modelMap = model.asMap();
		for (Object modelKey : modelMap.keySet()) {
			Object modelValue = modelMap.get(modelKey);
			System.out.println("ModelName" + modelKey + "=\n" + modelValue);
		}

		System.out.println();
		System.out.println("=== Request data ===");
		java.util.Enumeration<String> reqEnum = request.getAttributeNames();
		while (reqEnum.hasMoreElements()) {
			String s = reqEnum.nextElement();
			System.out.println("Name:" + s + "=\n" + request.getAttribute(s));
			System.out.println();
		}

		System.out.println();
		System.out.println("*** Session data ***");
		Enumeration<String> e = session.getAttributeNames();
		while (e.hasMoreElements()) {
			String s = e.nextElement();
			System.out.println("Name:" + s + "=\n" + session.getAttribute(s));
			System.out.println();
		}

		return "nextpage";
	}

	/**
	 * Handle Exception to display the defaultErrorPage
	 * 
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	public ModelAndView handleAllException(Exception ex) {

		ModelAndView model = new ModelAndView("defaultErrorPage");
		
		model.addObject("exception", ex);
		
		logger.warn(ex.toString());
		ex.printStackTrace();
		
		return model;

	}
	
//	@ExceptionHandler({Exception.class, RuntimeException.class})
//	protected ResponseEntity<Object> handleNoHandlerFoundException(final NoHandlerFoundException ex,
//			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
//		logger.warn("Exception:", ex);
//		//
//		final String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();
//
//		final com.itafin.addressvalidation.util.RestWebServiceError apiError = new RestWebServiceError(HttpStatus.NOT_FOUND, ex.getLocalizedMessage(),
//				error);
//		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
//	}

}
