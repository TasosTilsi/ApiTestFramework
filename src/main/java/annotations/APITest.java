package annotations;

import domain.RestEndpointEnum;
import domain.SoapActionEnum;
import domain.SoapBasePathEnum;
import io.restassured.http.Method;
import utils.enums.RequestType;
import utils.helpers.NoPayload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface APITest {
    
    RestEndpointEnum restEndpoint() default RestEndpointEnum.API;
    
    SoapBasePathEnum soapEndpoint() default SoapBasePathEnum.SOAP_SERVICE;
    
    SoapActionEnum soapAction() default SoapActionEnum.CREATE_USER_SERVICE;
    
    String route() default "";
    
    Method method() default Method.GET;
    
    Class<?> payload() default NoPayload.class;
    
    String[] pathParams() default {};
    
    String[] queryParams() default {};
    
    String filePath() default "";
    
    RequestType requestType() default RequestType.REST;
}
