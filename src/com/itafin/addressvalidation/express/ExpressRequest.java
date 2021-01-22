package com.itafin.addressvalidation.express;

/**
 * 
 * @author 375149
 *
 */
public class ExpressRequest {

	private String line1 = "";
	private String suite = "";;
	private String city = "";;
	private String state = "";;
	private String postalCode = "";
	private int maxRespRecords = 5;

	/**
	 * 
	 */
	public ExpressRequest() {
		super();
	}

	/**
	 * 
	 * @param line1
	 * @param maxRecords
	 */
	public ExpressRequest(String line1, int maxRecords) {
		super();
		this.line1 = line1;
		this.maxRespRecords = maxRecords;
	}

	/**
	 * 
	 * @param line1
	 * @param suite
	 * @param city
	 * @param state
	 * @param postalCode
	 * @param maxRecords
	 */
	public ExpressRequest(String suite, String city, String state, String postalCode, int maxRecords) {
		super();
		this.suite = suite;
		this.city = city;
		this.state = state;
		this.postalCode = postalCode;
		this.maxRespRecords = maxRecords;
	}

	/**
	 * @return the line1
	 */
	public String getLine1() {
		return line1;
	}

	/**
	 * @param line1
	 *            the line1 to set
	 */
	public void setLine1(String line1) {
		this.line1 = line1;
	}

	/**
	 * @return the suite
	 */
	public String getSuite() {
		return suite;
	}

	/**
	 * @param suite
	 *            the suite to set
	 */
	public void setSuite(String suite) {
		this.suite = suite;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city
	 *            the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the postalCode
	 */
	public String getPostalCode() {
		return postalCode;
	}

	/**
	 * @param postalCode
	 *            the postalCode to set
	 */
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	/**
	 * @return the maxRecords
	 */
	public int getMaxRespRecords() {
		return maxRespRecords;
	}

	/**
	 * @param maxRecords
	 *            the maxRecords to set
	 */
	public void setMaxRespRecords(int maxRecords) {
		this.maxRespRecords = maxRecords;
	}

}
