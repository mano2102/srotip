package com.srotip.userservice.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String messsage) {
        super(messsage);
    }

}
