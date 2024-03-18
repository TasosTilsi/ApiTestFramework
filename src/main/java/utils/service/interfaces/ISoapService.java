package utils.service.interfaces;


import io.restassured.response.Response;
import utils.factories.SoapServiceObjectFactory;

/**
 * This interface represents a SOAP service that extends the BaseService interface.
 * It provides methods to create a service object factory and make a POST request with SOAP parameters.
 */
public interface ISoapService extends IBaseService {
    
    /**
     * Returns a SoapServiceObjectFactory instance that can be used to create service objects.
     *
     * @return the SoapServiceObjectFactory instance
     */
    SoapServiceObjectFactory service();
    
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
}
