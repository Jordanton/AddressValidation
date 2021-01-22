/*
 * Copyright 2018 LATAX, Inc. All rights reserved.
 *
 */
package com.itafin.boe.geo;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.JAXB;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import com.itafin.boe.geo.response.exactmatch.AddressResponse;

@Component
public class BoeWebServiceImpl implements BoeWebService {
  
  private static final Logger logger = Logger.getLogger(BoeWebServiceImpl.class);

  enum RespStatusType {
    EXACT_MATCH("exactMatch"), MULTIPLE_MATCH("multipleMatch"), NO_MATCH("noMatch"), ERROR("error");

    private static final Map<String, RespStatusType> lookupMap =
        new HashMap<String, RespStatusType>();
    static {
      // Create reverse lookup hash map
      for (RespStatusType qType : RespStatusType.values())
        lookupMap.put(qType.getValue(), qType);
    }
    String statusValue;

    private RespStatusType(String statusVal) {
      statusValue = statusVal;
    }

    public String getValue() {
      return statusValue;
    }

    public static RespStatusType getRespStatusType(String statusVal) {
      return (RespStatusType) lookupMap.get(statusVal);
    }

  };
  
  public GISData requestByCoords(String latitude, String longitude) {
    
    GISData gisData = null;
    
    try {
      
      String queryType = "latax";
      String wsURL = "http://navigatela.lacity.org/cfc/geocode_services/geoQueryLA.cfc";
      URL url = new URL(wsURL);
      URLConnection connection = url.openConnection();
      HttpURLConnection httpConn = (HttpURLConnection) connection;
      ByteArrayOutputStream bout = new ByteArrayOutputStream();
      String xmlInput =
          "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\" "
          + "xmlns:tns=\"http://geocode_services.cfc\" xmlns:types=\"http://geocode_services.cfc/encodedTypes\" "
          + "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">"
            + "<soap:Body soap:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">"
              + "<tns:qByCoords>"
                + "<qVal xsi:type=\"xsd:string\">&lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?&gt; &lt;Address-Request&gt;    "
                + "&lt;qWhat&gt;addr_int&lt;/qWhat&gt;     &lt;status&gt;" 
                + queryType
                + "&lt;/status&gt;  &lt;coordinates&gt;"
                + longitude + "|" + latitude
                + "&lt;/coordinates&gt;    &lt;address-geocode&gt;"
                + "&lt;/address-geocode&gt;     &lt;layers&gt;      &lt;layer&gt;           &lt;name&gt;CTYEMPZN&lt;/name&gt;           &lt;fields&gt;              "
                + "&lt;field&gt;TAPSCD&lt;/field&gt;           &lt;/fields&gt;         &lt;/layer&gt;      &lt;layer&gt;           "
                + "&lt;name&gt;LATAX_ENFDIST&lt;/name&gt;          &lt;fields&gt;              &lt;field&gt;LATAX_DIST&lt;/field&gt;           &lt;/fields&gt;         "
                + "&lt;/layer&gt;      &lt;layer&gt;           &lt;name&gt;council districts&lt;/name&gt;          &lt;fields&gt;              "
                + "&lt;field&gt;cd_no&lt;/field&gt;            &lt;/fields&gt;         &lt;/layer&gt;  &lt;/layers&gt; &lt;/Address-Request&gt;</qVal>"
                + "<apikeyid>8FBD117C39DA4E8FBB0806953E78206D</apikeyid>" 
              + "</tns:qByCoords>"
            + "</soap:Body>" 
          + "</soap:Envelope>";
      
      logger.info("BOE Request: " + xmlInput);
      
      byte[] buffer = new byte[xmlInput.length()];
      buffer = xmlInput.getBytes();
      bout.write(buffer);
      byte[] b = bout.toByteArray();
      String SOAPAction = "http://navigatela.lacity.org/cfc/geocode_services/geoQueryLA.cfc";
      // Set the appropriate HTTP parameters.
      httpConn.setRequestProperty("Content-Length", String.valueOf(b.length));
      httpConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
      httpConn.setRequestProperty("SOAPAction", SOAPAction);
      httpConn.setRequestMethod("POST");
      httpConn.setDoOutput(true);
      httpConn.setDoInput(true);
      OutputStream out = httpConn.getOutputStream();
      // Write the content of the request to the outputstream of the HTTP Connection.
      out.write(b);
      out.close();
      // Ready with sending the request.

      // Read the response.
      InputStreamReader isr = new InputStreamReader(httpConn.getInputStream());
      BufferedReader in = new BufferedReader(isr);
      
      String responseString = "";
      String outputString = "";

      // Write the SOAP message response to a String and format.
      while ((responseString = in.readLine()) != null) {
        outputString = outputString + responseString;
      }
      outputString = outputString.replaceAll("\\s+", "");
      outputString = outputString.replaceAll("\\t+", "");

      outputString = outputString.replaceAll("&lt;", "<");
      outputString = outputString.replaceAll("&gt;", ">");
      outputString = outputString.replaceAll("&quot;", "\"");
      outputString = outputString.replaceAll("layername", "layer name");

      String[] parts = outputString.split("<\\?xmlversion=\"1.0\"encoding=\"UTF-8\"\\?>");
      parts = parts[1].split("</qByCoordsReturn>");
      outputString = parts[0];
      
      logger.info("BOE Response: " + outputString);

      gisData = handleResult(outputString);
      
    } catch (Exception e) {
      System.out.println("Exception::" + e.toString());
      e.printStackTrace();

    }
    
    return gisData;
  }

  /**
   * 
   * @param respAddrXmlStr
   * @return
   */
  private GISData handleResult(String respAddrXmlStr) {

    GISData gisData = new GISData();

    // Remove empty space
    String respAddrXmlStrTrimmed = respAddrXmlStr.trim();

    // Convert back to AddressResponse object from XML string
    // Default to use exact-match since all response type have the same
    // status field
    AddressResponse addressResponseExactMatch =
        JAXB.unmarshal(new StringReader(respAddrXmlStrTrimmed), AddressResponse.class);
    String retAddrStatus = addressResponseExactMatch.getStatus();

    RespStatusType respStatus = RespStatusType.getRespStatusType(retAddrStatus);

    switch (respStatus) {
      case EXACT_MATCH:

        gisData.latitude = addressResponseExactMatch.getCoords().getLatitude();
        gisData.longitude = addressResponseExactMatch.getCoords().getLongitude();

        // retrieve LATAX_DIST
        for (int i = 0; i < addressResponseExactMatch.getLayers().getLayer().size(); i++) {
          if (addressResponseExactMatch.getLayers().getLayer().get(i).getName()
              .equalsIgnoreCase("LATAX_ENFDIST")) {
            for (int j = 0; j < addressResponseExactMatch.getLayers().getLayer().get(i).getFields()
                .getField().size(); j++) {
              if (addressResponseExactMatch.getLayers().getLayer().get(i).getFields().getField()
                  .get(j).getFieldName().equalsIgnoreCase("LATAX_DIST")) {
                gisData.latax_dist = addressResponseExactMatch.getLayers().getLayer().get(i)
                    .getFields().getField().get(j).getValue();
              }
            }
          }
        }

        // retrieve cd_no
        for (int i = 0; i < addressResponseExactMatch.getLayers().getLayer().size(); i++) {
          if (addressResponseExactMatch.getLayers().getLayer().get(i).getName()
              .equalsIgnoreCase("councildistricts")) {
            for (int j = 0; j < addressResponseExactMatch.getLayers().getLayer().get(i).getFields()
                .getField().size(); j++) {
              if (addressResponseExactMatch.getLayers().getLayer().get(i).getFields().getField()
                  .get(j).getFieldName().equalsIgnoreCase("cd_no")) {
                gisData.cd_no = addressResponseExactMatch.getLayers().getLayer().get(i).getFields()
                    .getField().get(j).getValue();
              }
            }
          }
        }

        // retrieve TAPSCD
        for (int i = 0; i < addressResponseExactMatch.getLayers().getLayer().size(); i++) {
          if (addressResponseExactMatch.getLayers().getLayer().get(i).getName()
              .equalsIgnoreCase("LATAX_ENFDIST")) {
            for (int j = 0; j < addressResponseExactMatch.getLayers().getLayer().get(i).getFields()
                .getField().size(); j++) {
              if (addressResponseExactMatch.getLayers().getLayer().get(i).getFields().getField()
                  .get(j).getFieldName().equalsIgnoreCase("TAPSCD")) {
                gisData.taps_cd = addressResponseExactMatch.getLayers().getLayer().get(i)
                    .getFields().getField().get(j).getValue();
              }
            }
          }
        }

        gisData.in_city = true;

        break;
      case MULTIPLE_MATCH:
        gisData.in_city = false;
        break;
      case NO_MATCH:
        gisData.in_city = false;
        break;
      case ERROR:
        gisData.in_city = false;
        break;
      default:
        gisData.in_city = false;
        break;
    }
    return gisData;

  }

}

//public GISData requestByAddress(String address) {

//  String queryType = "new";
////  String address = "1843 S LA CI, 90035";
//
//    String xmlInput =
//        "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\" "
//        + "xmlns:types=\"http://geocode_services.cfc/encodedTypes\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
//        + "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">"
//          + "<soap:Body soap:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">"
//            + "<ns1:addressValidationService xmlns:ns1=\"http://geocode_services.cfc\">"
//              + "<addressSearch xsi:type=\"xsd:string\">&lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?&gt; &lt;Address-Request&gt;   &lt;qWhat&gt;addr_int&lt;/q"
//              + "What&gt;     &lt;status&gt;"
//              + queryType
//              + "&lt;/status&gt;    &lt;coordinates&gt;0|0&lt;/coordinates&gt;  &lt;address-geocode&gt;"
//              + address
//              + "&lt;/address-geocode&gt;  &lt;layers&gt;      &lt;layer&gt;           &lt;name&gt;CTYEMPZN&lt;/name&gt;           &lt;fields&gt;              "
//              + "&lt;field&gt;TAPSCD&lt;/field&gt;           &lt;/fields&gt;         &lt;/layer&gt;      &lt;layer&gt;           &lt;name&gt;LATAX_ENFDIST&lt;/name&gt;          "
//              + "&lt;fields&gt;              &lt;field&gt;LATAX_DIST&lt;/field&gt;           &lt;/fields&gt;         &lt;/layer&gt;      &lt;layer&gt;           "
//              + "&lt;name&gt;council districts&lt;/name&gt;          &lt;fields&gt;              &lt;field&gt;cd_no&lt;/field&gt;            &lt;/fields&gt;         "
//              + "&lt;/layer&gt;  &lt;/layers&gt; &lt;/Address-Request&gt;</addressSearch>"
//              + "<apikeyid>8FBD117C39DA4E8FBB0806953E78206D</apikeyid>"
//            + "</ns1:addressValidationService>"
//          + "</soap:Body>"
//        + "</soap:Envelope>";

//  parts = parts[1].split("</addressValidationServiceReturn>");
