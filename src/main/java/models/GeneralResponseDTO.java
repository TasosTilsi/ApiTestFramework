package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GeneralResponseDTO {
    @JsonProperty("page")
    private int page;
    
    @JsonProperty("per_page")
    private int perPage;
    
    @JsonProperty("total")
    private int total;
    
    @JsonProperty("total_pages")
    private int totalPages;
    
    @JsonProperty("support")
    private SupportDTO support;
}
