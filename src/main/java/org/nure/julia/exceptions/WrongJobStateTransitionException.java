package org.nure.julia.exceptions;

public class WrongJobStateTransitionException extends RuntimeException {

    public WrongJobStateTransitionException() { super(); }

    public WrongJobStateTransitionException(String msg) { super(msg); }

    public WrongJobStateTransitionException(String msg, Throwable throwable) { super(msg, throwable); }

}
