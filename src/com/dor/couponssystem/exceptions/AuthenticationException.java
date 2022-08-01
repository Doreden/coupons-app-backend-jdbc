package com.dor.couponssystem.exceptions;

public class AuthenticationException extends ApplicationException {

    public AuthenticationException(final String email) {

        super("failed to authentication: " + email);
    }
}
