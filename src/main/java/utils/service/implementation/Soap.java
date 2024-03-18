package utils.service.implementation;


import domain.SoapActionEnum;
import domain.SoapBasePathEnum;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import models.ContextData;
import org.slf4j.Logger;
import utils.config.EnvDataConfig;
import utils.factories.SoapServiceObjectFactory;
import utils.service.interfaces.ISoapService;

/**
 * This class represents a SOAP service and implements the SoapService interface.
 * It provides methods for creating service objects, accessing the logger, and
 * making POST requests.
 */
public class Soap implements ISoapService {
    
    public static final String SOAP_CONTENT_TYPE = "application/soap+xml; charset=utf-8";
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(Soap.class);
    private SoapServiceObjectFactory soapServiceObjectFactory;
    private ContextData contextData;
    private EnvDataConfig envDataConfig;
    
    /**
     * Constructs a new instance of the Soap class.
     * Initializes the soapServiceObjectFactory, logger, and contextFactory.
     */
    public Soap() {
        this.soapServiceObjectFactory = new SoapServiceObjectFactory(Soap.this);
        this.contextData = new ContextData();
        this.envDataConfig = new EnvDataConfig();
    }
    
    /**
     * Returns an instance of the SoapServiceObjectFactory class.
     * This factory is used to create objects related to the SOAP service.
     *
     * @return an instance of SoapServiceObjectFactory
     */
    @Override
    public SoapServiceObjectFactory service() {
        return this.soapServiceObjectFactory;
    }
    
    /**
     * Returns the context factory for this SOAP service.
     *
     * @return the context factory
     */
    @Override
    public ContextData context() {
        return this.contextData;
    }
    
    /**
     * Makes a POST request to the specified SOAP service.
     *
     * @param basePath   the path of the service
     * @param soapAction the SOAP action to be performed
     * @param xmlBody    the XML body of the request
     * @return a Response object representing the response of the POST request
     */
    public Response postRequest(String basePath, String soapAction, Object xmlBody) {
        return postRequest(envDataConfig.getSoapApiUrl(), basePath, soapAction, xmlBody);
    }
    
    /**
     * Makes a POST request to the specified SOAP service.
     *
     * @param baseUri    the base URI of the service
     * @param basePath   the path of the service
     * @param soapAction the SOAP action to be performed
     * @param xmlBody    the XML body of the request
     * @return a Response object representing the response of the POST request
     */
    @Override
    public Response postRequest(String baseUri, String basePath, String soapAction, Object xmlBody) {
        Response response = RestAssured.given()
                .when()
                .log()
                .all()
                .relaxedHTTPSValidation()
                .request()
                .baseUri(baseUri)
                .basePath(basePath)
                .header("SOAPAction", soapAction)
                .header("Content-Type", SOAP_CONTENT_TYPE)
                .body(xmlBody)
                .post();
        this.contextData.setLastResponse(response);
        return response;
    }
    
    /**
     * Makes a POST request to the specified SOAP service.
     *
     * @param basePath   the path of the service
     * @param soapAction the SOAP action to be performed
     * @param xmlBody    the XML body of the request
     * @return a Response object representing the response of the POST request
     */
    public Response postRequest(SoapBasePathEnum basePath, SoapActionEnum soapAction, Object xmlBody) {
        return postRequest(envDataConfig.getSoapApiUrl(), basePath.getPath(), soapAction.getValue(), xmlBody);
    }
}
