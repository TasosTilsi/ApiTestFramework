package utils.config;

import java.util.Properties;

public class SecretsConfig extends EnvDataConfig {
    
    public SecretsConfig() {
        // TODO document why this constructor is empty
    }
    
    public String getUsername() {
        return getSecretProperties().getProperty("username");
    }
    
    public String getPassword() {
        return getSecretProperties().getProperty("password");
    }
    
    public String getToken() {
        return getSecretProperties().getProperty("token");
    }
    
    
    private Properties getSecretProperties() {
        return getProperties(loadProperties(resourcesConfig.getSecretProperties()));
    }
}
