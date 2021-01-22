package com.itafin.addressvalidation.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ITSAddrData", propOrder = {
    "stdAddress",
    "firmName",
    "city",
    "state",
    "zip",
    "zip4",
    "matchScore",
})
public final class ITSAddrData {
	
	@XmlElement(name = "stdAddress", nillable = true)
    public java.lang.String stdAddress;
	@XmlElement(name = "firmName", nillable = true)
    public java.lang.String firmName;
	@XmlElement(name = "city", nillable = true)
    public java.lang.String city;
	@XmlElement(name = "state", nillable = true)
    public java.lang.String state;
	@XmlElement(name = "zip", nillable = true)
    public java.lang.String zip;
	@XmlElement(name = "zip4", nillable = true)
    public java.lang.String zip4;
	@XmlElement(name = "matchScore", nillable = true)
    public java.lang.String matchScore;

    public ITSAddrData
        (java.lang.String stdAddress,
        java.lang.String firmName,
        java.lang.String city,
        java.lang.String state,
        java.lang.String zip,
        java.lang.String zip4,
        java.lang.String matchScore)
    {
        this.stdAddress = stdAddress;
        this.firmName = firmName;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.zip4 = zip4;
        this.matchScore = matchScore;
    }
    
    public ITSAddrData
	    (java.lang.String stdAddress,
	    java.lang.String city,
	    java.lang.String state,
	    java.lang.String zip,
	    java.lang.String zip4)
    {
	    this.stdAddress = stdAddress;
	    this.city = city;
	    this.state = state;
	    this.zip = zip;
	    this.zip4 = zip4;
	}
}
