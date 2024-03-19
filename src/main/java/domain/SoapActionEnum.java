package domain;

import domain.interfaces.IEndpoint;

public enum SoapActionEnum implements IEndpoint {
    
    CREATE_USER_SERVICE("someSoapAction");
    
    private final String path;
    
    SoapActionEnum(String path) {
        this.path = path;
    }
    
    
    @Override
    public String getPath() {
        return path;
    }
    
    @Override
    public String toString() {
        return path;
    }
}
