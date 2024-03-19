package domain;


import domain.interfaces.IEndpoint;
import utils.config.EnvDataConfig;

public enum AuthEndpoint implements IEndpoint {
    
    // APIs
    AUTH_PATH("/somePath"),
    DEV_ENV_AUTH_PATH("/oauth/token");
    private final String path;
    private final EnvDataConfig envDataConfig;
    
    AuthEndpoint(String path) {
        this.path = path;
        envDataConfig = new EnvDataConfig();
    }
    
    public static AuthEndpoint get(String path) {
        for (AuthEndpoint restEndpointEnum : values()) {
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
    
    
    public String getPath() {
        return envDataConfig.getRestApiUrl() + path;
    }
}
