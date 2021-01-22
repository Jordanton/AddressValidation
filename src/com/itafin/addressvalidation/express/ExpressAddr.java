package com.itafin.addressvalidation.express;

import com.itafin.addressvalidation.model.Address;
import com.itafin.addressvalidation.model.ITSFullAddrData;

public interface ExpressAddr {

	public ExpressResponse expressAddrService(Address address);
	
	
}
