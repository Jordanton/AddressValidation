package com.itafin.addressvalidation.personator;

import java.io.Writer;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;


public class NamespaceStrippingXMLStreamWriter extends DelegatingXMLStreamWriter {

  public NamespaceStrippingXMLStreamWriter(XMLStreamWriter xmlWriter) throws XMLStreamException {
    super(xmlWriter);
  }

  @Override
  public void writeNamespace(String prefix, String uri) throws XMLStreamException {
    // intentionally doing nothing
  }

  @Override
  public void writeDefaultNamespace(String uri) throws XMLStreamException {
    // intentionally doing nothing
  }

  @Override
  public void writeStartElement(String prefix, String local, String uri) throws XMLStreamException {
    super.writeStartElement(null, local, null);
  }

  @Override
  public void writeStartElement(String uri, String local) throws XMLStreamException {
    super.writeStartElement(null, local);
  }

  @Override
  public void writeEmptyElement(String uri, String local) throws XMLStreamException {
    super.writeEmptyElement(null, local);
  }

  @Override
  public void writeEmptyElement(String prefix, String local, String uri) throws XMLStreamException {
    super.writeEmptyElement(null, local, null);
  }

  @Override
  public void writeAttribute(String prefix, String uri, String local, String value) throws XMLStreamException {
    super.writeAttribute(null, null, local, value);
  }

  @Override
  public void writeAttribute(String uri, String local, String value) throws XMLStreamException {
    super.writeAttribute(null, local, value);
  }
  
  public static XMLStreamWriter filter(Writer writer) throws XMLStreamException {
	    return new NamespaceStrippingXMLStreamWriter(XMLOutputFactory.newInstance().createXMLStreamWriter(writer));
	  }
}