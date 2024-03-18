package services.rest.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;
import models.GeneralResponseDTO;

import java.util.List;

@Data
public class UserResponseDTO {
    
    @JsonUnwrapped
    private GeneralResponseDTO generalResponseDTO;
    
    @JsonProperty("data")
    private List<UserDTO> data;
}
