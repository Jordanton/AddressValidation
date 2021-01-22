package com.itafin.addressvalidation.test;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.itafin.addressvalidation.exception.MDResultErrorException;
import com.itafin.addressvalidation.model.ITSAddrData;
import com.itafin.addressvalidation.model.ITSFullAddrData;
import com.itafin.addressvalidation.model.ITSLatLongData;
import com.itafin.addressvalidation.service.AddressValidationService;

/*
 * Controller simulates LATAX Portal calls for Address Validation. 
 */


@Controller
public class TestController {
	
	@Autowired
	public AddressValidationService addressValidationService;

	@RequestMapping(value = {"/"}, method = { RequestMethod.GET, RequestMethod.POST })
	public String home(Model model) {

		String viewToDisplay = "index";

		return viewToDisplay;
	}
	
	
	/*
	 * Example of getLatLong service call
	 */
	@RequestMapping(value = {"/getLatLongT"}, method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView getLatLongT(@RequestParam("latLongData") String latLongData, @RequestParam("fullAddrData") String fullAddrData) throws MDResultErrorException {

		ModelAndView viewToDisplay = new ModelAndView("index");
		
		//getLatLong service receives an XML String for an ITSLatLongData object, an XML String for an ITSFullAddrData object, and returns an ITSFullAddrData object
		ITSLatLongData testLatLongData = addressValidationService.getLatLong(latLongData, fullAddrData);
		
		String msg = "{\"latitude\":\"" + testLatLongData.latitude + "\",\"longitude\":\"" + testLatLongData.longitude + "\"}";
		
		viewToDisplay.addObject("msg", msg);
		return viewToDisplay;
	}
	
	/*
	 * Example of matchAddressExact service call
	 */
	@RequestMapping(value = {"/matchAddressExactT"}, method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView matchAddressExactT(@RequestParam("fullAddrData") String fullAddrData) throws MDResultErrorException {

		ModelAndView viewToDisplay = new ModelAndView("index");
		
		//getLatLong service receives an XML String for an ITSFullAddrData object and returns an ITSFullAddrData object
		ITSFullAddrData testFullAddrData = addressValidationService.personatorAddr(fullAddrData, false);
		
		String msg = "{\"stdAddress\":\"" + testFullAddrData.stdAddress + "\",\"city\":\"" + testFullAddrData.city + "\",\"state\":\"" + testFullAddrData.state + "\",\"zip\":\"" + testFullAddrData.zip + "\"}";
		
		viewToDisplay.addObject("msg", msg);
		return viewToDisplay;
	}
	
	/*
	 * Example of matchAddressFuzzy service call
	 */
	@RequestMapping(value = {"/matchAddressFuzzyT"}, method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView matchAddressFuzzyT(@RequestParam("fullAddrData") String fullAddrData) throws MDResultErrorException {

		ModelAndView viewToDisplay = new ModelAndView("index");
		
		//getLatLong service receives an XML String for an ITSFullAddrData object and returns a list of ITSAddrData objects
		List<ITSAddrData> testAddrDataList = addressValidationService.expressAddr(fullAddrData);
		
		String msg = "";
		
		for(int i = 0; i < testAddrDataList.size(); i++) {
			msg += "{\"stdAddress\":\"" + testAddrDataList.get(i).stdAddress + "\",\"city\":\"" + testAddrDataList.get(i).city + "\",\"state\":\"" + testAddrDataList.get(i).state + "\",\"zip\":\"" + testAddrDataList.get(i).zip + "\"}";
		}
		
		viewToDisplay.addObject("msg", msg);
		return viewToDisplay;
	}
	
}