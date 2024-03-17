package utils.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

@UtilityClass
public class SerializationUtil {
    
    @SneakyThrows
    public String serialize(Object object, ObjectMapper objectMapper) {
        return objectMapper.writeValueAsString(object);
    }
}
