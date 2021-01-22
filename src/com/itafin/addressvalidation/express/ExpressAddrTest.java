/**
 * 
 */
package com.itafin.addressvalidation.express;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.management.MXBean;
import javax.xml.bind.JAXB;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.itafin.addressvalidation.util.LoggingRequestInterceptor;


/**
 * @author 375149
 *
 */

public class ExpressAddrTest {

	//private static final Logger logger = Logger.getLogger(ExpressAddrTest.class);
	
	final static String EXPRESS_ADDR_URL = "http://expressentry.melissadata.net/web/ExpressAddress";

	final static int EXPRESS_ADDR_LICENSE_KEY = 112986022;

	final static String EXPRESS_ADDR_RET_FORMAT = "XML"; // OR "JSON"

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		//System.setProperty("http.proxyHost", "bcproxy.ci.la.ca.us");
		//System.setProperty("http.proxyPort", "8080");
		//System.setProperty("https.proxyHost", "bcproxy.ci.la.ca.us");
		//System.setProperty("https.proxyPort", "8080");
		// System.setProperty("http.nonProxyHosts","192.168.170.105|192.168.170.104");

		// System.setProperty("java.net.useSystemProxies", "true");
		

		
		ExpressAddrTest expAddrSer = new ExpressAddrTest();

		String line1 = "4315";
		String suite = "";
		String city = "El Monte";
		String state = "CA";
		String postal = "90703";
		int maxResponseRecords = 10;

		 ExpressResponse response = expAddrSer.fetchAddress(new ExpressRequest(
		 suite, city, state, postal, maxResponseRecords));
		//Response response = expAddrSer.fetchAddress(new ExpressRequest(line1, maxResponseRecords));

	}
	
	//@Autowired
	//private RestTemplate restTemplate;
	
	public ExpressResponse fetchAddress(ExpressRequest expressRequest) {

		RestTemplate restTemplate = new RestTemplate();

		ClientHttpRequestInterceptor ri = new LoggingRequestInterceptor();
		List<ClientHttpRequestInterceptor> ris = new ArrayList<ClientHttpRequestInterceptor>();
		ris.add(ri);
		restTemplate.setInterceptors(ris);
		restTemplate.setRequestFactory(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));

		// Create a list for the message converters
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
		// Add the String Message converter
		messageConverters.add(new StringHttpMessageConverter());
		// messageConverters.add(new MarshallingHttpMessageConverter());
		messageConverters.add(new Jaxb2RootElementHttpMessageConverter());
		// Add the message converters to the restTemplate
		restTemplate.setMessageConverters(messageConverters);



		HttpHeaders headers = new HttpHeaders();
		//headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);// work APPLICATION_JSON_VALUE
		headers.set("Accept", MediaType.APPLICATION_XML_VALUE);// work APPLICATION_XML_VALUE

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(EXPRESS_ADDR_URL)
				.queryParam("format", EXPRESS_ADDR_RET_FORMAT) // TODO JSON or
																// "XML")
				.queryParam("suite", expressRequest.getSuite()).queryParam("line1", expressRequest.getLine1())
				.queryParam("state", expressRequest.getState())
				.queryParam("maxrecords", expressRequest.getMaxRespRecords())
				.queryParam("city", expressRequest.getCity()).queryParam("id", EXPRESS_ADDR_LICENSE_KEY)
				.queryParam("postal", expressRequest.getPostalCode());

		HttpEntity<?> entity = new HttpEntity<>(headers);

		HttpEntity<String> response = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, entity,
				String.class);
		System.out.println("builder.build().encode().toUri()::" + builder.build().encode().toUri());
		System.out.println("result::" + response.getBody());
		ExpressResponse addressResponse = JAXB.unmarshal(new StringReader(response.getBody()), ExpressResponse.class);

		return addressResponse;

	}

}
