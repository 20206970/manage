package com.db.bms.exception;

public class OperationFailureException extends RuntimeException{
    public OperationFailureException() {
    }

    public OperationFailureException(String message) {
        super(message);
    }
}
