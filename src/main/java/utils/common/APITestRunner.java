package utils.common;

import annotations.APITest;
import domain.RestEndpointEnum;
import domain.SoapActionEnum;
import domain.SoapBasePathEnum;
import io.restassured.http.Method;
import io.restassured.response.Response;
import utils.enums.RequestType;
import utils.service.implementation.Rest;
import utils.service.implementation.Soap;
import utils.service.implementation.WebService;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class APITestRunner {
    public Response runAPITest(WebService webService, java.lang.reflect.Method method, Object[] args) {
        Response response = null;
        if (method.isAnnotationPresent(APITest.class)) {
            APITest apiTest = method.getAnnotation(APITest.class);
            
            RequestType requestType = apiTest.requestType();
            
            if (requestType == RequestType.REST) {
                // Handle REST request
                RestEndpointEnum endpoint = apiTest.restEndpoint();
                Method methodType = apiTest.method();
                String route = apiTest.route();
                Object payload = apiTest.payload();
                Map<String, Object> pathParams = parseParams(apiTest.pathParams());
                Map<String, Object> queryParams = parseParams(apiTest.queryParams());
                File file = null;
                if (apiTest.filePath() != null && !apiTest.filePath().isEmpty()) {
                    file = new File(apiTest.filePath());
                }
                response = makeRestRequest(webService, methodType, endpoint, route, payload, pathParams, queryParams, file);
            } else if (requestType == RequestType.SOAP) {
                // Handle SOAP request
                SoapBasePathEnum endpoint = apiTest.soapEndpoint();
                SoapActionEnum soapAction = apiTest.soapAction();
                Object payload = apiTest.payload();
                response = makeSoapRequest(webService, endpoint, soapAction, payload);
            }
        }
        
        return response;
    }
    
    private Response makeRestRequest(WebService webService, Method methodType, RestEndpointEnum basePath, String route, Object payload, Map<String, Object> pathParams, Map<String, Object> queryParams, File file) {
        Rest rest = webService.rest();
        return rest.sendRequest(
                rest.createRequest(rest.getRequestSpec(),
                        basePath,
                        route,
                        payload,
                        pathParams,
                        queryParams,
                        file),
                methodType,
                route);
    }
    
    private Response makeSoapRequest(WebService webService, SoapBasePathEnum basePath, SoapActionEnum soapAction, Object payload) {
        Soap soap = webService.soap();
        return soap.postRequest(basePath, soapAction, payload);
        
    }
    
    private Map<String, Object> parseParams(String[] params) {
        Map<String, Object> paramMap = new HashMap<>();
        if (params != null) {
            for (String param : params) {
                String[] keyValue = param.split("=", 2);
                if (keyValue.length == 2) {
                    paramMap.put(keyValue[0], keyValue[1]);
                } else if (keyValue.length == 1) {
                    paramMap.put(keyValue[0], null);
                }
            }
        }
        return paramMap;
    }
}
