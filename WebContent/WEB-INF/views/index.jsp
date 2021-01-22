<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

</head>

<body>
	<h1>Address Validation</h1>

	<form:form action="getLatLongT" method="post">
		
		<label>ITSLatLongData XML</label>
		<input type="text" name="latLongData" value="<ITSLatLongData><latitude>34.082144</latitude><longitude>-118.017247</longitude></ITSLatLongData>" />
		<br><br>
		<label>ITSFullAddrData XML</label>
		<input type="text" name="fullAddrData" value="<ITSFullAddrData><stdAddress>4315 Fandon Ave</stdAddress><streetNumber></streetNumber><streetName></streetName><streetNameSuffix></streetNameSuffix><city></city><state>CA</state><zip>91732</zip><zip4></zip4></ITSFullAddrData>" />
		<br><br>
		<button type="submit" class="btn btn-primary">
			getLatLong
		</button>
		
	</form:form>
	
	<br><br>
	
	<form:form action="matchAddressExactT" method="post">
		
		<label>ITSFullAddrData XML</label>
		<input type="text" name="fullAddrData" value="<ITSFullAddrData><stdAddress>4315 Fandon Ave</stdAddress><streetNumber></streetNumber><streetName></streetName><streetNameSuffix></streetNameSuffix><city></city><state>CA</state><zip>91732</zip><zip4></zip4></ITSFullAddrData>" />
		<br><br>
		<button type="submit" class="btn btn-primary">
			matchAddressExact
		</button>
		
	</form:form>
	
	<br><br>
	
	<form:form action="matchAddressFuzzyT" method="post">
		
		<label>ITSFullAddrData XML</label>
		<input type="text" name="fullAddrData" value="<ITSFullAddrData><stdAddress></stdAddress><streetNumber>4315</streetNumber><streetName></streetName><streetNameSuffix></streetNameSuffix><city>El Monte</city><state>CA</state><zip>90703</zip><zip4></zip4></ITSFullAddrData>" />
		<br><br>
		<button type="submit" class="btn btn-primary">
			matchAddressFuzzy
		</button>
		
	</form:form>
	
	<br><br>
	
	<p>Sample of data that will be returned as ITS model objects (will be returned as JSON if REST controller is called):</p>
	<p>${msg}</p>

</body>
	
</html>