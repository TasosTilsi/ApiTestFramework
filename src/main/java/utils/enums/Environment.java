package utils.enums;

import lombok.Getter;

@Getter
public enum Environment {
    LOCAL("local"),
    DEV("dev"),
    SIT("sit"),
    UAT("uat"),
    PREPROD("preprod");
    
    private final String name;
    
    Environment(String name) {
        this.name = name;
    }
    
    public static Environment fromString(String name) {
        for (Environment env : Environment.values()) {
            if (env.name.equals(name)) {
                return env;
            }
        }
        throw new IllegalArgumentException("No constant with name " + name + " found");
    }
}
