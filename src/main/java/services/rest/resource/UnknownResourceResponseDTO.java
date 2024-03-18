package services.rest.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;
import models.GeneralResponseDTO;

import java.util.List;

@Data
public class UnknownResourceResponseDTO {
    
    @JsonUnwrapped
    private GeneralResponseDTO generalResponseDTO;
    
    @JsonProperty("data")
    private List<UnknownResourceDataResponseDTO> data;
}
