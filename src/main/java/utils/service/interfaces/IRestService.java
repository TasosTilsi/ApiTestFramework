package utils.service.interfaces;

import domain.RestEndpointEnum;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utils.factories.RestServiceObjectFactory;

import java.io.File;
import java.util.Map;

/**
 * This interface represents a REST service and extends the BaseService interface.
 * It provides methods for creating service objects, making Jira requests, and
 * making POST requests.
 */
public interface IRestService extends IBaseService {
    
    /**
     * Returns an instance of the RestServiceObjectFactory class.
     * This factory is used to create objects related to the REST service.
     *
     * @return an instance of RestServiceObjectFactory
     */
    RestServiceObjectFactory service();
    
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
    Response postRequest(RequestSpecification requestSpec, RestEndpointEnum basePath, String route, Object body, Map<String, Object> pathParams, Map<String, Object> queryParams, File file);
    
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
    Response getRequest(RequestSpecification requestSpec, RestEndpointEnum basePath, String route, Object body, Map<String, Object> pathParams, Map<String, Object> queryParams, File file);
    
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
    Response deleteRequest(RequestSpecification requestSpec, RestEndpointEnum basePath, String route, Object body, Map<String, Object> pathParams, Map<String, Object> queryParams, File file);
    
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
    Response putRequest(RequestSpecification requestSpec, RestEndpointEnum basePath, String route, Object body, Map<String, Object> pathParams, Map<String, Object> queryParams, File file);
    
    /**
     * Sends a PATCH request to the specified endpoint with the given parameters.
     *
     * @param requestSpec the RequestSpecification object
     * @param basePath    the base path for the REST endpoint
     * @param route       the route for the REST endpoint
     * @param body        the request body object
     * @param pathParams  the path parameters for the REST endpoint
     * @param queryParams the query parameters for the REST endpoint
     * @param file        the file object
     * @return the Response object
     */
    Response patchRequest(RequestSpecification requestSpec, RestEndpointEnum basePath, String route, Object body, Map<String, Object> pathParams, Map<String, Object> queryParams, File file);
    
    /**
     * Creates a request specification with the given parameters.
     *
     * @param requestSpec the request specification object
     * @param basePath    the base path of the REST endpoint
     * @param route       the route for the request
     * @param body        the request body object
     * @param pathParams  the map of path parameters
     * @param queryParams the map of query parameters
     * @param file        the file object
     * @return the request specification object
     */
    RequestSpecification createRequest(RequestSpecification requestSpec, RestEndpointEnum basePath, String route, Object body, Map<String, Object> pathParams, Map<String, Object> queryParams, File file);
    
    /**
     * Sends a request using the provided request specification, HTTP method, and route.
     *
     * @param request the request specification object
     * @param method  the HTTP method used for the request
     * @param route   the route of the API endpoint
     * @return the response received from the server
     */
    Response sendRequest(RequestSpecification request, Method method, String route);
}
