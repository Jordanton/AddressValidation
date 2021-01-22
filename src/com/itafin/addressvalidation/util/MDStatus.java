package com.itafin.addressvalidation.util;

import java.util.ArrayList;
import java.util.Arrays;

public class MDStatus {

	/*
	 * PROPERTIES
	
	private final ArrayList<String> pSuccessCodesList = new ArrayList<String>(
			Arrays.asList(PropsUtil.LoadProp("MelissaData.Personator.Status.Success").split("\\s*,\\s*")));
	
	private final ArrayList<String> eSuccessCodesList = new ArrayList<String>(
			Arrays.asList(PropsUtil.LoadProp("MelissaData.ExpressEntry.Status.Success").split("\\s*,\\s*")));

	private final ArrayList<String> pErrorCodesList = new ArrayList<String>(
			Arrays.asList(PropsUtil.LoadProp("MelissaData.Personator.Status.Error").split("\\s*,\\s*")));

	private final ArrayList<String> pMultiMatchCodeList = new ArrayList<String>(
			Arrays.asList(PropsUtil.LoadProp("MelissaData.Personator.Status.MultiMatch").split("\\s*,\\s*")));
	
	*/
	
	private final ArrayList<String> pSuccessCodesList = new ArrayList<String>(
			Arrays.asList("AS01,AS02,AS03".split("\\s*,\\s*")));
	
	private final ArrayList<String> eSuccessCodesList = new ArrayList<String>(
			Arrays.asList("XS01,XS02".split("\\s*,\\s*")));

	private final ArrayList<String> pErrorCodesList = new ArrayList<String>(
			Arrays.asList("AE01,AE02,AE03,AE04".split("\\s*,\\s*")));

	private final ArrayList<String> pMultiMatchCodeList = new ArrayList<String>(
			Arrays.asList("AE05".split("\\s*,\\s*")));

	private ArrayList<String> responseStatusList;

	public void setResultCode(String resultCode) {
		responseStatusList = new ArrayList<String>(Arrays.asList(resultCode.split("\\s*,\\s*")));
	}

	public boolean isAddressSuccess() {

		for (int i = 0; i < pSuccessCodesList.size(); i++) {
			if (responseStatusList.contains(pSuccessCodesList.get(i)))
				return true;
		}

		return false;
	}
	
	  public boolean isAddressSearchSuccess() {

		    for (int i = 0; i < eSuccessCodesList.size(); i++) {
		      if (responseStatusList.contains(eSuccessCodesList.get(i)))
		        return true;
		    }

		    return false;
		  }

	public boolean isAddressError() {
		for (int i = 0; i < pErrorCodesList.size(); i++) {
			if (responseStatusList.contains(pErrorCodesList.get(i)))
				return true;
		}

		return false;
	}

	public boolean isMultiMatch() {
		for (int i = 0; i < pMultiMatchCodeList.size(); i++) {
			if (responseStatusList.contains(pMultiMatchCodeList.get(i)))
				return true;
		}
		return false;
	}

}
