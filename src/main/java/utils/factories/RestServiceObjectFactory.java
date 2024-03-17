package utils.factories;

import services.rest.RestCommonRequests;
import utils.service.implementation.Rest;

public class RestServiceObjectFactory {
    private final Rest rest;
    
    public RestServiceObjectFactory(Rest rest) {
        this.rest = rest;
    }
    
    public RestCommonRequests commonRequests() {
        return new RestCommonRequests(rest);
    }
}
