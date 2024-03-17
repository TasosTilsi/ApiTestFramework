package services.soap;

import io.restassured.response.Response;
import org.slf4j.Logger;
import org.testng.Assert;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import utils.config.EnvDataConfig;
import utils.service.implementation.Soap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

public class SoapCommonValidations {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SoapCommonValidations.class);
    
    protected Soap soap;
    protected EnvDataConfig envDataConfig;
    protected Response lastResponse;
    
    public SoapCommonValidations(Soap soap) {
        this.soap = soap;
        envDataConfig = new EnvDataConfig();
        lastResponse = soap.context().getLastResponse();
    }
    
    /**
     * Method to verify that the returned Status Code is equal to an expected.
     *
     * @param expectedStatusCode
     */
    public SoapCommonValidations verifyStatusCode(int expectedStatusCode) {
        Assert.assertEquals(soap.context().getLastResponse().statusCode(), (long) expectedStatusCode);
        return this;
    }
    
    /**
     * Method to verify that the returned Status Code is one from a list of expected.
     *
     * @param expectedStatusCodes
     */
    public SoapCommonValidations verifyStatusCodes(List<Integer> expectedStatusCodes) {
        for (int validStatusCode : expectedStatusCodes) {
            try {
                verifyStatusCode(validStatusCode);
                return this;
            } catch (AssertionError var5) {
                if (validStatusCode != expectedStatusCodes.get(expectedStatusCodes.size() - 1)) {
                    continue;
                }
                throw var5;
            }
        }
        return this;
    }
    
    /**
     * Method to verify that the returned Text contains an expected content.
     *
     * @param expectedText
     */
    public SoapCommonValidations verifyResponseText(String expectedText) {
        Assert.assertTrue(soap.context().getLastResponse().asString().contains(expectedText));
        return this;
    }
    
    /**
     * Method to verify that the response contains the expected count of the parent group of elements.
     *
     * @param expectedCount that defines expectedCount
     */
    public SoapCommonValidations verifyResponseCount(int expectedCount) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(soap.context().getLastResponse().getBody().asString()));
        Document doc = builder.parse(is);
        
        int actualCount = doc.getElementsByTagName("*").getLength();
        Assert.assertEquals(actualCount, expectedCount);
        return this;
    }
    
    public SoapCommonRequests validateResponseCodeAndContentType(int statusCode, String contentType) {
        logger.info("Starting validating the response...");
        soap.context().getLastResponse().then().statusCode(statusCode);
        soap.context().getLastResponse().then().contentType(contentType);
        logger.info("The response was validated successfully!!");
        logger.info("Status code: " + soap.context().getLastResponse().getStatusCode());
        logger.info("Content type: " + soap.context().getLastResponse().contentType());
        return new SoapCommonRequests(soap);
    }
    
}
