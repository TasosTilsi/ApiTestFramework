package services.soap;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.slf4j.Logger;
import utils.config.EnvDataConfig;
import utils.service.implementation.Soap;

import static org.apache.http.HttpStatus.SC_OK;

public class SoapCommonRequests {
    
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SoapCommonRequests.class);
    private static String apiUsername;
    private static String apiPassword;
    public SoapCommonValidations validate;
    protected Response response;
    protected Soap soap;
    protected EnvDataConfig envDataConfig;
    
    public SoapCommonRequests(Soap soap) {
        this.soap = soap;
        envDataConfig = new EnvDataConfig();
        validate = new SoapCommonValidations(soap);
    }
    
    public void applicationIsUpAndRunning() {
        logger.info(envDataConfig.getSoapApiUrl());
        RestAssured
                .given()
                .relaxedHTTPSValidation()
                .when()
                .get(envDataConfig.getSoapApiUrl())
                .then()
                .statusCode(SC_OK);
    }
    
    public Response postRequest(String baseUri, String basePath, String soapAction, String xmlBody) {
        this.response = soap.postRequest(baseUri, basePath, soapAction, xmlBody);
        return response;
    }
}
