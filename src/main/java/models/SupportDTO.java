package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SupportDTO {
    @JsonProperty("url")
    private String url;
    
    @JsonProperty("text")
    private String text;
}
