package com.itafin.addressvalidation.controller;

import java.util.List;

import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.itafin.addressvalidation.exception.MDResultErrorException;
import com.itafin.addressvalidation.model.ITSAddrData;
import com.itafin.addressvalidation.model.ITSFullAddrData;
import com.itafin.addressvalidation.model.ITSLatLongData;
import com.itafin.addressvalidation.service.AddressValidationService;
import com.itafin.common.controller.BaseController;

@RestController
public class AddressValidationController extends BaseController {
	
	private static final Logger logger = Logger.getLogger(AddressValidationController.class);
	
	@Autowired
	public AddressValidationService addressValidationService;
	
	
	private static final String LATLONG_DEFAULT_XML = "<ITSLatLongData><latitude>0.0</latitude><longitude>0.0</longitude></ITSLatLongData>";

	private static final String EXACT_DEFAULT_XML = "<ITSFullAddrData><stdAddress></stdAddress><streetNumber></streetNumber><streetName></streetName><streetNameSuffix><POBoxNumber>400764</POBoxNumber></streetNameSuffix><city>Charlottesville</city><state>VA</state><zip>22907</zip><zip4></zip4></ITSFullAddrData>";
	
	private static final String FUZZY_DEFAULT_XML = "<ITSFullAddrData><stdAddress></stdAddress><streetNumber></streetNumber><streetName></streetName><streetNameSuffix></streetNameSuffix><unitType>PO BOX</unitType><unitNumber>400764</unitNumber><city>Charlottesville</city><state>VA</state><zip>22907</zip><zip4></zip4></ITSFullAddrData>";

	
//	@RequestMapping(value="/test")
//	public String test(@RequestParam(value = "fullAddrData", defaultValue = "<ITSFullAddrData><stdAddress></stdAddress><streetNumber>4315</streetNumber><streetName>Fandon</streetName><streetNameSuffix>Ave</streetNameSuffix><city></city><state>CA</state><zip>91732</zip><zip4></zip4></ITSFullAddrData>") String fullAddrData,
//			@RequestParam(value = "abStandardizedSource", defaultValue = "true") boolean abStandardizedSource) throws JAXBException {
//
//		//return fullAddrData;
//		return "<ITSFullAddrData><stdAddress></stdAddress><streetNumber>43188</streetNumber><streetName>12Fandon</streetName><streetNameSuffix>Ave</streetNameSuffix></ITSFullAddrData>";
//	}

	@RequestMapping(value="/getLatLong", produces=MediaType.APPLICATION_JSON_VALUE)
	public ITSLatLongData getLatLong(@RequestParam(value = "latLongData", defaultValue = LATLONG_DEFAULT_XML) String latLongData,
			@RequestParam(value = "fullAddrData", defaultValue = EXACT_DEFAULT_XML) String fullAddrData) throws JAXBException, MDResultErrorException {
		
		logger.info("GET LAT LONG REQUEST (latLongData): " + latLongData);
		logger.info("GET LAT LONG REQUEST (fullAddrData): " + fullAddrData);
		
		return addressValidationService.getLatLong(latLongData, fullAddrData);
	}

	@RequestMapping(value="/matchAddressExact", produces=MediaType.APPLICATION_JSON_VALUE)
	public ITSFullAddrData matchAddressExact(	@RequestParam(value = "fullAddrData", defaultValue = EXACT_DEFAULT_XML) String fullAddrData,
												@RequestParam(value = "includeZoneData", defaultValue = "false") boolean includeZoneData
			) throws JAXBException, MDResultErrorException {
		
		logger.info("MATCH ADDRESS EXACT REQUEST: " + fullAddrData);

		return addressValidationService.personatorAddr(fullAddrData, includeZoneData);
		
	}
	
	@RequestMapping(value="/matchAddressFuzzy", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<ITSAddrData> matchAddressFuzzy(@RequestParam(value = "fullAddrData", defaultValue = FUZZY_DEFAULT_XML) String fullAddrData
			) throws JAXBException, MDResultErrorException {
		
		logger.info("MATCH ADDRESS FUZZY REQUEST: " + fullAddrData);
		
		return addressValidationService.expressAddr(fullAddrData);
		
	}
    
    @RequestMapping(value="/parseAddressLine", produces=MediaType.APPLICATION_JSON_VALUE)
    public ITSFullAddrData parseAddressLine(@RequestParam(value = "addressLine1", defaultValue = "") String addressLine1,
                                            @RequestParam(value = "addressLine2", defaultValue = "") String addressLine2,
                                            @RequestParam(value = "city", defaultValue = "") String city,
                                            @RequestParam(value = "state", defaultValue = "") String state,
                                            @RequestParam(value = "postalCode", defaultValue = "") String postalCode
            ) throws JAXBException, MDResultErrorException {
        
        logger.info("PARSE ADDRESS LINE REQUEST: " + addressLine1 + ", " + addressLine2 + ", " + city + ", " + state + ", " + postalCode + ", " );
        
        return addressValidationService.parseAddressLine(addressLine1, addressLine2, city, state, postalCode);
        
    }
    
    @RequestMapping(value="/getZoneData", produces=MediaType.APPLICATION_JSON_VALUE)
    public ITSFullAddrData getZoneData(   @RequestParam(value = "fullAddrData", defaultValue = EXACT_DEFAULT_XML) String fullAddrData
            ) throws JAXBException, MDResultErrorException {
        
        logger.info("GET ZONE DATA REQUEST: " + fullAddrData);

        return addressValidationService.getZoneData(fullAddrData);
        
    }

}
