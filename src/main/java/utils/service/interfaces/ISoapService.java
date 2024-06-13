package utils.service.interfaces;


import io.restassured.response.Response;
import models.ContextData;
import utils.factories.SoapServiceObjectFactory;

/**
 * This interface represents a SOAP service that extends the BaseService interface.
 * It provides methods to create a service object factory and make a POST request with SOAP parameters.
 */
public interface ISoapService {
    
    /**
     * Returns a SoapServiceObjectFactory instance that can be used to create service objects.
     *
     * @return the SoapServiceObjectFactory instance
     */
    SoapServiceObjectFactory service();
    
    /**
     * Returns the context factory for this SOAP service.
     *
     * @return the context factory
     */
    ContextData context();
    
    /**
     * Makes a POST request to the specified base URI and base path with the given SOAP action and XML body.
     *
     * @param baseUri    the base URI of the SOAP service
     * @param basePath   the base path of the SOAP service
     * @param soapAction the SOAP action to be performed
     * @param xmlBody    the XML body of the SOAP request
     * @return the Response object representing the response of the SOAP request
     */
    Response postRequest(String baseUri, String basePath, String soapAction, Object xmlBody);
    
    /**
     * Makes a POST request to the specified SOAP service.
     *
     * @param basePath   the path of the service
     * @param soapAction the SOAP action to be performed
     * @param xmlBody    the XML body of the request
     * @return a Response object representing the response of the POST request
     */
    Response postRequest(String basePath, String soapAction, Object xmlBody);
}
