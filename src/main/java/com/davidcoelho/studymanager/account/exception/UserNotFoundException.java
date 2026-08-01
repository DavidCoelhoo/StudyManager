package com.davidcoelho.studymanager.account.exception;

public class UserNotFoundException  extends RuntimeException{

    public UserNotFoundException(Integer id){
        super("User with id " + id + " was not found.");

    }
    public UserNotFoundException(String email) {
        super("User with email '" + email + "' not found");
    }
}
