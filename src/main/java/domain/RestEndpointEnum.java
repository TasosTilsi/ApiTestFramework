package domain;


import lombok.Getter;
import utils.config.EnvDataConfig;

public enum RestEndpointEnum {
    API("/api"),
    RESOURCE(API.getPath() + "/resource"),
    USERS(API.getPath() + "/api/users/"),
    LOGIN(API.getPath() + "/api/login"),
    REGISTER(API.getPath() + "/api/register"),
    LOGOUT(API.getPath() + "/api/logout");
    
    @Getter
    private final String path;
    EnvDataConfig envDataConfig;
    
    RestEndpointEnum(String path) {
        this.path = path;
        envDataConfig = new EnvDataConfig();
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
