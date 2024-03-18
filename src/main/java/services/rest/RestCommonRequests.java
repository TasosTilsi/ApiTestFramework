package services.rest;

import domain.RestEndpointEnum;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import utils.config.EnvDataConfig;
import utils.config.ResourcesConfig;
import utils.service.implementation.Rest;

import java.io.File;
import java.util.Map;

public class RestCommonRequests {
    
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(RestCommonRequests.class);
    
    public RestCommonValidations validate;
    protected Rest rest;
    protected EnvDataConfig envDataConfig;
    protected ResourcesConfig resourcesConfig;
    protected Response response;
    
    public RestCommonRequests(Rest rest) {
        this.rest = rest;
        envDataConfig = new EnvDataConfig();
        resourcesConfig = new ResourcesConfig();
        validate = new RestCommonValidations(rest);
    }
    
    /**
     * Sends a POST request to the specified endpoint with the given body.
     *
     * @param basePath The base path of the REST API endpoint.
     * @param route    The route of the REST API endpoint.
     * @param body     The body of the HTTP request.
     * @return The HTTP response.
     */
    public Response postRequest(RestEndpointEnum basePath, String route, Object body) {
        this.response = rest.postRequest(basePath, route, body);
        return response;
    }
    
    /**
     * Sends a POST request to the specified endpoint with the given body and path parameters.
     *
     * @param basePath   The base path of the REST API endpoint.
     * @param route      The route of the REST API endpoint.
     * @param body       The body of the HTTP request.
     * @param pathParams The path parameters of the HTTP request.
     * @return The HTTP response.
     */
    public Response postRequest(RestEndpointEnum basePath, String route, Object body, Map<String, Object> pathParams) {
        this.response = rest.postRequest(basePath, route, body, pathParams);
        return response;
    }
    
    /**
     * Sends a POST request to the specified endpoint with the given body and parameter name/value pair as path parameters.
     *
     * @param basePath           The base path of the REST API endpoint.
     * @param route              The route of the REST API endpoint.
     * @param body               The body of the HTTP request.
     * @param pathParameterName  The name of the path parameter.
     * @param pathParameterValue The value of the path parameter.
     * @return The HTTP response.
     */
    public Response postRequest(RestEndpointEnum basePath, String route, Object body, String pathParameterName, Object pathParameterValue) {
        this.response = rest.postRequest(basePath, route, body, pathParameterName, pathParameterValue);
        return response;
    }
    
    
    /**
     * Sends a multipart/form-data POST request to the specified endpoint with the given body and file.
     *
     * @param basePath The base path of the REST API endpoint.
     * @param route    The route of the REST API endpoint.
     * @param body     The body of the HTTP request.
     * @param file     The file to be uploaded.
     * @return The HTTP response.
     */
    public Response postRequest(RestEndpointEnum basePath, String route, Object body, File file) {
        this.response = rest.postRequest(basePath, route, body, file);
        return response;
    }
    
    /**
     * Sends a multipart/form-data POST request to the specified endpoint with the given body and path parameters.
     *
     * @param basePath   The base path of the REST API endpoint.
     * @param route      The route of the REST API endpoint.
     * @param body       The body of the HTTP request.
     * @param pathParams The path parameters of the HTTP request.
     * @param file       The file to be uploaded.
     * @return The HTTP response.
     */
    public Response postRequest(RestEndpointEnum basePath, String route, Object body, Map<String, Object> pathParams, File file) {
        this.response = rest.postRequest(basePath, route, body, pathParams, file);
        return response;
    }
    
    /**
     * Sends a multipart/form-data POST request to the specified endpoint with the given body and parameter name/value pair as path parameters.
     *
     * @param basePath           The base path of the REST API endpoint.
     * @param route              The route of the REST API endpoint.
     * @param body               The body of the HTTP request.
     * @param pathParameterName  The name of the path parameter.
     * @param pathParameterValue The value of the path parameter.
     * @param file               The file to be uploaded.
     * @return The HTTP response.
     */
    public Response postRequest(RestEndpointEnum basePath, String route, Object body, String pathParameterName, Object pathParameterValue, File file) {
        this.response = rest.postRequest(basePath, route, body, pathParameterName, pathParameterValue, file);
        return response;
    }
    
    /**
     * Sends a multipart/form-data POST request to the specified endpoint with the given body and query parameters.
     *
     * @param basePath    The base path of the REST API endpoint.
     * @param route       The route of the REST API endpoint.
     * @param body        The body of the HTTP request.
     * @param file        The file to be uploaded.
     * @param queryParams The query parameters of the HTTP request.
     * @return The HTTP response.
     */
    public Response postRequest(RestEndpointEnum basePath, String route, Object body, File file, Map<String, Object> queryParams) {
        this.response = rest.postRequest(basePath, route, body, file, queryParams);
        return response;
    }
    
    /**
     * Sends a GET request to the specified endpoint.
     *
     * @param basePath The base path of the REST API endpoint.
     * @param route    The route of the REST API endpoint.
     * @return The HTTP response.
     */
    public Response getRequest(RestEndpointEnum basePath, String route) {
        this.response = rest.getRequest(basePath, route);
        return response;
    }
    
    public Response getRequest(RestEndpointEnum basePath, String route, ContentType contentType) {
        this.response = rest.getRequest(basePath, route, contentType);
        return response;
    }
    
    /**
     * Sends a GET request to the specified endpoint with the given query parameters.
     *
     * @param basePath    The base path of the REST API endpoint.
     * @param route       The route of the REST API endpoint.
     * @param queryParams The query parameters of the HTTP request.
     * @return The HTTP response.
     */
    public Response getRequestWithQueryParams(RestEndpointEnum basePath, String route, Map<String, Object> queryParams) {
        this.response = rest.getRequestWithQueryParams(basePath, route, queryParams);
        return response;
    }
    
    /**
     * Retrieves a response by sending a GET request to a specified REST endpoint
     * with query parameters.
     *
     * @param basePath       the base path of the REST endpoint
     * @param route          the route of the REST endpoint
     * @param parameterName  the name of the query parameter
     * @param parameterValue the value of the query parameter
     * @return the response obtained from the REST API
     */
    public Response getRequestWithQueryParams(RestEndpointEnum basePath, String route, String parameterName, Object parameterValue) {
        this.response = rest.getRequestWithQueryParams(basePath, route, parameterName, parameterValue);
        return response;
    }
    
    /**
     * Sends a GET request to the specified endpoint with the given path parameters.
     *
     * @param basePath   The base path of the REST API endpoint.
     * @param route      The route of the REST API endpoint.
     * @param pathParams The path parameters of the HTTP request.
     * @return The HTTP response.
     */
    public Response getRequestWithPathParams(RestEndpointEnum basePath, String route, Map<String, Object> pathParams) {
        this.response = rest.getRequestWithPathParams(basePath, route, pathParams);
        return response;
    }
    
    public Response getRequestWithPathParams(RestEndpointEnum basePath, String route, Map<String, Object> pathParams, ContentType contentType) {
        this.response = rest.getRequestWithPathParams(basePath, route, pathParams, contentType);
        return response;
    }
    
    /**
     * Sends a GET request to the specified endpoint with the given parameter name/value pair as path parameter.
     *
     * @param basePath       The base path of the REST API endpoint.
     * @param route          The route of the REST API endpoint.
     * @param parameterName  The name of the path parameter.
     * @param parameterValue The value of the path parameter.
     * @return The HTTP response.
     */
    public Response getRequestWithPathParams(RestEndpointEnum basePath, String route, String parameterName, Object parameterValue) {
        this.response = rest.getRequestWithPathParams(basePath, route, parameterName, parameterValue);
        return response;
    }
    
    /**
     * Sends a PUT request to the specified endpoint with the given body and path parameters.
     *
     * @param basePath   The base path of the REST API endpoint.
     * @param route      The route of the REST API endpoint.
     * @param body       The body of the HTTP request.
     * @param pathParams The path parameters of the HTTP request.
     * @return The HTTP response.
     */
    public Response putRequest(RestEndpointEnum basePath, String route, Object body, Map<String, Object> pathParams) {
        this.response = rest.putRequest(basePath, route, body, pathParams);
        return response;
    }
    
    /**
     * Sends a PUT request to the specified endpoint with the given body and parameter name/value pair as path parameter.
     *
     * @param basePath       The base path of the REST API endpoint.
     * @param route          The route of the REST API endpoint.
     * @param body           The body of the HTTP request.
     * @param parameterName  The name of the path parameter.
     * @param parameterValue The value of the path parameter.
     * @return The HTTP response.
     */
    public Response putRequest(RestEndpointEnum basePath, String route, Object body, String parameterName, Object parameterValue) {
        this.response = rest.putRequest(basePath, route, body, parameterName, parameterValue);
        return response;
    }
    
    /**
     * Sends a PUT request to the specified REST endpoint.
     *
     * @param basePath   the base path of the REST endpoint
     * @param route      the route of the REST endpoint
     * @param pathParams the map of path parameters for the REST endpoint
     * @return the response received from the REST endpoint
     */
    public Response putRequest(RestEndpointEnum basePath, String route, Map<String, Object> pathParams) {
        this.response = rest.putRequest(basePath, route, pathParams);
        return response;
    }
    
    /**
     * Sends a PUT request to the specified REST endpoint.
     *
     * @param basePath       the base path of the REST endpoint
     * @param route          the route of the REST endpoint
     * @param parameterName  the name of the parameter
     * @param parameterValue the value of the parameter
     * @return the Response object returned by the function
     */
    public Response putRequest(RestEndpointEnum basePath, String route, String parameterName, Object parameterValue) {
        this.response = rest.putRequest(basePath, route, parameterName, parameterValue);
        return response;
    }
    
    /**
     * Sends a PATCH request to the specified endpoint.
     *
     * @return The HTTP response.
     */
    public Response patchRequest() {
        this.response = rest.patchRequest();
        return response;
    }
    
    /**
     * Sends a DELETE request to the specified REST endpoint. using the specified base path, route, and path parameters.
     *
     * @param basePath   the base path of the request
     * @param route      the route of the request
     * @param pathParams the path parameters of the request
     * @return the response of the request
     */
    public Response deleteRequest(RestEndpointEnum basePath, String route, Map<String, Object> pathParams) {
        this.response = rest.deleteRequest(basePath, route, pathParams);
        return response;
    }
    
    /**
     * Sends a DELETE request to the specified base path, route, parameter name, and parameter value.
     *
     * @param basePath           the base path of the REST endpoint
     * @param route              the route of the REST endpoint
     * @param pathParameterName  the name of the parameter
     * @param pathParameterValue the value of the parameter
     * @return the response from the delete request
     */
    public Response deleteRequest(RestEndpointEnum basePath, String route, String pathParameterName, Object pathParameterValue) {
        this.response = rest.deleteRequest(basePath, route, pathParameterName, pathParameterValue);
        return response;
    }
    
    /**
     * Sends a DELETE request to the specified base path, route, body, parameter name, and parameter value.
     *
     * @param basePath           the base path of the REST endpoint
     * @param route              the route of the request
     * @param body               the request body
     * @param pathParameterName  the name of the parameter
     * @param pathParameterValue the value of the parameter
     * @return the response from the delete request
     */
    public Response deleteRequest(RestEndpointEnum basePath, String route, Object body, String pathParameterName, Object pathParameterValue) {
        this.response = rest.deleteRequest(basePath, route, body, pathParameterName, pathParameterValue);
        return response;
    }
    
    /**
     * Sends a DELETE request to the specified base path, route, body, parameter name, and parameter value.
     *
     * @param basePath   the base path of the REST endpoint
     * @param route      the route of the request
     * @param body       the body of the request
     * @param pathParams the path parameters of the request
     * @return the response of the request
     */
    public Response deleteRequest(RestEndpointEnum basePath, String route, Object body, Map<String, Object> pathParams) {
        this.response = rest.deleteRequest(basePath, route, body, pathParams);
        return response;
    }
    
    /**
     * Sends a POST request to the specified route with the given parameters and returns a Response object.
     *
     * @param requestSpec the RequestSpecification object for the HTTP request
     * @param basePath    the base path for the REST endpoint
     * @param route       the route for the HTTP request
     * @param body        the body of the HTTP request
     * @param pathParams  the path parameters for the HTTP request
     * @param queryParams the query parameters for the HTTP request
     * @param file        the file to be sent with the HTTP request
     * @return the response of the HTTP request
     */
    public Response postRequest(RequestSpecification requestSpec, RestEndpointEnum basePath, String route, Object body, Map<String, Object> pathParams, Map<String, Object> queryParams, File file) {
        this.response = rest.postRequest(requestSpec, basePath, route, body, pathParams, queryParams, file);
        return response;
    }
    
    /**
     * Sends a GET request to the specified route with the given parameters and returns a Response object.
     *
     * @param requestSpec the request specification for the API call
     * @param basePath    the base path of the REST endpoint
     * @param route       the route of the API call
     * @param body        the body of the API call
     * @param pathParams  the path parameters of the API call
     * @param queryParams the query parameters of the API call
     * @param file        the file to be included in the API call
     * @return the response of the API call
     */
    public Response getRequest(RequestSpecification requestSpec, RestEndpointEnum basePath, String route, Object body, Map<String, Object> pathParams, Map<String, Object> queryParams, File file) {
        this.response = rest.getRequest(requestSpec, basePath, route, body, pathParams, queryParams, file);
        return response;
    }
    
    /**
     * Sends a DELETE request to the specified route with the given parameters and returns a Response object.
     *
     * @param requestSpec the request specification object
     * @param basePath    the base path of the REST endpoint
     * @param route       the route of the resource
     * @param body        the request body
     * @param pathParams  the path parameters
     * @param queryParams the query parameters
     * @param file        the file to be sent with the request
     * @return the response received from the server
     */
    public Response deleteRequest(RequestSpecification requestSpec, RestEndpointEnum basePath, String route, Object body, Map<String, Object> pathParams, Map<String, Object> queryParams, File file) {
        this.response = rest.deleteRequest(requestSpec, basePath, route, body, pathParams, queryParams, file);
        return response;
    }
    
    /**
     * Sends a PUT request to the specified route with the provided request specification, base path, body, path parameters, query parameters, and file.
     *
     * @param requestSpec the request specification object
     * @param basePath    the base path of the REST endpoint
     * @param route       the route for the request
     * @param body        the request body object
     * @param pathParams  the path parameters for the request
     * @param queryParams the query parameters for the request
     * @param file        the file to be uploaded with the request
     * @return the response object
     */
    public Response putRequest(RequestSpecification requestSpec, RestEndpointEnum basePath, String route, Object body, Map<String, Object> pathParams, Map<String, Object> queryParams, File file) {
        this.response = rest.putRequest(requestSpec, basePath, route, body, pathParams, queryParams, file);
        return response;
    }
}
