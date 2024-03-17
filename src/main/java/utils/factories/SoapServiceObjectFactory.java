package utils.factories;

import services.soap.SoapCommonRequests;
import utils.service.implementation.Soap;

public class SoapServiceObjectFactory {
    private final Soap soap;
    
    public SoapServiceObjectFactory(Soap soap) {
        this.soap = soap;
    }
    
    public SoapCommonRequests commonRequests() {
        return new SoapCommonRequests(soap);
    }
    
}
