package com.itafin.addressvalidation.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

public class LoggingRequestInterceptor implements ClientHttpRequestInterceptor {

	private static final Logger logger = Logger.getLogger(LoggingRequestInterceptor.class);

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {

        ClientHttpResponse response = execution.execute(request, body);

        log(request,body,response);

        return response;
    }

    private void log(HttpRequest request, byte[] body, ClientHttpResponse response) throws IOException {
        
    	
    	 StringBuilder inputStringBuilder = new StringBuilder();
         BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getBody(), "UTF-8"));
         String line = bufferedReader.readLine();
         while (line != null) {
             inputStringBuilder.append(line);
             inputStringBuilder.append('\n');
             line = bufferedReader.readLine();
         }
         logger.info("============================request begin==========================================");
         logger.info("URI          : {}"+request.getURI());
         logger.info("Method       : {}"+request.getMethod());
         logger.info("Headers      : {}"+request.getHeaders());
         logger.info("Request body: {}"+new String(body));
         logger.info("============================response begin==========================================");
         logger.info("Status code  : {}"+response.getStatusCode());
         logger.info("Status text  : {}"+response.getStatusText());
         logger.info("Headers      : {}"+response.getHeaders());
         logger.info("Response body: {}"+inputStringBuilder.toString());
         logger.info("=======================response end=================================================");
    	
    	
    }
    
    private void traceResponse(ClientHttpResponse response) throws IOException {
        StringBuilder inputStringBuilder = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getBody(), "UTF-8"));
        String line = bufferedReader.readLine();
        while (line != null) {
            inputStringBuilder.append(line);
            inputStringBuilder.append('\n');
            line = bufferedReader.readLine();
        }
//        log.debug("============================response begin==========================================");
//        log.debug("Status code  : {}", response.getStatusCode());
//        log.debug("Status text  : {}", response.getStatusText());
//        log.debug("Headers      : {}", response.getHeaders());
//        log.debug("Response body: {}", inputStringBuilder.toString());
//        log.debug("=======================response end=================================================");
    }
}