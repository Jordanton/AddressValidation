//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.03.16 at 01:58:23 PM PDT 
//


package com.itafin.addressvalidation.personator;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.latax.personator.msg package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Response_QNAME = new QName("http://schemas.datacontract.org/2004/07/WcfServiceMD.mdContactVerify", "Response");
    private final static QName _ArrayOfResponseRecord_QNAME = new QName("http://schemas.datacontract.org/2004/07/WcfServiceMD.mdContactVerify", "ArrayOfResponseRecord");
    private final static QName _ResponseRecord_QNAME = new QName("http://schemas.datacontract.org/2004/07/WcfServiceMD.mdContactVerify", "ResponseRecord");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.latax.personator.msg
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link PersonatorRequest }
     * 
     */
    public PersonatorRequest createRequest() {
        return new PersonatorRequest();
    }

    /**
     * Create an instance of {@link PersonatorRequest.Records }
     * 
     */
    public PersonatorRequest.Records createRequestRecords() {
        return new PersonatorRequest.Records();
    }

    /**
     * Create an instance of {@link PersonatorResponse }
     * 
     */
    public PersonatorResponse createResponse() {
        return new PersonatorResponse();
    }

    /**
     * Create an instance of {@link ArrayOfResponseRecord }
     * 
     */
    public ArrayOfResponseRecord createArrayOfResponseRecord() {
        return new ArrayOfResponseRecord();
    }

    /**
     * Create an instance of {@link ResponseRecord }
     * 
     */
    public ResponseRecord createResponseRecord() {
        return new ResponseRecord();
    }

    /**
     * Create an instance of {@link PersonatorRequest.Records.RequestRecord }
     * 
     */
    public PersonatorRequest.Records.RequestRecord createRequestRecordsRequestRecord() {
        return new PersonatorRequest.Records.RequestRecord();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PersonatorResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/WcfServiceMD.mdContactVerify", name = "Response")
    public JAXBElement<PersonatorResponse> createResponse(PersonatorResponse value) {
        return new JAXBElement<PersonatorResponse>(_Response_QNAME, PersonatorResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfResponseRecord }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/WcfServiceMD.mdContactVerify", name = "ArrayOfResponseRecord")
    public JAXBElement<ArrayOfResponseRecord> createArrayOfResponseRecord(ArrayOfResponseRecord value) {
        return new JAXBElement<ArrayOfResponseRecord>(_ArrayOfResponseRecord_QNAME, ArrayOfResponseRecord.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResponseRecord }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/WcfServiceMD.mdContactVerify", name = "ResponseRecord")
    public JAXBElement<ResponseRecord> createResponseRecord(ResponseRecord value) {
        return new JAXBElement<ResponseRecord>(_ResponseRecord_QNAME, ResponseRecord.class, null, value);
    }
    
}
