package domain;

import domain.interfaces.IEndpoint;
import lombok.Getter;

@Getter
public enum SoapBasePathEnum implements IEndpoint {
    SOAP_SERVICE("/websamples.countryinfo"),
    COUNTRY_INFO(SOAP_SERVICE.getPath() + "/CountryInfoService.wso");
    
    private final String path;
    
    SoapBasePathEnum(String path) {
        this.path = path;
    }
    
    @Override
    public String toString() {
        return path;
    }
}
