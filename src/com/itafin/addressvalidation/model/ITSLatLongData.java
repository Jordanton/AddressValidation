package com.itafin.addressvalidation.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="ITSLatLongData")
@XmlType(name = "ITSLatLongData", propOrder = {
    "zipCode",
    "latDirection",
    "latitude",
    "longDirection",
    "longitude"
})
public final class ITSLatLongData {
    
	@XmlElement(name = "zipCode", nillable = true)
	public String zipCode;
	@XmlElement(name = "latDirection", nillable = true)
    public String latDirection;
	@XmlElement(name = "latitude", nillable = true)
    public String latitude;
	@XmlElement(name = "longDirection", nillable = true)
    public String longDirection;
	@XmlElement(name = "longitude", nillable = true)
    public String longitude;

	public ITSLatLongData() {
		
	}
	
    public ITSLatLongData
        (java.lang.String zipCode,
        String latDirection,
        java.lang.String latitude,
        String longDirection,
        java.lang.String longitude)
    {
        this.zipCode = zipCode;
        this.latDirection = latDirection;
        this.latitude = latitude;
        this.longDirection = longDirection;
        this.longitude = longitude;
    }
}
