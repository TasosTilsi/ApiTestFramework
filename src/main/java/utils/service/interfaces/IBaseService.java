package utils.service.interfaces;

import models.ContextData;

/**
 * This interface represents a base service.
 * It provides methods for accessing the logger and context factory.
 */
public interface IBaseService {
    
    IRestService rest();
    
    ISoapService soap();
    
    /**
     * Returns the context factory for this service.
     *
     * @return the context factory
     */
    ContextData context();
}
