package com.itafin.addressvalidation.model;

public class Address {

	/* Address Record as in Request; almost matches ITSFullAddressData from LATAX */
	  /** The Record id. */
	  protected long   RecordID;
	  /* AddressLine1= Street address; Suite can be added to end or in AddressLine2. */
	  /** The Address line1. */
	  protected String AddressLine1;
	  /* Address Line2 = continuation of AddressLine1 (ex: suite) or another address. */
	  /** The Address line2. */
	  protected String AddressLine2;        // Used as suite in Express Entry.

	  /** The City. */
	  protected String City;

	  /** The State. */
	  protected String State;

	  /** The Postal code. */
	  protected String PostalCode;

	  /** The Country. */
	  protected String Country;
	  /* LastLine = City + State + ZIP. */
	  /** The Last line. */
	  protected String LastLine;
	  /* Freeform can contain Address, Name, Phone, Email, and Company. */
	  /** The Free form. */
	  protected String FreeForm;

	  /** The First name. */
	  protected String FirstName;

	  /** The Last name. */
	  protected String LastName;

	  /** The Full name. */
	  protected String FullName;

	  /** The Phone number. */
	  protected String PhoneNumber;

	  /** The Email address. */
	  protected String EmailAddress;

	  /** The Company name. */
	  protected String CompanyName;
	  /* Has all the fields in Request + those in Response */
	  /** The Address house number. */
	  private String   AddressHouseNumber;

	  /** The Address pre direction. */
	  private String   AddressPreDirection;

	  /** The Address street name. */
	  private String   AddressStreetName;

	  /** The Address street suffix. */
	  private String   AddressStreetSuffix;

	  /** The Address post direction. */
	  private String   AddressPostDirection;

	  /** The Address suite number. */
	  private String   AddressSuiteNumber;

	  /** The Address suite name. */
	  private String   AddressSuiteName;

	  /**
	   * Instantiates a new address.
	   */
	  public Address() {
	    RecordID = 0;
	    AddressLine1 = "";
	    AddressLine2 = "";
	    City = "";
	    State = "";
	    PostalCode = "";
	    Country = "";
	    LastLine = "";
	    FreeForm = "";
	    FirstName = "";
	    LastName = "";
	    FullName = "";
	    PhoneNumber = "";
	    EmailAddress = "";
	    CompanyName = "";
	  }

	  /**
	   * Gets the record id.
	   *
	   * @return the record id
	   */
	  public long getRecordID() {
	    return RecordID;
	  }

	  /**
	   * Sets the record id.
	   *
	   * @param RecordID the new record id
	   */
	  public void setRecordID(long RecordID) {
	    this.RecordID = RecordID;
	  }

	  /**
	   * Gets the phone number.
	   *
	   * @return the phone number
	   */
	  public String getPhoneNumber() {
	    return PhoneNumber;
	  }

	  /**
	   * Sets the phone number.
	   *
	   * @param PhoneNumber the new phone number
	   */
	  public void setPhoneNumber(String PhoneNumber) {
	    this.PhoneNumber = PhoneNumber;
	  }

	  /**
	   * Gets the company name.
	   *
	   * @return the company name
	   */
	  public String getCompanyName() {
	    return CompanyName;
	  }

	  /**
	   * Sets the company name.
	   *
	   * @param CompanyName the new company name
	   */
	  public void setCompanyName(String CompanyName) {
	    this.CompanyName = CompanyName;
	  }

	  /**
	   * Gets the country.
	   *
	   * @return the country
	   */
	  public String getCountry() {
	    return Country;
	  }

	  /**
	   * Sets the country.
	   *
	   * @param Country the new country
	   */
	  public void setCountry(String Country) {
	    this.Country = Country;
	  }

	  /**
	   * Gets the last name.
	   *
	   * @return the last name
	   */
	  public String getLastName() {
	    return LastName;
	  }

	  /**
	   * Sets the last name.
	   *
	   * @param LastName the new last name
	   */
	  public void setLastName(String LastName) {
	    this.LastName = LastName;
	  }

	  /**
	   * Gets the city.
	   *
	   * @return the city
	   */
	  public String getCity() {
	    return City;
	  }

	  /**
	   * Sets the city.
	   *
	   * @param City the new city
	   */
	  public void setCity(String City) {
	    this.City = City;
	  }

	  /**
	   * Gets the postal code.
	   *
	   * @return the postal code
	   */
	  public String getPostalCode() {
	    return PostalCode;
	  }

	  /**
	   * Sets the postal code.
	   *
	   * @param PostalCode the new postal code
	   */
	  public void setPostalCode(String PostalCode) {
	    this.PostalCode = PostalCode;
	  }

	  /**
	   * Gets the state.
	   *
	   * @return the state
	   */
	  public String getState() {
	    return State;
	  }

	  /**
	   * Sets the state.
	   *
	   * @param State the new state
	   */
	  public void setState(String State) {
	    this.State = State;
	  }

	  /**
	   * Gets the full name.
	   *
	   * @return the full name
	   */
	  public String getFullName() {
	    return FullName;
	  }

	  /**
	   * Sets the full name.
	   *
	   * @param FullName the new full name
	   */
	  public void setFullName(String FullName) {
	    this.FullName = FullName;
	  }

	  /**
	   * Gets the email address.
	   *
	   * @return the email address
	   */
	  public String getEmailAddress() {
	    return EmailAddress;
	  }

	  /**
	   * Sets the email address.
	   *
	   * @param EmailAddress the new email address
	   */
	  public void setEmailAddress(String EmailAddress) {
	    this.EmailAddress = EmailAddress;
	  }

	  /**
	   * Gets the first name.
	   *
	   * @return the first name
	   */
	  public String getFirstName() {
	    return FirstName;
	  }

	  /**
	   * Sets the first name.
	   *
	   * @param FirstName the new first name
	   */
	  public void setFirstName(String FirstName) {
	    this.FirstName = FirstName;
	  }

	  /**
	   * Gets the free form.
	   *
	   * @return the free form
	   */
	  public String getFreeForm() {
	    return FreeForm;
	  }

	  /**
	   * Sets the free form.
	   *
	   * @param FreeForm the new free form
	   */
	  public void setFreeForm(String FreeForm) {
	    this.FreeForm = FreeForm;
	  }

	  /**
	   * Gets the address line1.
	   *
	   * @return the address line1
	   */
	  public String getAddressLine1() {
	    return AddressLine1;
	  }

	  /**
	   * Sets the address line1.
	   *
	   * @param AddressLine1 the new address line1
	   */
	  public void setAddressLine1(String AddressLine1) {
	    this.AddressLine1 = AddressLine1;
	  }

	  /**
	   * Gets the address line2.
	   *
	   * @return the address line2
	   */
	  public String getAddressLine2() {
	    return AddressLine2;
	  }

	  /**
	   * Sets the address line2.
	   *
	   * @param AddressLine2 the new address line2
	   */
	  public void setAddressLine2(String AddressLine2) {
	    this.AddressLine2 = AddressLine2;
	  }

	  /**
	   * Gets the last line.
	   *
	   * @return the last line
	   */
	  public String getLastLine() {
	    return LastLine;
	  }

	  /**
	   * Sets the last line.
	   *
	   * @param LastLine the new last line
	   */
	  public void setLastLine(String LastLine) {
	    this.LastLine = LastLine;
	  }

	  /**
	   * Gets the address house number.
	   *
	   * @return the address house number
	   */
	  public String getAddressHouseNumber() {
	    return AddressHouseNumber;
	  }

	  /**
	   * Sets the address house number.
	   *
	   * @param addressHouseNumber the new address house number
	   */
	  public void setAddressHouseNumber(String addressHouseNumber) {
	    AddressHouseNumber = addressHouseNumber;
	  }

	  /**
	   * Gets the address pre direction.
	   *
	   * @return the address pre direction
	   */
	  public String getAddressPreDirection() {
	    return AddressPreDirection;
	  }

	  /**
	   * Sets the address pre direction.
	   *
	   * @param addressPreDirection the new address pre direction
	   */
	  public void setAddressPreDirection(String addressPreDirection) {
	    AddressPreDirection = addressPreDirection;
	  }

	  /**
	   * Gets the address street name.
	   *
	   * @return the address street name
	   */
	  public String getAddressStreetName() {
	    return AddressStreetName;
	  }

	  /**
	   * Sets the address street name.
	   *
	   * @param addressStreetName the new address street name
	   */
	  public void setAddressStreetName(String addressStreetName) {
	    AddressStreetName = addressStreetName;
	  }

	  /**
	   * Gets the address street suffix.
	   *
	   * @return the address street suffix
	   */
	  public String getAddressStreetSuffix() {
	    return AddressStreetSuffix;
	  }

	  /**
	   * Sets the address street suffix.
	   *
	   * @param addressStreetSuffix the new address street suffix
	   */
	  public void setAddressStreetSuffix(String addressStreetSuffix) {
	    AddressStreetSuffix = addressStreetSuffix;
	  }

	  /**
	   * Gets the address post direction.
	   *
	   * @return the address post direction
	   */
	  public String getAddressPostDirection() {
	    return AddressPostDirection;
	  }

	  /**
	   * Sets the address post direction.
	   *
	   * @param addressPostDirection the new address post direction
	   */
	  public void setAddressPostDirection(String addressPostDirection) {
	    AddressPostDirection = addressPostDirection;
	  }

	  /**
	   * Gets the address suite number.
	   *
	   * @return the address suite number
	   */
	  public String getAddressSuiteNumber() {
	    return AddressSuiteNumber;
	  }

	  /**
	   * Sets the address suite number.
	   *
	   * @param addressSuiteNumber the new address suite number
	   */
	  public void setAddressSuiteNumber(String addressSuiteNumber) {
	    AddressSuiteNumber = addressSuiteNumber;
	  }

	  /**
	   * Gets the address suite name.
	   *
	   * @return the address suite name
	   */
	  public String getAddressSuiteName() {
	    return AddressSuiteName;
	  }

	  /**
	   * Sets the address suite name.
	   *
	   * @param addressSuiteName the new address suite name
	   */
	  public void setAddressSuiteName(String addressSuiteName) {
	    AddressSuiteName = addressSuiteName;
	  }

	  /*
	   * (non-Javadoc)
	   *
	   * @see java.lang.Object#toString()
	   */
	  public String toString() {
	    return "AddressRecord [RecordID=" + RecordID + ", AddressLine1=" + AddressLine1
	        + ", AddressLine2=" + AddressLine2 + ", City=" + City + ", State=" + State
	        + ", PostalCode=" + PostalCode + ", Country=" + Country + ", LastLine=" + LastLine
	        + ", FreeForm=" + FreeForm + ", FirstName=" + FirstName + ", LastName=" + LastName
	        + ", FullName=" + FullName + ", PhoneNumber=" + PhoneNumber + ", EmailAddress="
	        + EmailAddress + ", CompanyName=" + CompanyName + ", AddressHouseNumber="
	        + AddressHouseNumber + ", AddressPreDirection=" + AddressPreDirection
	        + ", AddressStreetName=" + AddressStreetName + ", AddressStreetSuffix="
	        + AddressStreetSuffix + ", AddressPostDirection=" + AddressPostDirection
	        + ", AddressSuiteNumber=" + AddressSuiteNumber + ", AddressSuiteName=" + AddressSuiteName
	        + "]";
	  }
	
}
