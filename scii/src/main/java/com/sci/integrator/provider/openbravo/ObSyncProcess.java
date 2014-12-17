package com.sci.integrator.provider.openbravo;

import javax.inject.Inject;
import javax.xml.transform.dom.DOMSource;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.sci.integrator.domain.core.AppSettings;
import com.sci.integrator.domain.core.SciiRequest;
import com.sci.integrator.domain.core.SciiResponse;
import com.sci.integrator.domain.core.SyncData;
import com.sci.integrator.provider.IProvider;
import com.sci.integrator.provider.IProviderSyncProcess;
import com.sci.integrator.provider.RestBaseProviderGateway;
import com.sci.integrator.services.ISyncDataService;
import com.sci.integrator.transaction.Transaction;


public class ObSyncProcess extends RestBaseProviderGateway implements IProviderSyncProcess
{

  
  // ********* Injected Components **********
 
  @Inject 
  private Jaxb2Marshaller marshaller;
    
  @Inject
  ISyncDataService         syncDataService;

  private String              strUrl;
  private RestTemplate        restTemplate;

  // ********* Properties **********
  
  SyncData syncData;
  
  // ********* Constructors **********
  
  public ObSyncProcess(String baseUrl)
  {
    strUrl = baseUrl;
    this.restTemplate = new RestTemplate();    
  }


  // ********* IProviderSyncProcess implementation methods **********


  public void synchronize()
  {

    HttpHeaders headers = this.createHeaders("DNDAdmin", "123");
    
    // *** Get Main Transaction request string ***
    SciiRequest mainRequest = buildMainRequest();

    if (mainRequest != null)
    {
      
      try
      {
  
        ResponseEntity<DOMSource> responseEntity;       
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(mainRequest.getStrRequest() , headers);
        responseEntity = restTemplate.exchange(strUrl + mainRequest.getUrlExtension(), mainRequest.getHttpMethod(), httpEntity, DOMSource.class, mainRequest.getWhereClause());

        SciiResponse response = new SciiResponse();
        response.setException(null);
        response.setResponseEntity(responseEntity);
        
        // *** Process response ***
        processMainResponse(response);
               
      }
      catch (HttpServerErrorException e)
      {
        
        mainRequest.setStatus(Transaction.STATUS_FAILED);
        
        SciiResponse response = new SciiResponse();
        response.setException(e);
        
        // *** Process response ***
        processMainResponse(response);
  
        throw e;
      }    
      finally
      {
      }
      
    }
    
  }

  
  // ********** Private Methods **********
  
  private SciiRequest buildMainRequest()
  {
    SciiRequest request = new SciiRequest();
    request.setUrlExtension("ws/com.spiritsci.spimo.getSyncData");
    request.setStrRequest("");
    request.setHttpMethod(HttpMethod.GET);
    request.setWhereClause("");

    System.out.println();

    return request;
  }

  public void processMainResponse(SciiResponse response)
  {
    
    DOMSource ds = response.getResponseEntity().getBody();
    
    // *** Unmarshall Response ***
    SyncData syncData = (SyncData)marshaller.unmarshal(ds);
    
    // *** Clear Database before saving new data ***
    //syncDataService.deleteSyncData();
    
    // *** Save to Database ***    
    syncDataService.saveSyncData(syncData);
    
    System.out.println(syncData.getTag());
    System.out.println(syncData.getSyncDate());
  }

  
}
