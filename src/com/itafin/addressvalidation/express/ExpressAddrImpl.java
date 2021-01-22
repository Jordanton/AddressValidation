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
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.itafin.addressvalidation.model.Address;
import com.itafin.addressvalidation.model.ITSFullAddrData;
import com.itafin.addressvalidation.personator.PersonatorAddrImpl;
import com.itafin.addressvalidation.util.LoggingRequestInterceptor;
import com.itafin.addressvalidation.util.PropsUtil;


/**
 * @author 375149
 *
 */
@Component
public class ExpressAddrImpl implements ExpressAddr {
	
	private static final Logger logger = Logger.getLogger(ExpressAddrImpl.class);

	/**
	 * @param args
	 */
	public ExpressResponse expressAddrService(Address address) {

		ExpressAddrImpl expAddrSer = new ExpressAddrImpl();
		return expAddrSer.fetchAddress(address);
		
	}
	
	public ExpressResponse fetchAddress(Address address) {

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
		headers.set("Accept", MediaType.APPLICATION_XML_VALUE);// work APPLICATION_XML_VALUE

		
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://expressentry.melissadata.net/web/ExpressAddress")
				.queryParam("format", "XML") //XML currently
				.queryParam("id", "112986022")
				.queryParam("line1", address.getAddressLine1())
				.queryParam("suite", address.getAddressLine2())
				.queryParam("city", address.getCity())
				.queryParam("state", address.getState())
				.queryParam("postalcode", address.getPostalCode())
				.queryParam("maxrecords", 10);
		


		HttpEntity<?> entity = new HttpEntity<>(headers);

		HttpEntity<String> response = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, entity,
				String.class);
		logger.info("builder.build().encode().toUri()::" + builder.build().encode().toUri());
		logger.info("result::" + response.getBody());
		ExpressResponse addressResponse = JAXB.unmarshal(new StringReader(response.getBody()), ExpressResponse.class);

		return addressResponse;

	}

}
