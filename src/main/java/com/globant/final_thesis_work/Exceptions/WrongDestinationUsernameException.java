package com.globant.final_thesis_work.Exceptions;

public class WrongDestinationUsernameException extends Exception {
    public static final String MESSAGE = "Wrong Username in List: ";

    public WrongDestinationUsernameException(String list) {
        super(MESSAGE + list);
    }
}
