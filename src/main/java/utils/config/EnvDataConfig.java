package utils.config;

import utils.enums.Environment;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.Properties;

public class EnvDataConfig {
    
    protected ResourcesConfig resourcesConfig;
    
    public EnvDataConfig() {
        resourcesConfig = new ResourcesConfig();
    }
    
    protected static Properties loadProperties(String testDataFile) {
        Properties prop = new Properties();
        try {
            InputStream inputStream = new FileInputStream(testDataFile);
            Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            prop.load(reader);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return prop;
    }
    
    protected static Properties getProperties(Properties params) {
        Properties result = new Properties();
        Enumeration<?> names = params.propertyNames();
        while (names.hasMoreElements()) {
            String name = (String) names.nextElement();
            result.put(name, params.get(name));
        }
        return result;
    }
    
    private static String removeTrailingSlash(String url) {
        if (url.endsWith("/")) {
            url = url.substring(0, url.length() - 1);
        }
        return url;
    }
    
    public int getRetry() {
        return Integer.parseInt(getEnvProperties().getProperty("retry"));
    }
    
    public String getURL(String applicationName) {
        return removeTrailingSlash(getEnvProperties().getProperty(applicationName + ".url"));
    }
    
    public String getRestApiUrl() {
        return removeTrailingSlash(getEnvProperties().getProperty("rest.url"));
    }
    
    public String getIdentityUrl() {
        String identityUrl = getEnvProperties().getProperty("rest.identity.url");
        if (identityUrl != null) {
            return removeTrailingSlash(identityUrl);
        }
        return getRestApiUrl();
    }
    
    public String getRestBaseUri() {
        String baseUri = getEnvProperties().getProperty("rest.baseUri");
        if (baseUri == null) {
            return getRestApiUrl();
        }
        return removeTrailingSlash(baseUri);
    }
    
    public String getSoapApiUrl() {
        return removeTrailingSlash(getEnvProperties().getProperty("soap.url"));
    }
    
    /**
     * Retrieves the environment by calling the method `getEnvironmentIdFromSelectedProfile()`
     * and passing the returned environment ID to the method `EnvironmentProfiles.getEnvById()`.
     *
     * @return an instance of the `Environment` class representing the retrieved environment.
     */
    public Environment getEnvironment() {
        return Environment.valueOf(getEnvironmentIdFromSelectedProfile().toUpperCase());
    }
    
    /**
     * Retrieves the environment ID from the selected profile.
     *
     * @return The environment ID extracted from the environment properties path.
     */
    protected String getEnvironmentIdFromSelectedProfile() {
        return extractProfileFromEnvPropertiesPath(resourcesConfig.getEnvironmentProperties());
    }
    
    private String extractProfileFromEnvPropertiesPath(String envPropertiesPath) {
        String config = "config/";
        String extracted = envPropertiesPath.substring(envPropertiesPath.indexOf(config) + config.length(), envPropertiesPath.lastIndexOf("."));
        extracted = extracted.replace("/", "-");
        return extracted;
    }
    
    /**
     * This function retrieves the environment properties by loading the environment
     * properties file and converting it into a Properties object.
     *
     * @return The Properties object containing the environment properties.
     */
    private Properties getEnvProperties() {
        return getProperties(loadProperties(resourcesConfig.getEnvironmentProperties()));
    }
}
