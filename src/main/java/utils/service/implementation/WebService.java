package utils.service.implementation;


import models.ContextData;
import org.slf4j.Logger;
import utils.service.interfaces.IBaseService;
import utils.service.interfaces.IRestService;
import utils.service.interfaces.ISoapService;

/**
 * This class represents a web service and implements the BaseService interface.
 * It provides methods for accessing the REST service, SOAP service, logger,
 * and context factory.
 */
public class WebService implements IBaseService {
    
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(WebService.class);
    private IRestService rest;
    private ISoapService soap;
    private ContextData contextData;
    
    /**
     * Constructs a new instance of the WebService class.
     * Initializes the rest, soap, logger, and contextFactory.
     */
    public WebService() {
        this.rest = new Rest();
        this.soap = new Soap();
        this.contextData = new ContextData();
    }
    
    /**
     * Returns the context factory for this web service.
     *
     * @return the context factory
     */
    @Override
    public ContextData context() {
        return this.contextData;
    }
    
    /**
     * Returns the REST service associated with this web service.
     *
     * @return the REST service
     */
    public IRestService rest() {
        return this.rest;
    }
    
    /**
     * Returns the SOAP service associated with this web service.
     *
     * @return the SOAP service
     */
    public ISoapService soap() {
        return this.soap;
    }
}
