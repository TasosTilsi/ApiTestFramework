package TS_API_Users;

import annotations.APITest;
import annotations.SkipTest;
import domain.RestEndpointEnum;
import io.restassured.http.Method;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import services.rest.resource.UnknownResourceResponseDTO;
import utils.BaseTest;
import utils.enums.Environment;

import static org.apache.http.HttpStatus.SC_OK;

@Listeners(utils.listeners.SkipTestListener.class)
public class TC_API_DEMO extends BaseTest {
    
    @Test(description = "GET /api/{resource}")
    @APITest(restEndpoint = RestEndpointEnum.RESOURCE, method = Method.GET)
    public void TC_API_01_001_AuthorsTest_step1() throws NoSuchMethodException {
        UnknownResourceResponseDTO response = executeAPITest().as(UnknownResourceResponseDTO.class);
        
        step("Verify response is not null")
                .rest()
                .service()
                .commonRequests()
                .validate
                .validateResponseCodeAndContentType(SC_OK, "application/json");
        
        Assert.assertNotNull(response, "Response is null");
    }
    
    @Test(description = "GET /api/{resource} with query parameter - page")
    @APITest(restEndpoint = RestEndpointEnum.RESOURCE, method = Method.GET, queryParams = {"page=1"})
    public void TC_API_01_001_AuthorsTest_step2() throws NoSuchMethodException {
        UnknownResourceResponseDTO response = executeAPITest().as(UnknownResourceResponseDTO.class);
        
        step("Verify response is not null")
                .rest()
                .service()
                .commonRequests()
                .validate
                .validateResponseCodeAndContentType(SC_OK, "application/json");
        
        Assert.assertNotNull(response, "Response is null");
    }
    
    @Test(description = "GET /api/{resource} with query parameter - per_page")
    @APITest(restEndpoint = RestEndpointEnum.RESOURCE, method = Method.GET, queryParams = {"per_page=1"})
    public void TC_API_01_001_AuthorsTest_step3() throws NoSuchMethodException {
        UnknownResourceResponseDTO response = executeAPITest().as(UnknownResourceResponseDTO.class);
        
        step("Verify response is not null")
                .rest()
                .service()
                .commonRequests()
                .validate
                .validateResponseCodeAndContentType(SC_OK, "application/json");
        
        Assert.assertNotNull(response, "Response is null");
    }
    
    @Test(description = "GET /api/{resource} with all query parameters - page and per_page")
    @APITest(restEndpoint = RestEndpointEnum.RESOURCE, method = Method.GET, queryParams = {"page=2", "per_page=1"})
    public void TC_API_01_001_AuthorsTest_step4() throws NoSuchMethodException {
        UnknownResourceResponseDTO response = executeAPITest().as(UnknownResourceResponseDTO.class);
        
        step("Verify response is not null")
                .rest()
                .service()
                .commonRequests()
                .validate
                .validateResponseCodeAndContentType(SC_OK, "application/json");
        
        Assert.assertNotNull(response, "Response is null");
    }
    
    @Test(description = "GET /api/{resource} with body")
    @APITest(restEndpoint = RestEndpointEnum.RESOURCE, method = Method.GET, queryParams = {"page=2", "per_page=1"}, filePath = "/src/test/resources/TS_API_Users/TC_API_01_001_AuthorsTest_step5.json")
    @SkipTest(description = "Skip GET /api/{resource} with body", environments = {Environment.DEV})
    public void TC_API_01_001_AuthorsTest_step5() throws NoSuchMethodException {
        UnknownResourceResponseDTO response = executeAPITest().as(UnknownResourceResponseDTO.class);
        step("Verify response is not null")
                .rest()
                .service()
                .commonRequests()
                .validate
                .validateResponseCodeAndContentType(SC_OK, "application/json");
        
        Assert.assertNotNull(response, "Response is null");
    }
    
}
