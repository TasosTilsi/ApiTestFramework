package domain;

public enum SoapBasePathEnum {
    USER_SERVICE("someUserServiceEndpoint");
    
    private String path;
    
    SoapBasePathEnum(String path) {
        this.path = path;
    }
    
    public String getPath() {
        return path;
    }
    
    @Override
    public String toString() {
        return path;
    }
}
