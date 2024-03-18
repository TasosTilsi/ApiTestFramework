package services.rest.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UnknownResourceDataResponseDTO {
    @JsonProperty("id")
    private int id;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("year")
    private int year;
    
    @JsonProperty("color")
    private String color;
    
    @JsonProperty("pantone_value")
    private String pantone_value;
}
