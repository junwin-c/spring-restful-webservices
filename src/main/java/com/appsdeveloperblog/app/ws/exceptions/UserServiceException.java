package com.appsdeveloperblog.app.ws.exceptions;

public class UserServiceException extends RuntimeException{
    private static final long serialVersionUID = -2521981827157843969L;

    public UserServiceException(String message) {
        super(message);
    }

}
