package com.ideas2it.ems.exception;

/**
 * <p>
 *     Defines the blueprint for exception when fields which are not available
 *     given as a input.
 * </p>
 *
 * @author Saiprasath
 * @version 1.5
 */
public class EntityAlreadyExistsException extends RuntimeException{
    public EntityAlreadyExistsException(String message){
        super(message);
    }
}
