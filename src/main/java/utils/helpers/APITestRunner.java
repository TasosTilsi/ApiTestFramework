package utils.helpers;

import annotations.APITest;
import domain.RestEndpointEnum;
import domain.SoapActionEnum;
import domain.SoapBasePathEnum;
import io.restassured.http.Method;
import io.restassured.response.Response;
import utils.enums.RequestType;
import utils.service.interfaces.IBaseService;
import utils.service.interfaces.IRestService;
import utils.service.interfaces.ISoapService;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public class APITestRunner {
    private final java.lang.reflect.Method method;
    
    public APITestRunner(java.lang.reflect.Method method) {
        this.method = method;
    }
    
    public Response runAPITest(IBaseService webService) {
        Response response = null;
        if (method.isAnnotationPresent(APITest.class)) {
            APITest apiTest = method.getAnnotation(APITest.class);
            RequestType requestType = apiTest.requestType();
            if (requestType == RequestType.REST) {
                response = handleRestRequest(apiTest, webService);
            } else if (requestType == RequestType.SOAP) {
                response = handleSoapRequest(apiTest, webService);
            }
        }
        return response;
    }
    
    private Response handleSoapRequest(APITest apiTest, IBaseService webService) {
        SoapBasePathEnum endpoint = apiTest.soapEndpoint();
        SoapActionEnum soapAction = apiTest.soapAction();
        Object payload = apiTest.payload();
        return makeSoapRequest(webService, endpoint, soapAction, payload);
    }
    
    private Response handleRestRequest(APITest apiTest, IBaseService webService) {
        RestEndpointEnum endpoint = apiTest.restEndpoint();
        Method methodType = apiTest.method();
        String route = apiTest.route();
        Class<?> payload = apiTest.payload() != null && !NoPayload.class.isAssignableFrom(apiTest.payload()) ? apiTest.payload() : null;
        Map<String, Object> pathParams = parseParams(apiTest.pathParams());
        Map<String, Object> queryParams = parseParams(apiTest.queryParams());
        File file = apiTest.filePath() != null && !apiTest.filePath().isEmpty() ? new File(apiTest.filePath()) : null;
        return makeRestRequest(webService, methodType, endpoint, route, payload, pathParams, queryParams, file);
    }
    
    private Response makeRestRequest(IBaseService webService, Method methodType, RestEndpointEnum basePath, String route, Object payload, Map<String, Object> pathParams, Map<String, Object> queryParams, File file) {
        IRestService rest = webService.rest();
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
    
    private Response makeSoapRequest(IBaseService webService, SoapBasePathEnum basePath, SoapActionEnum soapAction, Object payload) {
        ISoapService soap = webService.soap();
        return soap.postRequest(basePath.getPath(), soapAction.getPath(), payload);
        
    }
    
    private Map<String, Object> parseParams(String[] params) {
        if (params == null) {
            return Collections.emptyMap();
        }
        
        return Arrays.stream(params)
                .map(param -> param.split("=", 2))
                .map(keyValue -> Map.entry(keyValue[0], keyValue.length > 1 ? (Object) keyValue[1] : null))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
