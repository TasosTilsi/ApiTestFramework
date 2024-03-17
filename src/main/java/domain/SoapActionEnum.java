package domain;

public enum SoapActionEnum {
    
    CREATE_USER_SERVICE("someSoapAction");
    
    private String value;
    
    SoapActionEnum(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
    
    
    @Override
    public String toString() {
        return value;
    }
}
