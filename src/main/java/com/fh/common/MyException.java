package com.fh.common;

public class MyException extends RuntimeException {
    public MyException() {
    }

    public MyException(String message) {
        super(message);
    }
}
