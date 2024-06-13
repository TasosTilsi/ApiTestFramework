package models;

import io.restassured.response.Response;
import lombok.Data;

@Data
public class ContextData {
    private String stepDescription;
    private Response lastResponse;
    private String id;
    private String testName;
    private Long responseTime;
}
