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
    
}
