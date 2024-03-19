package utils.factories;

import services.rest.RestCommonRequests;
import utils.factories.interfaces.IRestServiceFactory;
import utils.service.implementation.Rest;

public class RestServiceObjectFactory implements IRestServiceFactory {
    private final Rest rest;
    
    public RestServiceObjectFactory(Rest rest) {
        this.rest = rest;
    }
    
    public RestCommonRequests commonRequests() {
        return new RestCommonRequests(rest);
    }
}
