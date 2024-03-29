package TS_API_Users;

import annotations.APITest;
import domain.RestEndpointEnum;
import io.restassured.http.Method;
import org.testng.Assert;
import org.testng.annotations.Test;
import services.rest.resource.UnknownResourceResponseDTO;
import utils.BaseTest;

import static org.apache.http.HttpStatus.SC_OK;

public class TC_API_DEMO extends BaseTest {
    
    @Test(description = "GET /api/{resource}")
    @APITest(restEndpoint = RestEndpointEnum.RESOURCE, method = Method.GET)
    public void TC_API_01_001_AuthorsTest_step1() throws NoSuchMethodException {
        executeAPITest();
        UnknownResourceResponseDTO response = step("Verify response is not null")
                .rest()
                .service()
                .commonRequests()
                .validate
                .validateResponseCodeAndContentType(SC_OK, "application/json")
                .getLastResponse()
                .as(UnknownResourceResponseDTO.class);
        Assert.assertNotNull(response, "Response is null");
    }
    
    @Test(description = "GET /api/{resource} with query parameter - page")
    @APITest(restEndpoint = RestEndpointEnum.RESOURCE, method = Method.GET, queryParams = {"page=1"})
    public void TC_API_01_001_AuthorsTest_step2() throws NoSuchMethodException {
        executeAPITest();
        UnknownResourceResponseDTO response = step("Verify response is not null")
                .rest()
                .service()
                .commonRequests()
                .validate
                .validateResponseCodeAndContentType(SC_OK, "application/json")
                .getLastResponse()
                .as(UnknownResourceResponseDTO.class);
        Assert.assertNotNull(response, "Response is null");
    }
    
    @Test(description = "GET /api/{resource} with query parameter - per_page")
    @APITest(restEndpoint = RestEndpointEnum.RESOURCE, method = Method.GET, queryParams = {"per_page=1"})
    public void TC_API_01_001_AuthorsTest_step3() throws NoSuchMethodException {
        executeAPITest();
        UnknownResourceResponseDTO response = step("Verify response is not null")
                .rest()
                .service()
                .commonRequests()
                .validate
                .validateResponseCodeAndContentType(SC_OK, "application/json")
                .getLastResponse()
                .as(UnknownResourceResponseDTO.class);
        Assert.assertNotNull(response, "Response is null");
    }
    
    @Test(description = "GET /api/{resource} with all query parameters - page and per_page")
    @APITest(restEndpoint = RestEndpointEnum.RESOURCE, method = Method.GET, queryParams = {"page=2", "per_page=1"})
    public void TC_API_01_001_AuthorsTest_step4() throws NoSuchMethodException {
        executeAPITest();
        UnknownResourceResponseDTO response = step("Verify response is not null")
                .rest()
                .service()
                .commonRequests()
                .validate
                .validateResponseCodeAndContentType(SC_OK, "application/json")
                .getLastResponse()
                .as(UnknownResourceResponseDTO.class);
        Assert.assertNotNull(response, "Response is null");
    }
    
    @Test(description = "GET /api/{resource} with body")
    @APITest(restEndpoint = RestEndpointEnum.RESOURCE, method = Method.GET, queryParams = {"page=2", "per_page=1"}, filePath = "/src/test/resources/TS_API_Users/TC_API_01_001_AuthorsTest_step5.json")
    public void TC_API_01_001_AuthorsTest_step5() throws NoSuchMethodException {
        executeAPITest();
        UnknownResourceResponseDTO response = step("Verify response is not null")
                .rest()
                .service()
                .commonRequests()
                .validate
                .validateResponseCodeAndContentType(SC_OK, "application/json")
                .getLastResponse()
                .as(UnknownResourceResponseDTO.class);
        Assert.assertNotNull(response, "Response is null");
    }
}
