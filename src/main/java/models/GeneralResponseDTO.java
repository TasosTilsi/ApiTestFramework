package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GeneralResponseDTO {
    @JsonProperty("page")
    private int page;
    
    @JsonProperty("per_page")
    private int per_page;
    
    @JsonProperty("total")
    private int total;
    
    @JsonProperty("total_pages")
    private int total_pages;
    
    @JsonProperty("support")
    private SupportDTO support;
}
