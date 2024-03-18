package services.rest;

import io.restassured.response.Response;
import org.slf4j.Logger;
import org.testng.Assert;
import utils.config.EnvDataConfig;
import utils.service.implementation.Rest;

import static org.apache.http.HttpStatus.SC_OK;


public class RestCommonValidations {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(RestCommonValidations.class);
    
    protected Rest rest;
    protected EnvDataConfig envDataConfig;
    Response lastResponse;
    
    public RestCommonValidations(Rest rest) {
        this.rest = rest;
        envDataConfig = new EnvDataConfig();
        lastResponse = rest.context().getLastResponse();
    }
    
    /**
     * Method to verify that the returned Status Code is equal to an expected.
     *
     * @param expectedStatusCode
     */
    public RestCommonValidations verifyStatusCode(int expectedStatusCode) {
        Assert.assertEquals(getLastResponse().statusCode(), expectedStatusCode);
        return this;
    }
    
    /**
     * Method to verify that the returned Status Code is equal to an expected.
     */
    public RestCommonValidations verifyStatusCode() {
        Assert.assertEquals(getLastResponse().statusCode(), SC_OK);
        return this;
    }
    
    /**
     * Method to verify that the returned Text contains an expected content.
     *
     * @param expectedText
     */
    public RestCommonValidations verifyResponseText(String expectedText) {
        Assert.assertTrue(getLastResponse().asString().contains(expectedText));
        return this;
    }
    
    public RestCommonValidations validateResponseCodeAndContentType(int statusCode, String contentType) {
        logger.info("Starting validating the response...");
        getLastResponse().then().statusCode(statusCode);
        getLastResponse().then().contentType(contentType);
        logger.info("The response was validated successfully!!");
        logger.info("Status code: {}", getLastResponse().getStatusCode());
        logger.info("Content type: {}", getLastResponse().contentType());
        return this;
    }
    
    public Response setLastResponse(Response response) {
        lastResponse = response;
        return lastResponse;
    }
    
    public Response getLastResponse() {
        return setLastResponse(rest.context().getLastResponse());
    }
    
}
