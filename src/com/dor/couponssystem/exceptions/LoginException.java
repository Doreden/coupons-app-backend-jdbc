package com.dor.couponssystem.exceptions;

public class LoginException extends Exception {
    public LoginException(String message) {
        super(String.format("Failed To Connect customer this.email"));
    }
}
