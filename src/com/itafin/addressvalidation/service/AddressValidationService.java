package com.itafin.addressvalidation.service;

import java.util.List;

import com.itafin.addressvalidation.exception.MDResultErrorException;
import com.itafin.addressvalidation.model.ITSAddrData;
import com.itafin.addressvalidation.model.ITSFullAddrData;
import com.itafin.addressvalidation.model.ITSLatLongData;

public interface AddressValidationService {

	public ITSLatLongData getLatLong(String latLongData, String fullAddrData) throws MDResultErrorException;
	
	public ITSFullAddrData personatorAddr(String fullAddrData, boolean includeZoneData) throws MDResultErrorException;
	
	public List<ITSAddrData> expressAddr(String fullAddrData) throws MDResultErrorException;
	
	public ITSFullAddrData parseAddressLine(String addressLine1, String addressLine2, String city, String state, String postalCode) throws MDResultErrorException;
	
	   public ITSFullAddrData getZoneData(String fullAddrData) throws MDResultErrorException;
		
}
