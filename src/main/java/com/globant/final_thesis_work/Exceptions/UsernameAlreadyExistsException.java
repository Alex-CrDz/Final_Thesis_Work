package com.globant.final_thesis_work.Exceptions;

public class UsernameAlreadyExistsException extends Exception {
    public static final String MESSAGE = "Username already exists";

    public UsernameAlreadyExistsException() {
        super(MESSAGE);
    }
}
