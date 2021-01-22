package com.itafin.addressvalidation.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="ITSFullAddrData")
@XmlType(name = "ITSFullAddrData", propOrder = {
    "stdAddress",
    "firmName",
    "streetNumber",
    "streetPrefixDir",
    "streetName",
    "streetNameSuffix",
    "streetSuffixDir",
    "unitType",
    "unitNumber",
    "city",
    "state",
    "zip",
    "zip4",
    "POBoxNumber",
    "ruralRoute",
    "ruralBoxNumber",
    "latitude",
    "longitude",
    "cd_no",
    "taps_cd",
    "latax_dist",
    "in_city",
    "resultStatus",
    "resultCode"
})
public final class ITSFullAddrData {
    
	@XmlElement(name = "stdAddress", nillable = true)
	public String stdAddress;
	@XmlElement(name = "firmName", nillable = true)
    public String firmName;
	@XmlElement(name = "streetNumber", nillable = true)
    public String streetNumber;
	@XmlElement(name = "streetPrefixDir", nillable = true)
    public String streetPrefixDir;
	@XmlElement(name = "streetName", nillable = true)
    public String streetName;
	@XmlElement(name = "streetNameSuffix", nillable = true)
    public String streetNameSuffix;
	@XmlElement(name = "streetSuffixDir", nillable = true)
    public String streetSuffixDir;
	@XmlElement(name = "unitType", nillable = true)
    public String unitType;
	@XmlElement(name = "unitNumber", nillable = true)
    public String unitNumber;
	@XmlElement(name = "city", nillable = true)
    public String city;
	@XmlElement(name = "state", nillable = true)
    public String state;
	@XmlElement(name = "zip", nillable = true)
    public String zip;
	@XmlElement(name = "zip4", nillable = true)
    public String zip4;
	@XmlElement(name = "POBoxNumber", nillable = true)
    public String POBoxNumber;
	@XmlElement(name = "ruralRoute", nillable = true)
    public String ruralRoute;
	@XmlElement(name = "ruralBoxNumber", nillable = true)
    public String ruralBoxNumber;
	@XmlElement(name = "latitude", nillable = true)
    public String latitude;
	@XmlElement(name = "longitude", nillable = true)
    public String longitude;
	
	@XmlElement(name = "cd_no", nillable = true)
    public String cd_no;
	@XmlElement(name = "taps_cd", nillable = true)
    public String taps_cd;
	@XmlElement(name = "latax_dist", nillable = true)
    public String latax_dist;
	@XmlElement(name = "in_city", nillable = true)
    public String in_city;
	
	@XmlElement(name = "resultStatus", nillable = true)
    public String resultStatus;
	@XmlElement(name = "resultCode", nillable = true)
    public String resultCode;
	
    
    public ITSFullAddrData() {
    	
    }

    public ITSFullAddrData
        (String stdAddress,
        String firmName,
        String streetNumber,
        String streetPrefixDir,
        String streetName,
        String streetNameSuffix,
        String streetSuffixDir,
        String unitType,
        String unitNumber,
        String city,
        String state,
        String zip,
        String zip4,
        String POBoxNumber,
        String ruralRoute,
        String ruralBoxNumber,
        String latitude,
        String longitude,
        String cd_no,
        String taps_cd,
        String latax_dist,
        String in_city,
        String resultStatus,
        String resultCode)
    {
        this.stdAddress = stdAddress;
        this.firmName = firmName;
        this.streetNumber = streetNumber;
        this.streetPrefixDir = streetPrefixDir;
        this.streetName = streetName;
        this.streetNameSuffix = streetNameSuffix;
        this.streetSuffixDir = streetSuffixDir;
        this.unitType = unitType;
        this.unitNumber = unitNumber;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.zip4 = zip4;
        this.POBoxNumber = POBoxNumber;
        this.ruralRoute = ruralRoute;
        this.ruralBoxNumber = ruralBoxNumber;
        this.latitude = latitude;
        this.longitude = longitude;
        this.cd_no = cd_no;
        this.taps_cd = taps_cd;
        this.latax_dist = latax_dist;
        this.in_city = in_city;
        this.resultStatus = resultStatus;
        this.resultCode = resultCode;
        
    }
}
