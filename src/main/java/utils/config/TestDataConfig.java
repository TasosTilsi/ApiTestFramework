package utils.config;


import lombok.Getter;
import org.testng.Assert;
import org.testng.util.Strings;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public final class TestDataConfig extends EnvDataConfig {
    
    @Getter
    private final String propertyFile;
    @Getter
    private Properties testData = new Properties();
    
    public TestDataConfig(String testSuiteName, String testCaseName) {
        propertyFile = initializePropertyFile(testSuiteName);
        testData = getTestData(testCaseName);
    }
    
    public TestDataConfig(String testSuiteName, String testCaseName, int index) {
        propertyFile = initializePropertyFile(testSuiteName);
        testData = getTestData(testCaseName + "." + index);
    }
    
    /**
     * This method returns the value of a String parameter from properties file
     *
     * @param parameterName the name of the parameter as appears in the properties file [String]
     * @return the parameter value as String
     */
    public String getStringParameter(String parameterName) {
        if (!parameterExists(parameterName)) {
            Assert.fail("Parameter '" + parameterName + "' does not exist in Test Data!");
        }
        return testData.getProperty(parameterName);
    }
    
    /**
     * This method returns the value of a String parameter if it exists in the properties file, otherwise Null
     *
     * @param parameterName the name of the parameter as appears in the properties file [String]
     * @return the parameter value as String
     */
    public String getStringParameterIfExists(String parameterName) {
        return getStringParameterIfExists(parameterName, null);
    }
    
    /**
     * Returns the value of a specific parameter from the given properties object if it exists,
     * otherwise returns the provided default value.
     *
     * @param parameterName the name of the parameter to retrieve
     * @param defaultValue  the default value to return if the parameter does not exist
     * @return the value of the parameter if it exists, otherwise the default value
     */
    public String getStringParameterIfExists(String parameterName, String defaultValue) {
        return parameterExists(parameterName) ? testData.getProperty(parameterName) : defaultValue;
    }
    
    /**
     * This method returns the value of a Boolean parameter from properties file
     *
     * @param parameterName the name of the parameter as appears in the properties file [String]
     * @return the parameter value as Boolean
     */
    public boolean getBooleanParameter(String parameterName) {
        String value = getStringParameter(parameterName);
        if (Strings.isNullOrEmpty(value)) {
            return false;
        }
        //This is to cover cases in properties where "Yes" or "Checked" means true and "No" or "Unchecked" means false.
        if (value.equalsIgnoreCase("Yes") || value.equalsIgnoreCase("Checked")) {
            value = "true";
        } else if (value.equalsIgnoreCase("No") || value.equalsIgnoreCase("Unchecked")) {
            value = "false";
        }
        return Boolean.parseBoolean(value.toLowerCase());
    }
    
    /**
     * This method returns the value of an Integer parameter from properties file
     *
     * @param parameterName the name of the parameter as appears in the properties file [String]
     * @return the parameter value as Integer
     */
    public int getIntegerParameter(String parameterName) {
        String value = getStringParameter(parameterName);
        if (Strings.isNullOrEmpty(value)) {
            Assert.fail("Integer parameter '" + parameterName + "' must have a value!");
        }
        return Integer.parseInt(value);
    }
    
    /**
     * This method creates an array of Integer parameters from properties file
     *
     * @param parameterPrefix the name of the parameter as appears in the properties file [String]
     * @return the parameter values as an array of Integers
     */
    public List<Integer> getMultipleIntegerParametersArray(String parameterPrefix) {
        return getMultipleIntegerParameters(parameterPrefix);
    }
    
    /**
     * This method creates a List of Integer parameters from properties file
     *
     * @param parameterPrefix the name of the parameter as appears in the properties file [String]
     * @return the parameter values as a List of Integers
     */
    public List<Integer> getMultipleIntegerParameters(String parameterPrefix) {
        List<Integer> values = new ArrayList<>();
        for (String value : getMultipleStringParameters(parameterPrefix)) {
            values.add(Integer.parseInt(value));
        }
        return values;
    }
    
    /**
     * This method returns an array of String parameters if exist in the properties file, otherwise Null
     *
     * @param parameterPrefix the name of the parameter as appears in the properties file [String]
     * @return the parameter values as an array of Strings or Null
     */
    public String[] getMultipleStringParametersIfExists(String parameterPrefix) {
        return parameterExists(parameterPrefix) ?
                getMultipleStringParametersArray(parameterPrefix) :
                null;
    }
    
    /**
     * This method creates an array of String parameters from properties file
     *
     * @param parameterPrefix the name of the parameter as appears in the properties file [String]
     * @return the parameter values as an array of Strings
     */
    public String[] getMultipleStringParametersArray(String parameterPrefix) {
        return getMultipleStringParameters(parameterPrefix).toArray(new String[0]);
    }
    
    /**
     * This method creates a List of String parameters from properties file
     *
     * @param parameterPrefix the name of the parameter as appears in the properties file [String]
     * @return the parameter values as a List of Strings
     */
    public List<String> getMultipleStringParameters(String parameterPrefix) {
        Properties targetProperties = getParameters(parameterPrefix);
        if (targetProperties.isEmpty()) {
            Assert.fail("No parameters with prefix '" + parameterPrefix + "' found in Test Data!");
        }
        return targetProperties.values().stream()
                .map(value -> Objects.toString(value, null))
                .collect(Collectors.toList());
    }
    
    /**
     * This method checks whether a given parameter exists in properties file.
     *
     * @param parameter the name of the parameter as appears in the properties file [String]
     * @return true if parameter exists, false otherwise
     */
    public boolean parameterExists(String parameter) {
        Properties targetProperties = getParameters(parameter);
        return !targetProperties.isEmpty();
    }
    
    /**
     * Loads test data from a given file path and returns a Properties object of the data.
     * If a dataSetPrefix is provided, it returns only the properties that have a key starting with the prefix.
     *
     * @param filepath      The path of the file containing the test data.
     * @param dataSetPrefix The prefix of the data set to return. Can be null to return all data.
     * @return A Properties object containing the test data.
     */
    public Properties getTestData(String filepath, String dataSetPrefix) {
        Properties prop = loadTestData(filepath);
        if (dataSetPrefix != null) {
            return getProperties(prop, dataSetPrefix);
        } else {
            return prop;
        }
    }
    
    /**
     * Loads test data from propertyFile and returns a Properties object of the data.
     * If a dataSetPrefix is provided, it returns only the properties that have a key starting with the prefix.
     *
     * @param dataSetPrefix The prefix of the data set to return. Can be null to return all data.
     * @return A Properties object containing the test data.
     */
    public Properties getTestData(String dataSetPrefix) {
        return getTestData(propertyFile, dataSetPrefix);
    }
    
    /**
     * Loads test data from a given file path and returns the specified user property value.
     *
     * @param filepath          The path of the file containing the test data.
     * @param userPropertyValue The property value to return.
     * @return The value of the specified user property.
     */
    public String getTestUserData(String filepath, String userPropertyValue) {
        Properties prop = loadTestData(filepath);
        return prop.getProperty(userPropertyValue);
    }
    
    /**
     * Loads test data from propertyFile and returns the specified user property value.
     *
     * @param userPropertyValue The property value to return.
     * @return The value of the specified user property.
     */
    public String getTestUserData(String userPropertyValue) {
        return getTestUserData(propertyFile, userPropertyValue);
    }
    
    /**
     * Loads Properties from a given test data file.
     *
     * @param testDataFile The path of the file containing the test data.
     * @return A Properties object containing the test data.
     */
    private Properties loadTestData(String testDataFile) {
        return loadProperties(testDataFile);
    }
    
    /**
     * Loads Properties from the default test data file ("testData.properties").
     *
     * @return A Properties object containing the test data.
     */
    private Properties loadTestData() {
        return loadTestData(propertyFile);
    }
    
    /**
     * Returns a Properties object containing only the properties with keys starting with a given prefix from a supplied Properties object.
     *
     * @param params The Properties object to extract properties from.
     * @param prefix The prefix of the properties to extract.
     * @return A new Properties object containing only the extracted properties.
     */
    @SuppressWarnings("rawtypes")
    private Properties getProperties(Properties params, String prefix) {
        Properties result = new Properties();
        Enumeration names = params.propertyNames();
        while (names.hasMoreElements()) {
            String name = (String) names.nextElement();
            if (name.indexOf(prefix) == 0) {
                result.put(name.substring(prefix.length() + 1), params.get(name));
            }
        }
        return result;
    }
    
    /**
     * Returns a Properties object containing only the properties with keys starting with a given prefix from the default test data object.
     *
     * @param prefix The prefix of the properties to extract.
     * @return A new Properties object containing only the extracted properties.
     */
    private Properties getProperties(String prefix) {
        return getProperties(testData, prefix);
    }
    
    /**
     * Returns a Properties object containing only the properties with keys starting with a given prefix (and optional period separator) from a supplied Properties object.
     *
     * @param params The Properties object to extract properties from.
     * @param prefix The prefix of the properties to extract.
     * @return A new Properties object containing only the extracted properties.
     */
    @SuppressWarnings("rawtypes")
    private Properties getParameters(Properties params, String prefix) {
        Properties targetParameters = new Properties();
        
        Enumeration allParameters = params.propertyNames();
        while (allParameters.hasMoreElements()) {
            String parameter = (String) allParameters.nextElement();
            if (parameter.startsWith(prefix)) {
                if (parameter.length() == prefix.length()) {
                    targetParameters.put(parameter, params.get(parameter));
                } else if (parameter.substring(prefix.length()).startsWith(".")) {
                    targetParameters.put(parameter.substring(prefix.length() + 1), params.get(parameter));
                }
            }
        }
        return targetParameters;
    }
    
    /**
     * Returns a Properties object containing only the properties with keys starting with a given prefix (and optional period separator) from the default test data object.
     *
     * @param prefix The prefix of the properties to extract.
     * @return A new Properties object containing only the extracted properties.
     */
    private Properties getParameters(String prefix) {
        return getParameters(testData, prefix);
    }
    
    /**
     * Get the file path for the property file corresponding to the given test suite name.
     * Searches for the file path in order of precedence:
     * 1. Second structure without regions in the filename
     * resources\
     * test-data\
     * properties\
     * preprod\
     * TS_API_SmokeTests.properties
     * sit\
     * TS_APLSmokeTests.properties
     * uat\
     * TS_APl_SmokeTests.properties
     * 2. Third structure with only the test suite name
     * resources\
     * test-data\
     * properties\
     * TS_APl_SmokeTests.properties
     *
     * @param testSuiteName The name of the test suite.
     * @return The file path for the property file, or null if it does not exist.
     */
    private String initializePropertyFile(String testSuiteName) {
        ResourcesConfig resourcesConfig = new ResourcesConfig();
        
        String filePath;
        
        filePath = getFilepath(testSuiteName, resourcesConfig.getTestCasePropertiesDir() + getEnvironment().getName() + "/");
        if (filePath != null) {
            return filePath;
        }
        
        filePath = getFilepath(testSuiteName, resourcesConfig.getTestCasePropertiesDir());
        return filePath;
    }
    
    /**
     * Get the file path for the given test suite name, directory path, and region name.
     *
     * @param testSuiteName The name of the test suite.
     * @param directoryPath The directory path where the file should be located.
     * @return The file path for the property file, or null if it does not exist.
     */
    private String getFilepath(String testSuiteName, String directoryPath) {
        String filePath = directoryPath + testSuiteName + ".properties";
        
        File file = new File(filePath);
        if (file.exists()) {
            return filePath;
        }
        return null;
    }
}
