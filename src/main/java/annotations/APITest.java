package annotations;

import domain.RestEndpointEnum;
import domain.SoapActionEnum;
import domain.SoapBasePathEnum;
import io.restassured.http.Method;
import utils.enums.RequestType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface APITest {
    
    RestEndpointEnum restEndpoint();
    
    SoapBasePathEnum soapEndpoint();
    
    SoapActionEnum soapAction();
    
    String route() default "";
    
    Method method();
    
    Class<?> payload() default Object.class;
    
    String[] pathParams() default {};
    
    String[] queryParams() default {};
    
    String filePath() default "";
    
    RequestType requestType() default RequestType.REST;
}
