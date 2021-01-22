package com.itafin.boe.geo;

public interface BoeWebService {
  
  public GISData requestByCoords(String latitude, String longitude);
  
}
