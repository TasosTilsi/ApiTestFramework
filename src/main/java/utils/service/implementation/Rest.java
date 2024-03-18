package utils.service.implementation;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.OffsetDateTimeSerializer;
import domain.RestEndpointEnum;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import models.ContextData;
import org.slf4j.Logger;
import utils.common.SerializationUtil;
import utils.config.SecretsConfig;
import utils.enums.Environment;
import utils.factories.RestServiceObjectFactory;
import utils.service.interfaces.IRestService;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.util.Map;

import static io.restassured.http.ContentType.JSON;
import static io.restassured.http.ContentType.MULTIPART;

/**
 * This class represents a REST service and implements the RestService interface.
 * It provides methods for creating service objects, accessing the logger, making Jira requests,
 * and making POST requests.
 */
public class Rest implements IRestService {
    
    public static final String REST_CONTENT_TYPE = "application/json";
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(Rest.class);
    private final RestServiceObjectFactory restServiceObjectFactory;
    private final ContextData contextData;
    protected SecretsConfig secretsConfig = new SecretsConfig();
    private ObjectMapper mapper = new ObjectMapper();
    private RequestSpecification requestSpec;
    private ResponseSpecification responseSpec;
    
    /**
     * Constructs a new instance of the Rest class.
     * Initializes the restServiceObjectFactory, jiraRequests, logger, and contextFactory.
     */
    public Rest() {
        this.contextData = new ContextData();
        this.restServiceObjectFactory = new RestServiceObjectFactory(this);
        this.requestSpec = getRequestSpec();
        this.responseSpec = getResponseSpec();
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ"));
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.registerModule(new JavaTimeModule());
        mapper.registerModule(new SimpleModule().addSerializer(OffsetDateTime.class, OffsetDateTimeSerializer.INSTANCE));
    }
    
    /**
     * Returns an instance of the RestServiceObjectFactory class.
     * This factory is used to create objects related to the REST service.
     *
     * @return an instance of RestServiceObjectFactory
     */
    @Override
    public RestServiceObjectFactory service() {
        return this.restServiceObjectFactory;
    }
    
    /**
     * Returns the context factory for this REST service.
     *
     * @return the context factory
     */
    @Override
    public ContextData context() {
        return this.contextData;
    }
    
    /**
     * Sends a POST request with a body.
     *
     * @param basePath the base path of the endpoint.
     * @param route    the route of the endpoint.
     * @param body     the request body.
     * @return the response of the request.
     */
    public Response postRequest(RestEndpointEnum basePath, String route, Object body) {
        return postRequest(requestSpec, basePath, route, body, null, null, null);
    }
    
    /**
     * Sends a POST request with path parameters.
     *
     * @param basePath   the base path of the endpoint.
     * @param route      the route of the endpoint.
     * @param body       the request body.
     * @param pathParams the path parameters of the endpoint.
     * @return the response of the request.
     */
    public Response postRequest(RestEndpointEnum basePath, String route, Object body, Map<String, Object> pathParams) {
        return postRequest(requestSpec, basePath, route, body, pathParams, null, null);
    }
    
    /**
     * Sends a POST request with a single path parameter.
     *
     * @param basePath           the base path of the endpoint.
     * @param route              the route of the endpoint.
     * @param body               the request body.
     * @param pathParameterName  the name of the path parameter.
     * @param pathParameterValue the value of the path parameter.
     * @return the response of the request.
     */
    public Response postRequest(RestEndpointEnum basePath, String route, Object body, String pathParameterName, Object pathParameterValue) {
        Map<String, Object> pathParams = Map.of(pathParameterName, pathParameterValue);
        return postRequest(requestSpec, basePath, route, body, pathParams, null, null);
    }
    
    /**
     * Sends a POST multipart request.
     *
     * @param basePath the base path of the endpoint.
     * @param route    the route of the endpoint.
     * @param body     the request body.
     * @param file     the file to send in the request.
     * @return the response of the request.
     */
    public Response postRequest(RestEndpointEnum basePath, String route, Object body, File file) {
        return postRequest(requestSpec, basePath, route, body, null, null, file);
    }
    
    /**
     * Sends a POST multipart request with path parameters.
     *
     * @param basePath   the base path of the endpoint.
     * @param route      the route of the endpoint.
     * @param body       the request body.
     * @param pathParams the path parameters of the endpoint.
     * @param file       the file to send in the request.
     * @return the response of the request.
     */
    public Response postRequest(RestEndpointEnum basePath, String route, Object body, Map<String, Object> pathParams, File file) {
        return postRequest(requestSpec, basePath, route, body, pathParams, null, file);
    }
    
    /**
     * Sends a POST multipart request with a single path parameter.
     *
     * @param basePath           the base path of the endpoint.
     * @param route              the route of the endpoint.
     * @param body               the request body.
     * @param pathParameterName  the name of the path parameter.
     * @param pathParameterValue the value of the path parameter.
     * @param file               the file to send in the request.
     * @return the response of the request.
     */
    public Response postRequest(RestEndpointEnum basePath, String route, Object body, String pathParameterName, Object pathParameterValue, File file) {
        Map<String, Object> pathParams = Map.of(pathParameterName, pathParameterValue);
        return postRequest(requestSpec, basePath, route, body, pathParams, null, file);
    }
    
    /**
     * Sends a POST multipart request with query parameters.
     *
     * @param basePath    the base path of the endpoint.
     * @param route       the route of the endpoint.
     * @param body        the request body.
     * @param queryParams the query parameters of the endpoint.
     * @param file        the file to send in the request.
     * @return the response of the request.
     */
    public Response postRequest(RestEndpointEnum basePath, String route, Object body, File file, Map<String, Object> queryParams) {
        return postRequest(requestSpec, basePath, route, body, null, queryParams, file);
    }
    
    /**
     * Sends a POST multipart request with a single query parameter.
     *
     * @param basePath            the base path of the endpoint.
     * @param route               the route of the endpoint.
     * @param body                the request body.
     * @param queryParameterName  the name of the query parameter.
     * @param queryParameterValue the value of the query parameter.
     * @param file                the file to send in the request.
     * @return the response of the request.
     */
    public Response postRequest(RestEndpointEnum basePath, String route, Object body, File file, String queryParameterName, Object queryParameterValue) {
        Map<String, Object> queryParams = Map.of(queryParameterName, queryParameterValue);
        return postRequest(requestSpec, basePath, route, body, null, queryParams, file);
    }
    
    /**
     * Sends a GET request.
     *
     * @param basePath the base path of the endpoint.
     * @param route    the route of the endpoint.
     * @return the response of the request.
     */
    public Response getRequest(RestEndpointEnum basePath, String route) {
        return getRequest(basePath, route, JSON);
    }
    
    /**
     * Generates a GET request to the specified REST endpoint.
     *
     * @param basePath    the base path of the REST endpoint
     * @param route       the route of the REST endpoint
     * @param contentType the content type of the request
     * @return the response from the GET request
     */
    public Response getRequest(RestEndpointEnum basePath, String route, ContentType contentType) {
        return getRequest(getRequestSpec(contentType), basePath, route, null, null, null, null);
    }
    
    /**
     * Sends a GET request with query parameters.
     *
     * @param basePath    the base path of the endpoint.
     * @param route       the route of the endpoint.
     * @param queryParams the query parameters of the endpoint.
     * @return the response of the request.
     */
    public Response getRequestWithQueryParams(RestEndpointEnum basePath, String route, Map<String, Object> queryParams) {
        return getRequest(requestSpec, basePath, route, null, null, queryParams, null);
    }
    
    /**
     * Generates a GET request URL with query parameters.
     *
     * @param basePath       the base path of the REST endpoint
     * @param route          the route of the request
     * @param parameterName  the name of the query parameter
     * @param parameterValue the value of the query parameter
     * @return the response object
     */
    public Response getRequestWithQueryParams(RestEndpointEnum basePath, String route, String parameterName, Object parameterValue) {
        Map<String, Object> queryParams = Map.of(parameterName, parameterValue);
        return getRequestWithQueryParams(basePath, route, queryParams);
    }
    
    /**
     * Sends a GET request with path parameters.
     *
     * @param basePath   the base path of the endpoint.
     * @param route      the route of the endpoint.
     * @param pathParams the path parameters of the endpoint.
     * @return the response of the request.
     */
    public Response getRequestWithPathParams(RestEndpointEnum basePath, String route, Map<String, Object> pathParams) {
        return getRequestWithPathParams(basePath, route, pathParams, JSON);
    }
    
    /**
     * Generates a GET request with path parameters.
     *
     * @param basePath    the base path of the REST endpoint
     * @param route       the route of the request
     * @param pathParams  the map of path parameters
     * @param contentType the content type of the request
     * @return the response of the GET request
     */
    public Response getRequestWithPathParams(RestEndpointEnum basePath, String route, Map<String, Object> pathParams, ContentType contentType) {
        return getRequest(getRequestSpec(contentType), basePath, route, null, pathParams, null, null);
    }
    
    /**
     * Sends a GET request with a single path parameter.
     *
     * @param basePath       the base path of the endpoint.
     * @param route          the route of the endpoint.
     * @param parameterName  the name of the path parameter.
     * @param parameterValue the value of the path parameter.
     * @return the response of the request.
     */
    public Response getRequestWithPathParams(RestEndpointEnum basePath, String route, String parameterName, Object parameterValue) {
        Map<String, Object> pathParams = Map.of(parameterName, parameterValue);
        return getRequestWithPathParams(basePath, route, pathParams, JSON);
    }
    
    /**
     * Sends a PUT request with path parameters.
     *
     * @param basePath   the base path of the endpoint.
     * @param route      the route of the endpoint.
     * @param body       the request body.
     * @param pathParams the path parameters of the endpoint.
     * @return the response of the request.
     */
    public Response putRequest(RestEndpointEnum basePath, String route, Object body, Map<String, Object> pathParams) {
        return putRequest(requestSpec, basePath, route, body, pathParams, null, null);
    }
    
    /**
     * Sends a PUT request with a single path parameter.
     *
     * @param basePath           the base path of the endpoint.
     * @param route              the route of the endpoint.
     * @param body               the request body.
     * @param pathParameterName  the name of the path parameter.
     * @param pathParameterValue the value of the path parameter.
     * @return the response of the request.
     */
    public Response putRequest(RestEndpointEnum basePath, String route, Object body, String pathParameterName, Object pathParameterValue) {
        Map<String, Object> pathParams = Map.of(pathParameterName, pathParameterValue);
        return putRequest(basePath, route, body, pathParams);
    }
    
    /**
     * Generates the function comment for the given function body.
     *
     * @param basePath   the base path of the REST endpoint
     * @param route      the route of the REST endpoint
     * @param pathParams the path parameters of the REST endpoint
     * @return the Response object returned by the REST endpoint
     */
    public Response putRequest(RestEndpointEnum basePath, String route, Map<String, Object> pathParams) {
        return putRequest(requestSpec, basePath, route, null, pathParams, null, null);
    }
    
    /**
     * Generates a PUT request to the specified REST endpoint with the given route and path parameter.
     *
     * @param basePath           the base path of the REST endpoint
     * @param route              the route of the request
     * @param pathParameterName  the name of the path parameter
     * @param pathParameterValue the value of the path parameter
     * @return the response from the PUT request
     */
    public Response putRequest(RestEndpointEnum basePath, String route, String pathParameterName, Object pathParameterValue) {
        Map<String, Object> pathParams = Map.of(pathParameterName, pathParameterValue);
        return putRequest(requestSpec, basePath, route, null, pathParams, null, null);
    }
    
    /**
     * Sends a PATCH request (not implemented).
     *
     * @return null.
     */
    public Response patchRequest() {
        throw new IllegalCallerException("Method not implemented yet.");
    }
    
    /**
     * Deletes a request using the specified base path, route, and path parameters.
     *
     * @param basePath   the base path of the REST endpoint
     * @param route      the route of the request
     * @param pathParams the path parameters of the request
     * @return the response of the request
     */
    public Response deleteRequest(RestEndpointEnum basePath, String route, Map<String, Object> pathParams) {
        return deleteRequest(requestSpec, basePath, route, null, pathParams, null, null);
    }
    
    /**
     * Deletes a request using the specified base path, route, path parameter name, and path parameter value.
     *
     * @param basePath           the base path of the REST endpoint
     * @param route              the route of the request
     * @param pathParameterName  the name of the path parameter
     * @param pathParameterValue the value of the path parameter
     * @return the response of the delete request
     */
    public Response deleteRequest(RestEndpointEnum basePath, String route, String pathParameterName, Object pathParameterValue) {
        Map<String, Object> pathParams = Map.of(pathParameterName, pathParameterValue);
        return deleteRequest(basePath, route, pathParams);
    }
    
    /**
     * Deletes a resource using a DELETE request.
     *
     * @param basePath   the base path of the REST endpoint
     * @param route      the route of the resource to delete
     * @param body       the body of the request
     * @param pathParams the path parameters of the request
     * @return the response received from the server
     */
    public Response deleteRequest(RestEndpointEnum basePath, String route, Object body, Map<String, Object> pathParams) {
        return deleteRequest(requestSpec, basePath, route, body, pathParams, null, null);
    }
    
    /**
     * Deletes a request based on the given parameters.
     *
     * @param basePath           the base path of the REST endpoint
     * @param route              the route of the request
     * @param body               the body of the request
     * @param pathParameterName  the name of the path parameter
     * @param pathParameterValue the value of the path parameter
     * @return the response of the delete request
     */
    public Response deleteRequest(RestEndpointEnum basePath, String route, Object body, String pathParameterName, Object pathParameterValue) {
        Map<String, Object> pathParams = Map.of(pathParameterName, pathParameterValue);
        return deleteRequest(basePath, route, body, pathParams);
    }
    
    /**
     * Retrieves the request specification with the specified content type.
     *
     * @param contentType the content type to set for the request specification
     * @return the request specification with the specified content type
     */
    public RequestSpecification getRequestSpec(ContentType contentType) {
        return getRequestSpec().contentType(contentType);
    }
    
    /**
     * Gets the request specification to use in the requests, with a specific country code.
     *
     * @return the request specification.
     */
    public RequestSpecification getRequestSpec() {
        /*
         * Uncomment code and use it if needed, not all applications need a token
         * or the headers are specific to workview application
         * nice to have is the allure config to blacklist whatever header you want to not be visible
         *
         * */
        //String accessToken = new Authenticator().generateToken();
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder()
                .setRelaxedHTTPSValidation()
                .setBaseUri(secretsConfig.getRestApiUrl())
//                .addHeader("Authorization", "Bearer " + accessToken)
//                .addHeader("apiVersion", secretsProviderConfig.getApiVersion())
                .setContentType(JSON)
//                .setConfig(RestAssuredConfig.config().logConfig(LogConfig.logConfig().blacklistHeader("Authorization").and().enablePrettyPrinting(true)))
                .addFilter(new AllureRestAssured())
                .log(LogDetail.METHOD)
                .log(LogDetail.URI)
                .log(LogDetail.HEADERS)
                .log(LogDetail.PARAMS)
                .log(LogDetail.BODY);
        
        if (secretsConfig.getEnvironment() == Environment.DEV) {
            requestSpecBuilder.setBaseUri(secretsConfig.getRestBaseUri());
//            requestSpecBuilder.setBasePath(secretsConfig.getApiPath());
        }
        return requestSpecBuilder.build();
    }
    
    /**
     * Gets the response specification to use in the requests.
     *
     * @return the response specification.
     */
    public ResponseSpecification getResponseSpec() {
        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();
        return RestAssured.responseSpecification = responseSpecBuilder.build();
    }
    
    /**
     * Sends a POST request to the specified route with the given parameters and returns a Response object.
     *
     * @param requestSpec the request specification
     * @param basePath    the base path of the REST endpoint
     * @param route       the route of the request
     * @param body        the request body
     * @param pathParams  the path parameters
     * @param queryParams the query parameters
     * @param file        the file to be sent with the request
     * @return the response of the request
     */
    @Override
    public Response postRequest(RequestSpecification requestSpec, RestEndpointEnum basePath, String route, Object body, Map<String, Object> pathParams, Map<String, Object> queryParams, File file) {
        return sendRequest(
                createRequest(requestSpec,
                        basePath,
                        route,
                        body,
                        pathParams,
                        queryParams,
                        file),
                Method.POST,
                route);
    }
    
    /**
     * Sends a GET request to the specified route with the given parameters and returns a Response object.
     *
     * @param requestSpec the RequestSpecification object used to configure the request
     * @param basePath    the base path of the REST endpoint
     * @param route       the route of the request
     * @param body        the body of the request
     * @param pathParams  a map containing path parameters
     * @param queryParams a map containing query parameters
     * @param file        a File object representing a file to be uploaded with the request
     * @return a Response object containing the response from the server
     */
    @Override
    public Response getRequest(RequestSpecification requestSpec, RestEndpointEnum basePath, String route, Object body, Map<String, Object> pathParams, Map<String, Object> queryParams, File file) {
        return sendRequest(
                createRequest(requestSpec,
                        basePath,
                        route,
                        body,
                        pathParams,
                        queryParams,
                        file),
                Method.GET,
                route);
    }
    
    /**
     * Sends a DELETE request to the specified route with the given parameters and returns a Response object.
     *
     * @param requestSpec the request specification
     * @param basePath    the base path of the REST endpoint
     * @param route       the route of the request
     * @param body        the request body
     * @param pathParams  the path parameters
     * @param queryParams the query parameters
     * @param file        the file to upload
     * @return the response of the request
     */
    @Override
    public Response deleteRequest(RequestSpecification requestSpec, RestEndpointEnum basePath, String route, Object body, Map<String, Object> pathParams, Map<String, Object> queryParams, File file) {
        return sendRequest(
                createRequest(requestSpec,
                        basePath,
                        route,
                        body,
                        pathParams,
                        queryParams,
                        file),
                Method.DELETE,
                route);
    }
    
    /**
     * Sends a PUT request to the specified route with the provided request specification, base path, body, path parameters, query parameters, and file.
     *
     * @param requestSpec the request specification
     * @param basePath    the base path
     * @param route       the route
     * @param body        the body
     * @param pathParams  the path parameters
     * @param queryParams the query parameters
     * @param file        the file
     * @return the response
     */
    @Override
    public Response putRequest(RequestSpecification requestSpec, RestEndpointEnum basePath, String route, Object body, Map<String, Object> pathParams, Map<String, Object> queryParams, File file) {
        return sendRequest(
                createRequest(requestSpec,
                        basePath,
                        route,
                        body,
                        pathParams,
                        queryParams,
                        file),
                Method.PUT,
                route);
    }
    
    /**
     * Sends a PATCH request to the specified endpoint with the given parameters.
     *
     * @param requestSpec the request specification object
     * @param basePath    the base path of the endpoint
     * @param route       the route of the endpoint
     * @param body        the request body
     * @param pathParams  the path parameters
     * @param queryParams the query parameters
     * @param file        the file to send with the request
     * @return the response received from the server
     */
    @Override
    public Response patchRequest(RequestSpecification requestSpec, RestEndpointEnum basePath, String route, Object body, Map<String, Object> pathParams, Map<String, Object> queryParams, File file) {
        return sendRequest(
                createRequest(requestSpec,
                        basePath,
                        route,
                        body,
                        pathParams,
                        queryParams,
                        file),
                Method.PATCH,
                route);
    }
    
    /**
     * Creates a request specification with the given parameters.
     *
     * @param requestSpec the request specification to be used as a base
     * @param basePath    the base path of the REST endpoint
     * @param route       the route of the request
     * @param body        the request body
     * @param pathParams  the path parameters
     * @param queryParams the query parameters
     * @param file        the file to be uploaded
     * @return the created request specification
     */
    @Override
    public RequestSpecification createRequest(RequestSpecification requestSpec, RestEndpointEnum basePath, String route, Object body, Map<String, Object> pathParams, Map<String, Object> queryParams, File file) {
        RequestSpecification request = RestAssured.given(requestSpec)
                .basePath(basePath.getPath());
        if (body != null) {
            request.body(body);
        }
        if (pathParams != null && !pathParams.isEmpty()) {
            request.pathParams(pathParams);
        }
        if (queryParams != null && !queryParams.isEmpty()) {
            request.queryParams(queryParams);
        }
        if (file != null && file.exists()) {
            request.contentType(MULTIPART);
            request.multiPart("file", file);
            if (body != null) {
                request.multiPart("payload", SerializationUtil.serialize(body, mapper), REST_CONTENT_TYPE);
            }
        }
        return request;
    }
    
    /**
     * Sends a request using the provided request specification, HTTP method, and route.
     *
     * @param request the request specification
     * @param method  the HTTP method
     * @param route   the route to send the request to
     * @return the response received from the server
     */
    @Override
    public Response sendRequest(RequestSpecification request, Method method, String route) {
        return getResponse(request, method, route);
    }
    
    /**
     * Retrieves the response for a given request.
     *
     * @param request the request specification
     * @param method  the HTTP method of the request
     * @param route   the route of the request
     * @return the response object
     */
    private Response getResponse(RequestSpecification request, Method method, String route) {
        Response response;
        response = RestAssured.given(request)
                .when()
                .request(method, route)
                .then()
                .spec(responseSpec)
                .log()
                .status()
                .and()
                .log()
                .body()
                .extract()
                .response();
        this.contextData.setLastResponse(response);
        return response;
    }
}
