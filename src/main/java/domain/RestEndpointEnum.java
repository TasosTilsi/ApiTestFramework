package domain;


import domain.interfaces.IEndpoint;
import lombok.Getter;

public enum RestEndpointEnum implements IEndpoint {
    API("/api"),
    RESOURCE(API.getPath() + "/resource"),
    USERS(API.getPath() + "/api/users/"),
    LOGIN(API.getPath() + "/api/login"),
    REGISTER(API.getPath() + "/api/register"),
    LOGOUT(API.getPath() + "/api/logout");
    
    @Getter
    private final String path;
    
    RestEndpointEnum(String path) {
        this.path = path;
    }
    
    public static RestEndpointEnum get(String path) {
        for (RestEndpointEnum restEndpointEnum : values()) {
            if (restEndpointEnum.path.equals(path)) {
                return restEndpointEnum;
            }
        }
        return null;
    }
    
    @Override
    public String toString() {
        return path;
    }
    
}
