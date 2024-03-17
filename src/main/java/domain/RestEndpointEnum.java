package domain;


import utils.config.EnvDataConfig;

public enum RestEndpointEnum {
    
    // APIs
    BASE_PATH("/"),
    AUTHORS_PATH("/api/v1/Authors/"),
    ACTIVITIES_PATH("/api/v1/Activities"),
    PATH_READ_AUTHOR_BY_ID("/api/v1/Authors/{author_id}");
    
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
    
    
    public String getPath() {
        return path;
    }
}
