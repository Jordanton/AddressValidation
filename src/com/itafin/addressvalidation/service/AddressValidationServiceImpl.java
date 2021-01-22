package com.itafin.addressvalidation.service;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import com.itafin.addressvalidation.exception.MDResultErrorException;
import com.itafin.addressvalidation.express.ExpressAddrImpl;
import com.itafin.addressvalidation.express.ExpressResponse;
import com.itafin.addressvalidation.express.ExpressResponse.Results.Result;
import com.itafin.addressvalidation.model.Address;
import com.itafin.addressvalidation.model.ITSAddrData;
import com.itafin.addressvalidation.model.ITSFullAddrData;
import com.itafin.addressvalidation.model.ITSLatLongData;
import com.itafin.addressvalidation.personator.PersonatorAddrImpl;
import com.itafin.addressvalidation.personator.PersonatorResponse;
import com.itafin.addressvalidation.personator.ResponseRecord;
import com.itafin.addressvalidation.util.StringUtils;
import com.itafin.boe.geo.BoeWebServiceImpl;
import com.itafin.boe.geo.GISData;

@Service
public class AddressValidationServiceImpl implements AddressValidationService {
	
	private static final Logger logger = Logger.getLogger(AddressValidationServiceImpl.class);
	
	@Autowired
	public ExpressAddrImpl expressAddr;
	
	@Autowired
	public PersonatorAddrImpl personatorAddr;
	
	@Autowired
    public BoeWebServiceImpl boeService;
	
	private static final String LATAX_POBOX_CODE = "POBX";
	
	
	
	public ITSLatLongData getLatLong(String latLongData, String fullAddrData) throws MDResultErrorException {
		
		ITSFullAddrData a_ITSFullAddrData = null;
		ITSLatLongData a_ITSLatLongData = null;

		try {

			// create unmarshaller for ITSFullAddrData
			JAXBContext jaxbContextITSFullAddrData = JAXBContext.newInstance(ITSFullAddrData.class);
			Unmarshaller unmarshallerITSFullAddrData = jaxbContextITSFullAddrData.createUnmarshaller();

			// unmarshall ITSFullAddrData
			StringReader reader = new StringReader(fullAddrData);
			a_ITSFullAddrData = (ITSFullAddrData) unmarshallerITSFullAddrData.unmarshal(reader);

			// create unmarshaller for ITSLatLongData
			JAXBContext jaxbContextITSLatLongData = JAXBContext.newInstance(ITSLatLongData.class);
			Unmarshaller unmarshallerITSLatLongData = jaxbContextITSLatLongData.createUnmarshaller();

			// unmarshall ITSFullAddrData
			StringReader reader2 = new StringReader(latLongData);
			a_ITSLatLongData = (ITSLatLongData) unmarshallerITSLatLongData.unmarshal(reader2);

		} catch (JAXBException e) {
			logger.warn("An exception occured due to:" + e);
			e.printStackTrace();
		}

		double latitude = 0.0;
		double longitude = 0.0;
		try {

			latitude = Double.parseDouble(a_ITSFullAddrData.latitude);
		} catch (Exception e) {
			latitude = 0.0;
		}

		try {

			longitude = Double.parseDouble(a_ITSFullAddrData.longitude);
		} catch (Exception e) {
			longitude = 0.0;
		}

		ITSLatLongData latLong = a_ITSLatLongData;
		if ((latitude == 0.0) | (longitude == 0.0)) {

			ITSFullAddrData address = personatorAddr(fullAddrData, true);
			latLong.latitude = address.latitude;
			latLong.longitude = address.longitude;

		} else {

			latLong.latitude = a_ITSFullAddrData.latitude;
			latLong.longitude = a_ITSFullAddrData.longitude;
		}
		
		return latLong;
		
	}
	

	
	public ITSFullAddrData personatorAddr(String fullAddrData, boolean includeZoneData) throws MDResultErrorException {
				
		ITSFullAddrData a_ITSFullAddrData = null;
		
		// create unmarshaller for ITSFullAddrData
		JAXBContext jaxbContextITSFullAddrData;
		try {
			jaxbContextITSFullAddrData = JAXBContext.newInstance(ITSFullAddrData.class);
		
			Unmarshaller unmarshallerITSFullAddrData = jaxbContextITSFullAddrData.createUnmarshaller();
	
			// unmarshall ITSFullAddrData
			StringReader reader = new StringReader(fullAddrData);
			a_ITSFullAddrData = (ITSFullAddrData) unmarshallerITSFullAddrData.unmarshal(reader);
		} catch (JAXBException e) {
			logger.warn("An exception occured due to:" + e);
			e.printStackTrace();
		}
		
		ITSFullAddrData addressIn = a_ITSFullAddrData;


	    String addressLine1 = StringUtils.fixNull(addressIn.streetNumber) + " "
	              + StringUtils.fixNull(addressIn.streetPrefixDir) + " "
	              + StringUtils.fixNull(addressIn.streetName) + " "
	              + StringUtils.fixNull(addressIn.streetNameSuffix) + " "
	              + StringUtils.fixNull(addressIn.streetSuffixDir);
	    
	    if(addressLine1.replaceAll("\\s+","").length() == 0) {
	      addressLine1 = StringUtils.fixNull(addressIn.stdAddress);
        }
	    

	    String addressLine2 = "";
	    if (!StringUtils.isBlank(addressIn.unitNumber)) {
	      String unit =
	          StringUtils.fixNull(addressIn.unitType) + " " + StringUtils.fixNull(addressIn.unitNumber);
	      addressLine2 = unit;
	    }

	    if (!StringUtils.isBlank(addressIn.POBoxNumber)) {
	      String poBox = "PO BOX " + StringUtils.fixNull(addressIn.POBoxNumber);
	      addressLine2 = poBox;
	    }
	    String zipPlus4 =
	        StringUtils.fixNull(addressIn.zip) + "-" + StringUtils.fixNull(addressIn.zip4);
		
	    
	    Address reqAddress = new Address();
	    reqAddress.setAddressLine1(addressLine1);
	    reqAddress.setAddressLine2(addressLine2);
	    reqAddress.setFreeForm(addressIn.stdAddress);
	    reqAddress.setCity(addressIn.city);
	    reqAddress.setState(addressIn.state);
	    reqAddress.setPostalCode(zipPlus4);
	    reqAddress.setCompanyName(addressIn.firmName);
	    
		PersonatorResponse resp = personatorAddr.personatorAddrService(reqAddress);
		
		ResponseRecord recordOut = resp.getRecords().getResponseRecord().get(0);
		recordOut.setResultCode(recordOut.getResults());
		
		if(recordOut.getAddressLine1().replaceAll("\\s+","").length() == 0) {
		  recordOut.setAddressLine1(null);
		}
		if(recordOut.getAddressStreetSuffix().replaceAll("\\s+","").length() == 0) {
		  recordOut.setAddressStreetSuffix(null);
		}
		if(recordOut.getAddressPreDirection().replaceAll("\\s+","").length() == 0) {
		  recordOut.setAddressPreDirection(null);
		}
		if(recordOut.getAddressPostDirection().replaceAll("\\s+","").length() == 0) {
		  recordOut.setAddressPostDirection(null);
		}
		if(recordOut.getLatitude().replaceAll("\\s+","").length() == 0) {
		  recordOut.setLatitude(null);
		}
		if(recordOut.getLongitude().replaceAll("\\s+","").length() == 0) {
		  recordOut.setLongitude(null);
		}
		if(recordOut.getAddressStreetName().replaceAll("\\s+","").length() == 0) {
		  recordOut.setAddressStreetName(null);
		}
		if(recordOut.getAddressSuiteName().replaceAll("\\s+","").length() == 0) {
		  recordOut.setAddressSuiteName(null);
		}
		if(recordOut.getAddressSuiteNumber().replaceAll("\\s+","").length() == 0) {
		  recordOut.setAddressSuiteNumber(null);
		}
		if(recordOut.getAddressHouseNumber().replaceAll("\\s+","").length() == 0) {
		  recordOut.setAddressHouseNumber(null);
		}
		if(recordOut.getAddressStreetName().replaceAll("\\s+","").length() == 0) {
		  recordOut.setAddressStreetName(null);
		}
		if(recordOut.getCity().replaceAll("\\s+","").length() == 0) {
		  recordOut.setCity(null);
		}
		if(recordOut.getState().replaceAll("\\s+","").length() == 0) {
		  recordOut.setState(null);
		}
		if(recordOut.getPostalCode().replaceAll("\\s+","").length() == 0) {
		  recordOut.setPostalCode(null);
		}
        
		
		if(recordOut.isSuccess()) {
			a_ITSFullAddrData.stdAddress = recordOut.getAddressLine1();
			a_ITSFullAddrData.streetNameSuffix = recordOut.getAddressStreetSuffix();
			a_ITSFullAddrData.streetPrefixDir = recordOut.getAddressPreDirection();
			a_ITSFullAddrData.streetSuffixDir = recordOut.getAddressPostDirection();
			a_ITSFullAddrData.latitude = recordOut.getLatitude();
			a_ITSFullAddrData.longitude = recordOut.getLongitude();
			
			boolean isPobx = false;
			if(recordOut.getAddressStreetName() != null) {
				isPobx = recordOut.getAddressStreetName().toUpperCase().matches(".*P+.*O+.*B+.*X+.*");
			}
			
			if(isPobx) {
				a_ITSFullAddrData.unitType = LATAX_POBOX_CODE;
				a_ITSFullAddrData.POBoxNumber = recordOut.getAddressHouseNumber();
				a_ITSFullAddrData.streetNumber = "";
				a_ITSFullAddrData.streetName = "";
			} else {
				a_ITSFullAddrData.unitType = recordOut.getAddressSuiteName();
				a_ITSFullAddrData.unitNumber = recordOut.getAddressSuiteNumber();
				a_ITSFullAddrData.streetNumber = recordOut.getAddressHouseNumber();
				a_ITSFullAddrData.streetName = recordOut.getAddressStreetName();
			}
			
			a_ITSFullAddrData.city = recordOut.getCity();
			a_ITSFullAddrData.state = recordOut.getState();
			a_ITSFullAddrData.zip = recordOut.getPostalCode().substring(0,5);
			a_ITSFullAddrData.zip4 = recordOut.getPostalCode().substring(recordOut.getPostalCode().length()-4);

			a_ITSFullAddrData.latitude = recordOut.getLatitude();
			a_ITSFullAddrData.longitude = recordOut.getLongitude();
			
			a_ITSFullAddrData.resultCode = recordOut.getResults();
			a_ITSFullAddrData.resultStatus = "Success";
			
		} else if (recordOut.isMultiMatch()) {
			a_ITSFullAddrData.resultCode = recordOut.getResults();
			a_ITSFullAddrData.resultStatus = "MultiMatch";
		} else if (recordOut.isError()) {
			a_ITSFullAddrData.resultCode = recordOut.getResults();
			a_ITSFullAddrData.resultStatus = "MultiMatch";
		} else {
			a_ITSFullAddrData.resultCode = recordOut.getResults();
			a_ITSFullAddrData.resultStatus = "Error";
		}
		
		if(includeZoneData) {
		  
		  GISData gisData = boeService.requestByCoords(a_ITSFullAddrData.latitude, a_ITSFullAddrData.longitude);
		  
		  a_ITSFullAddrData.latax_dist = gisData.latax_dist;
		  a_ITSFullAddrData.cd_no = gisData.cd_no;
		  a_ITSFullAddrData.taps_cd = gisData.taps_cd;
		  
		  a_ITSFullAddrData.latax_dist = StringUtils.nullToZero(a_ITSFullAddrData.latax_dist);
		  a_ITSFullAddrData.cd_no = StringUtils.nullToZero(a_ITSFullAddrData.cd_no);
		  a_ITSFullAddrData.taps_cd = StringUtils.nullToZero(a_ITSFullAddrData.taps_cd);
		  
		  if(gisData.latitude != null) {
		    a_ITSFullAddrData.latitude = gisData.latitude.toString();
		  }
		  
		  if(gisData.longitude != null) {
            a_ITSFullAddrData.longitude = gisData.longitude.toString();
          }
		  
		  if(gisData.in_city) {
		    a_ITSFullAddrData.in_city = "TRUE";
		  } else {
		    a_ITSFullAddrData.in_city = "FALSE";
		  }
		  
		}
		
		return a_ITSFullAddrData;
		
	}
	
	public List<ITSAddrData> expressAddr(String fullAddrData) {
		
		
		ITSFullAddrData a_ITSFullAddrData = null;
		
		// create unmarshaller for ITSFullAddrData
		JAXBContext jaxbContextITSFullAddrData;
		
		try {
			jaxbContextITSFullAddrData = JAXBContext.newInstance(ITSFullAddrData.class);
		
			Unmarshaller unmarshallerITSFullAddrData = jaxbContextITSFullAddrData.createUnmarshaller();
	
			// unmarshall ITSFullAddrData
			StringReader reader = new StringReader(fullAddrData);
			a_ITSFullAddrData = (ITSFullAddrData) unmarshallerITSFullAddrData.unmarshal(reader);
		} catch (JAXBException e) {
			logger.warn("An exception occured due to:" + e);
			e.printStackTrace();
		}
		
		
		String addressLine1 =
		        StringUtils.fixNull(a_ITSFullAddrData.streetNumber) + " "
		            + StringUtils.fixNull(a_ITSFullAddrData.streetPrefixDir) + " "
		            + StringUtils.fixNull(a_ITSFullAddrData.streetName) + " "
		            + StringUtils.fixNull(a_ITSFullAddrData.streetNameSuffix) + " "
		            + StringUtils.fixNull(a_ITSFullAddrData.streetSuffixDir);

		    String addressLine2 = "";
		    //if (StringUtils.fixNull(a_ITSFullAddrData.unitType).equals("STE")) {
		    if (!StringUtils.isEmpty(a_ITSFullAddrData.unitNumber, true)) {
		      // addressLine2 = StringUtils.fixNull(a_ITSFullAddrData.unitNumber);
		      String unit =
		          StringUtils.fixNull(a_ITSFullAddrData.unitType) + " " + StringUtils.fixNull(a_ITSFullAddrData.unitNumber);
		      addressLine2 = unit;

		    }

		    String zipPlus4 =
		        StringUtils.fixNull(a_ITSFullAddrData.zip) + "-"
		            + StringUtils.fixNull(a_ITSFullAddrData.zip4);


		    // Just pass in the address; rest is done by the service.
		    Address address = new Address();
		    /*
		     * These components are the primary address number, predirectional, street name, suffix,
		     * postdirectional, secondary address indentifier, and secondary address.
		     */

		    address = new Address();
		    address.setAddressLine1(addressLine1);
		    address.setAddressLine2(addressLine2);
		    address.setCity(a_ITSFullAddrData.city);
		    address.setState(a_ITSFullAddrData.state);
		    address.setPostalCode(zipPlus4);
		    address.setCompanyName(a_ITSFullAddrData.firmName);
		
		ExpressResponse resp = expressAddr.expressAddrService(address);
		
		resp.setResultStatus(resp.getResultCode());
		
		if(resp.isSuccess()) {
			List<Result> results = resp.getResults().getResult();
			List<ITSAddrData> addrDataList = new ArrayList<ITSAddrData>();
			
			for (int i = 0; i < results.size(); i++) {
				
				String zip = "";
				String zip4 = "";
				
				if(results.get(i).getAddress().getPostalCode() != null) {
					zip = results.get(i).getAddress().getPostalCode().substring(0,5);
					zip4 = results.get(i).getAddress().getPostalCode().substring(results.get(i).getAddress().getPostalCode().length()-4);
				}
				
				addrDataList.add(new ITSAddrData(results.get(i).getAddress().getAddressLine1(), 
						results.get(i).getAddress().getCity(), results.get(i).getAddress().getState(), zip, zip4));
			}
			
			// returns XML modeled as list of ITSAddrData objects

			return addrDataList;
		}
		return null;
		
		
	}
	
	public ITSFullAddrData parseAddressLine(String addressLine1, String addressLine2, String city, String state, String postalCode) throws MDResultErrorException {
	  
	  ITSFullAddrData a_ITSFullAddrData = new ITSFullAddrData();
      
      Address reqAddress = new Address();
      reqAddress.setAddressLine1(addressLine1);
      reqAddress.setAddressLine2(addressLine2);
      reqAddress.setCity(city);
      reqAddress.setState(state);
      reqAddress.setPostalCode(postalCode);
      
      PersonatorResponse resp = personatorAddr.personatorAddrService(reqAddress);
      
      ResponseRecord recordOut = resp.getRecords().getResponseRecord().get(0);
      recordOut.setResultCode(recordOut.getResults());
      
      if(recordOut.isSuccess()) {         

          a_ITSFullAddrData.streetPrefixDir = recordOut.getAddressPreDirection();
          a_ITSFullAddrData.streetSuffixDir = recordOut.getAddressPostDirection();
          a_ITSFullAddrData.streetNameSuffix = recordOut.getAddressStreetSuffix();
          a_ITSFullAddrData.city = recordOut.getCity();
          a_ITSFullAddrData.state = recordOut.getState();
          if(recordOut.getPostalCode().length() > 4) {
            a_ITSFullAddrData.zip = recordOut.getPostalCode().substring(0,5);
          } else {
            a_ITSFullAddrData.zip = "";
          }
          a_ITSFullAddrData.resultStatus = "Success";
          
          
          boolean isPobx = false;
          if(recordOut.getAddressStreetName() != null) {
              isPobx = recordOut.getAddressStreetName().toUpperCase().matches(".*P+.*O+.*B+.*X+.*");
          }
          
          if(isPobx) {
              a_ITSFullAddrData.unitType = LATAX_POBOX_CODE;
              a_ITSFullAddrData.POBoxNumber = recordOut.getAddressHouseNumber();
              a_ITSFullAddrData.streetNumber = "";
              a_ITSFullAddrData.streetName = "";
          } else {
              a_ITSFullAddrData.unitType = recordOut.getAddressSuiteName();
              a_ITSFullAddrData.unitNumber = recordOut.getAddressSuiteNumber();
              a_ITSFullAddrData.streetNumber = recordOut.getAddressHouseNumber();
              a_ITSFullAddrData.streetName = recordOut.getAddressStreetName();
          }    
          
          GISData gisData = boeService.requestByCoords(a_ITSFullAddrData.latitude, a_ITSFullAddrData.longitude);
          
          a_ITSFullAddrData.latax_dist = gisData.latax_dist;
          a_ITSFullAddrData.cd_no = gisData.cd_no;
          a_ITSFullAddrData.taps_cd = gisData.taps_cd;
          
          a_ITSFullAddrData.latax_dist = StringUtils.nullToZero(a_ITSFullAddrData.latax_dist);
          a_ITSFullAddrData.cd_no = StringUtils.nullToZero(a_ITSFullAddrData.cd_no);
          a_ITSFullAddrData.taps_cd = StringUtils.nullToZero(a_ITSFullAddrData.taps_cd);
          
          if(gisData.latitude != null) {
            a_ITSFullAddrData.latitude = gisData.latitude.toString();
          }
          
          if(gisData.longitude != null) {
            a_ITSFullAddrData.longitude = gisData.longitude.toString();
          }
          
          if(gisData.in_city) {
            a_ITSFullAddrData.in_city = "TRUE";
          } else {
            a_ITSFullAddrData.in_city = "FALSE";
          }
          
      } else {
        a_ITSFullAddrData.streetPrefixDir = recordOut.getAddressPreDirection();
        a_ITSFullAddrData.streetSuffixDir = recordOut.getAddressPostDirection();
        a_ITSFullAddrData.streetNameSuffix = recordOut.getAddressStreetSuffix();
        a_ITSFullAddrData.city = recordOut.getCity();
        a_ITSFullAddrData.state = recordOut.getState();
        if(recordOut.getPostalCode().length() > 4) {
          a_ITSFullAddrData.zip = recordOut.getPostalCode().substring(0,5);
        } else {
          a_ITSFullAddrData.zip = "";
        }
        a_ITSFullAddrData.resultStatus = "Error";
        
        
        boolean isPobx = false;
        if(recordOut.getAddressStreetName() != null) {
            isPobx = recordOut.getAddressStreetName().toUpperCase().matches(".*P+.*O+.*B+.*X+.*");
        }
        
        if(isPobx) {
            a_ITSFullAddrData.unitType = LATAX_POBOX_CODE;
            a_ITSFullAddrData.POBoxNumber = recordOut.getAddressHouseNumber();
            a_ITSFullAddrData.streetNumber = "";
            a_ITSFullAddrData.streetName = "";
        } else {
            a_ITSFullAddrData.unitType = recordOut.getAddressSuiteName();
            a_ITSFullAddrData.unitNumber = recordOut.getAddressSuiteNumber();
            a_ITSFullAddrData.streetNumber = recordOut.getAddressHouseNumber();
            a_ITSFullAddrData.streetName = recordOut.getAddressStreetName();
        }    
      }
      
      return a_ITSFullAddrData;
      
  }
	
	public ITSFullAddrData getZoneData(String fullAddrData) throws MDResultErrorException {
	  
	  ITSFullAddrData a_ITSFullAddrData = null;
      
      // create unmarshaller for ITSFullAddrData
      JAXBContext jaxbContextITSFullAddrData;
      try {
          jaxbContextITSFullAddrData = JAXBContext.newInstance(ITSFullAddrData.class);
      
          Unmarshaller unmarshallerITSFullAddrData = jaxbContextITSFullAddrData.createUnmarshaller();
  
          // unmarshall ITSFullAddrData
          StringReader reader = new StringReader(fullAddrData);
          a_ITSFullAddrData = (ITSFullAddrData) unmarshallerITSFullAddrData.unmarshal(reader);
      } catch (JAXBException e) {
          logger.warn("An exception occured due to:" + e);
          e.printStackTrace();
      }
	
  	  GISData gisData = boeService.requestByCoords(a_ITSFullAddrData.latitude, a_ITSFullAddrData.longitude);
      
      a_ITSFullAddrData.latax_dist = gisData.latax_dist;
      a_ITSFullAddrData.cd_no = gisData.cd_no;
      a_ITSFullAddrData.taps_cd = gisData.taps_cd;
      
      a_ITSFullAddrData.latax_dist = StringUtils.nullToZero(a_ITSFullAddrData.latax_dist);
      a_ITSFullAddrData.cd_no = StringUtils.nullToZero(a_ITSFullAddrData.cd_no);
      a_ITSFullAddrData.taps_cd = StringUtils.nullToZero(a_ITSFullAddrData.taps_cd);
      
      if(gisData.latitude != null) {
        a_ITSFullAddrData.latitude = gisData.latitude.toString();
      }
      
      if(gisData.longitude != null) {
        a_ITSFullAddrData.longitude = gisData.longitude.toString();
      }
      
      if(gisData.in_city) {
        a_ITSFullAddrData.in_city = "TRUE";
      } else {
        a_ITSFullAddrData.in_city = "FALSE";
      }
      return a_ITSFullAddrData;
	}
	
}
