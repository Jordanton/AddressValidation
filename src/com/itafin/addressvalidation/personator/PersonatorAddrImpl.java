/**
 * 
 */
package com.itafin.addressvalidation.personator;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.itafin.addressvalidation.model.Address;
import com.itafin.addressvalidation.model.ITSFullAddrData;
import com.itafin.addressvalidation.service.AddressValidationServiceImpl;
import com.itafin.addressvalidation.util.LoggingRequestInterceptor;
import com.itafin.addressvalidation.util.PropsUtil;
import com.itafin.addressvalidation.util.StringUtils;


/**
 * @author 375149
 *
 */
@Component
public class PersonatorAddrImpl implements PersonatorAddr {

	private static final Logger logger = Logger.getLogger(PersonatorAddrImpl.class);
	
	/**
	 * @param args
	 */
	public PersonatorResponse personatorAddrService(Address reqAddress) {
		
		
		try {
			PersonatorAddrImpl per = new PersonatorAddrImpl();
			
			PersonatorRequest contactRequestObj = createDoContactVerify(reqAddress);
			
			PersonatorResponse response = per.fetchAddress(contactRequestObj);
			return response;
			
		} catch (XMLStreamException e) {
			logger.warn("An exception occured due to:" + e);
			e.printStackTrace();
			return null;
		} catch (JAXBException e) {
			logger.warn("An exception occured due to:" + e);
			e.printStackTrace();
			return null;
		}

	}
	

	
	/**
	 * Create Request
	 * 
	 * @return
	 */
	static public PersonatorRequest createDoContactVerify(Address reqAddress) {

		ObjectFactory objFactory = new ObjectFactory();

		PersonatorRequest request = new PersonatorRequest();
		
		
		request.setActions(PropsUtil.LoadProp("MelissaData.Personator.Actions"));
		
		request.setColumns(PropsUtil.LoadProp("MelissaData.Personator.Columns") + "," + PropsUtil.LoadProp("MelissaData.Geocode.Columns") + ",GrpAddressDetails,GrpNameDetails,GrpParsedPhone");
		
		request.setCustomerID(PropsUtil.LoadProp("MelissaData.Key"));
		
		request.setOptions(PropsUtil.LoadProp("MelissaData.Personator.Options"));
		request.setTransmissionReference("TEST");

		PersonatorRequest.Records arrayRecord = objFactory.createRequestRecords();

		PersonatorRequest.Records.RequestRecord record = objFactory.createRequestRecordsRequestRecord();

			record.setAddressLine1(reqAddress.getAddressLine1());
			record.setAddressLine2(reqAddress.getAddressLine2());
			record.setFreeform(reqAddress.getFreeForm());
			record.setCity(reqAddress.getCity());
			record.setState(reqAddress.getState());
			record.setPostalCode(reqAddress.getPostalCode());
			record.setCompanyName(reqAddress.getCompanyName());
		
		arrayRecord.getRequestRecord().add(record);

		request.setRecords(arrayRecord);

		return request;

	}
	
	/**
	 * 
	 * @param contactRequestObj1
	 * @return
	 * @throws XMLStreamException
	 * @throws JAXBException
	 */
	public PersonatorResponse fetchAddress(PersonatorRequest contactRequestObj) throws XMLStreamException, JAXBException{
		
		RestTemplate restTemplate = new RestTemplate();
		
		
		ClientHttpRequestInterceptor ri = new LoggingRequestInterceptor();
		List<ClientHttpRequestInterceptor> ris = new ArrayList<ClientHttpRequestInterceptor>();
		ris.add(ri);
		restTemplate.setInterceptors(ris);
		restTemplate.setRequestFactory(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
		
		//Create a list for the message converters
	    List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
	    //Add the String Message converter
	    messageConverters.add(new StringHttpMessageConverter());
	    //messageConverters.add(new MarshallingHttpMessageConverter());
	    messageConverters.add(new Jaxb2RootElementHttpMessageConverter());
	
	    //Add the message converters to the restTemplate
	   restTemplate.setMessageConverters(messageConverters);
		
		StringWriter xmlRequestStrWriter = new StringWriter();
		XMLStreamWriter xmlStrWriter = NamespaceStrippingXMLStreamWriter.filter(xmlRequestStrWriter);
		JAXBContext jaxbContext = JAXBContext.newInstance(PersonatorRequest.class.getPackage().getName());
		Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		logger.info("prt: " + contactRequestObj);
		logger.info("prt: " + xmlStrWriter);
		marshaller.marshal(contactRequestObj, xmlStrWriter);

		String xmlRequestStr = xmlRequestStrWriter.toString();
		logger.info("xmlRequestStr::\n" + xmlRequestStr);
		 
		// **1 Using HttpEntity
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_XML);
	
		HttpEntity<String> requestEntity = new HttpEntity<String>(xmlRequestStr, headers);

		HttpEntity<PersonatorRequest> requestEntity2 = new HttpEntity<PersonatorRequest>(contactRequestObj, headers);
		
		/*
		 * PROPERTIES
		  
		 ResponseEntity<PersonatorResponse> responseEntity  = restTemplate.postForEntity(
				PropsUtil.LoadProp("MelissaData.Personator.Address"),
				 requestEntity2, PersonatorResponse.class);
	
		 */
		
		ResponseEntity<PersonatorResponse> responseEntity  = restTemplate.postForEntity(
				"http://personator.melissadata.net/v3/WEB/ContactVerify/doContactVerify",
				 requestEntity2, PersonatorResponse.class);
		
	    logger.info("response.getHeaders()::"+responseEntity.getHeaders());
	    logger.info("response.getBody().getTotalRecords::"+responseEntity.getBody().getTotalRecords());
	    

	    PersonatorResponse contactResponseObj2 = responseEntity.getBody();
		StringWriter xmlResponseStrWriter2 = new StringWriter();
		XMLStreamWriter xmlStrWriter2 = NamespaceStrippingXMLStreamWriter.filter(xmlResponseStrWriter2);
		JAXBContext jaxbContext2 = JAXBContext.newInstance(PersonatorResponse.class.getPackage().getName());
		Marshaller marshaller2 = jaxbContext2.createMarshaller();
		marshaller2.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		
		//Class is missing the required @XMLRootElement annotation, so setup by doing this
		//Ref::https://www.codenotfound.com/jaxb-marshal-element-missing-xmlrootelement-annotation.html
		QName qName = new QName(PersonatorResponse.class.getPackage().getName(), PersonatorResponse.class.getSimpleName());
	    JAXBElement<PersonatorResponse> root = new JAXBElement<>(qName, PersonatorResponse.class, contactResponseObj2);
	    marshaller2.marshal(root,xmlStrWriter2);
		
		String xmlRequestStr2 = xmlResponseStrWriter2.toString();
		logger.info("xmlRequestStrRemovedNameSpace::\n" + xmlRequestStr2);
		
		
		return responseEntity.getBody();
		
	}
	

}
