package com.globant.final_thesis_work.Exceptions;

public class PasswordNotMatchException extends Exception {
    public static final String MESSAGE = "Password Not Match";

    public PasswordNotMatchException() {
        super(MESSAGE);
    }
}
