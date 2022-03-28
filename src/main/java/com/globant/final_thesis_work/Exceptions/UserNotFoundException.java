package com.globant.final_thesis_work.Exceptions;

public class UserNotFoundException extends RuntimeException {
    public static final String MESSAGE = "User Not Found";

    public UserNotFoundException() {
        super(MESSAGE);
    }
}
