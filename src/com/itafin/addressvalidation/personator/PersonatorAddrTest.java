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
import javax.xml.bind.Unmarshaller;
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
import org.springframework.web.client.RestTemplate;

import com.itafin.addressvalidation.model.Address;
import com.itafin.addressvalidation.model.ITSFullAddrData;
import com.itafin.addressvalidation.util.LoggingRequestInterceptor;




//import jaxb.generated.contactverify.ArrayOfRequestRecord;
//import jaxb.generated.contactverify.DoContactVerify;
//import jaxb.generated.contactverify.ObjectFactory;
//import jaxb.generated.contactverify.Request;
//import jaxb.generated.contactverify.RequestRecord;
//import jaxb.generated.contactverify.Response;

/**
 * @author 375149
 *
 */
public class PersonatorAddrTest {
	
	private static final Logger logger = Logger.getLogger(PersonatorAddrTest.class);

	/**
	 * 
	 */
	public PersonatorAddrTest() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
//		try {
			PersonatorAddrImpl per = new PersonatorAddrImpl();
			
			//PersonatorRequest contactRequestObj = createDoContactVerify(reqAddress);
			
			//PersonatorResponse response = per.fetchAddress(contactRequestObj);
			//return response;
			
//		} catch (XMLStreamException e) {
//			logger.warn("An exception occured due to:" + e);
//			e.printStackTrace();
//			//return null;
//		} catch (JAXBException e) {
//			logger.warn("An exception occured due to:" + e);
//			e.printStackTrace();
//			//return null;
//		}

	}
	

	
	/**
	 * Create Request
	 * 
	 * @return
	 */
	static public PersonatorRequest createDoContactVerify(Address reqAddress) {

		ObjectFactory objFactory = new ObjectFactory();

		PersonatorRequest request = new PersonatorRequest();
		request.setActions("Check,Verify");
		
		request.setColumns("GrpParsedAddress,GrpAddressDetails,GrpGeocode,GrpNameDetails,GrpParsedPhone");
		
		/*
		 * PROPERTIES
		 
		
		request.setCustomerID(PropsUtil.LoadProp("MelissaData.Key"));
		
		*/
		
		request.setCustomerID("112986022");
		
		request.setOptions("CentricHint:Auto;Append:Blank;AdvancedAddressCorrection:off;");
		request.setTransmissionReference("TEST");

		PersonatorRequest.Records arrayRecord = objFactory.createRequestRecords();

//		RequestRecord record = objFactory.createRequestRecord();
//		record.setAddressLine1(objFactory.createResponseRecordAddressLine1("200 N Main St"));
//		record.setState(objFactory.createRequestRecordState("CA"));
//		arrayRecord.getRequestRecord().add(record);
		
		PersonatorRequest.Records.RequestRecord record = objFactory.createRequestRecordsRequestRecord();

//			record.setAddressLine1(StringUtils.fixNull(stdAddress));
//		
//
//			record.setCity(StringUtils.fixNull(city));
//		
//
//			record.setState(StringUtils.fixNull(state));
//		
//
//			record.setPostalCode(StringUtils.fixNull(zip));
			
			record.setAddressLine1(reqAddress.getAddressLine1());
			record.setAddressLine2(reqAddress.getAddressLine2());
			record.setFreeform(reqAddress.getFreeForm());
			record.setCity(reqAddress.getCity());
			record.setState(reqAddress.getState());
			record.setPostalCode(reqAddress.getPostalCode());
			record.setCompanyName(reqAddress.getCompanyName());
		
		
		arrayRecord.getRequestRecord().add(record);

//		PersonatorRequest.Records.RequestRecord record2 = objFactory.createRequestRecordsRequestRecord();
//		//record2.set("Melissa Data");
//		record2.setAddressLine1("22382 Avenida Empresa");
//		record2.setState("CA");
//		record2.setPostalCode("92688");
//		arrayRecord.getRequestRecord().add(record2);

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

		//System.out.println("marshaled::\n" + xmlRequestStrWriter.toString());
		String xmlRequestStr = xmlRequestStrWriter.toString();
		//String xmlRequestStr = getXMLRequestString();
		logger.info("xmlRequestStr::\n" + xmlRequestStr);

		
		HttpHeaders headers = new HttpHeaders();
		//headers.add("header_name", "header_value");
		headers.setContentType(MediaType.APPLICATION_XML);
	
		HttpEntity<String> requestEntity = new HttpEntity<String>(xmlRequestStr, headers);


		HttpEntity<PersonatorRequest> requestEntity2 = new HttpEntity<PersonatorRequest>(contactRequestObj, headers);
		

		ResponseEntity<PersonatorResponse> responseEntity  = restTemplate.postForEntity(
				"http://personator.melissadata.net/v3/WEB/ContactVerify/doContactVerify",
				 requestEntity2, PersonatorResponse.class);
		
	    //JAXB.marshal(responseEntity.getBody(), System.out);
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
		
		//marshaller2.marshal(contactResponseObj2, xmlStrWriter2);
		String xmlRequestStr2 = xmlResponseStrWriter2.toString();
		logger.info("xmlRequestStrRemovedNameSpace::\n" + xmlRequestStr2);
		
		return responseEntity.getBody();
		
	}
	

}
