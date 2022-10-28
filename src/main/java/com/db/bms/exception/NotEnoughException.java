package com.db.bms.exception;

/**
 * 库存不足异常
 *
 */
public class NotEnoughException extends RuntimeException{
    public NotEnoughException() {
        super();
    }

    public NotEnoughException(String message) {
        super(message);
    }
}
