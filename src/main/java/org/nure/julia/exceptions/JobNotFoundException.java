package org.nure.julia.exceptions;

public class JobNotFoundException extends RuntimeException {

    public JobNotFoundException() { super(); }

    public JobNotFoundException(String msg) { super(msg); }

    public JobNotFoundException(String msg, Throwable throwable) { super(msg, throwable); }

}
