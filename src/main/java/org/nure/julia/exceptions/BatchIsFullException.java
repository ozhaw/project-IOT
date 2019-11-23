package org.nure.julia.exceptions;

public class BatchIsFullException extends RuntimeException {

    public BatchIsFullException() { super(); }

    public BatchIsFullException(String msg) { super(msg); }

    public BatchIsFullException(String msg, Throwable throwable) { super(msg, throwable); }

}
