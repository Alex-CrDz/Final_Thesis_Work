package com.globant.final_thesis_work.Exceptions;

public class MessageNotFoundException extends RuntimeException {
    public static final String MESSAGE = "Message Not Found";

    public MessageNotFoundException() {
        super(MESSAGE);
    }
}
