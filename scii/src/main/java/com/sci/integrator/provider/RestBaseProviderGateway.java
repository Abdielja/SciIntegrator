/**
 * 
 */
package com.sci.integrator.provider;

import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpHeaders;

/**
 * @author Abdiel Jaramillo Ojedis.
 * 
 */
public class RestBaseProviderGateway
{

  private String baseUrl;
  
  /**
   * @return the baseUrl
   */
  public String getBaseUrl()
  {
    return baseUrl;
  }

  /**
   * @param baseUrl the baseUrl to set
   */
  public void setBaseUrl(String baseUrl)
  {
    this.baseUrl = baseUrl;
  }

  public HttpHeaders createHeaders(String _username, String _password)
  {

    final String username = _username;
    final String password = _password;

    return new HttpHeaders()
    {
      {
        String auth = username + ":" + password;
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes());
        String authHeader = "Basic " + new String(encodedAuth);
        set("Authorization", authHeader);
      }
    };
  }

}
