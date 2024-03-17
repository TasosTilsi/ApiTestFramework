package utils.config;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

public class ResourcesConfig {
    
    public String getOutputDir() {
        return getTestResourcesPath() + "/test-data/outputDir/";
    }
    
    public String getInputDir() {
        return getTestResourcesPath() + "/test-data/inputDir/";
    }
    
    public String getTestDataDir() {
        return getTestResourcesPath() + "/test-data/";
    }
    
    public String getTestCasePropertiesDir() {
        return getTestResourcesPath() + "/test-data/properties/";
    }
    
    public String getEnvironmentProperties() {
        String envPropertiesPath;
        if (System.getProperty("env.properties") == null) {
            envPropertiesPath = getAbsolutePath() + getPomProperty("env.properties");
            if (envPropertiesPath.endsWith("null")) {
                envPropertiesPath = getResourcesPath() + "/config/local.properties";
            }
        } else {
            envPropertiesPath = getAbsolutePath() + System.getProperty("env.properties");
        }
        return envPropertiesPath;
    }
    
    public String getSecretProperties() {
        if (System.getProperty("secret.properties") == null) {
            return getAbsolutePath() + getPomProperty("secret.properties");
        } else {
            return getAbsolutePath() + System.getProperty("secret.properties");
        }
    }
    
    public String getTestResourcesPath() {
        return getResourcesPath("test");
    }
    
    public String getResourcesPath() {
        return getResourcesPath("main");
    }
    
    public String getAbsolutePath() {
        String absPath = Paths.get(".")
                .toAbsolutePath().normalize().toString().replace("\\", "/");
        
        String modulePath = this.getClass().getClassLoader().getResource(".").getPath();
        modulePath = modulePath.replace("\\", "/");
        modulePath = modulePath.replace("/target/test-classes", "");
        modulePath = modulePath.replace("/target/classes", "");
        modulePath = modulePath.replace(absPath, "");
        modulePath = modulePath.replace("//", "/");
        
        return absPath + modulePath;
    }
    
    public String getTargetPath() {
        String absPath = Paths.get(".")
                .toAbsolutePath().normalize().toString().replace("\\", "/");
        
        String modulePath = this.getClass().getClassLoader().getResource(".").getPath();
        modulePath = modulePath.replace("\\", "/");
        modulePath = modulePath.replace("test-classes", "");
        modulePath = modulePath.replace("classes", "");
        modulePath = modulePath.replace(absPath, "");
        modulePath = modulePath.replace("//", "/");
        
        return absPath + modulePath;
    }
    
    private String getPomProperty(String propertyName) {
        Model model = null;
        MavenXpp3Reader reader = new MavenXpp3Reader();
        try {
            model = reader.read(new FileReader(getAbsolutePath() + "/pom.xml"));
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
        }
        return model.getProperties().getProperty(propertyName);
    }
    
    private String getResourcesPath(String packageName) {
        String filePathString = getAbsolutePath() + "src/" + packageName + "/resources";
        File f = new File(filePathString);
        if (!f.exists())
            filePathString = getAbsolutePath();
        return filePathString;
    }
}
