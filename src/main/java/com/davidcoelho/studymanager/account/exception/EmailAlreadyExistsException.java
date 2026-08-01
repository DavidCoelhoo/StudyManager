package com.davidcoelho.studymanager.account.exception;

public class EmailAlreadyExistsException  extends RuntimeException{
    public EmailAlreadyExistsException(String email) {
        super("Email '" + email + "' is already in use.");
    }
}
