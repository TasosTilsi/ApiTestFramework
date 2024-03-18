package domain;

import lombok.Getter;

public enum SoapBasePathEnum {
    SOAP_SERVICE("/websamples.countryinfo"),
    COUNTRY_INFO(SOAP_SERVICE.getPath() + "/CountryInfoService.wso");
    
    @Getter
    private String path;
    
    SoapBasePathEnum(String path) {
        this.path = path;
    }
    
    @Override
    public String toString() {
        return path;
    }
}
