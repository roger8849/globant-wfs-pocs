package com.globant.testing.framework.web.exceptions;

/**
 * @author Juan Krzemien
 */
public class MethodInvocationError extends Error {

    public MethodInvocationError(String msg) {
        super(msg);
    }

    public MethodInvocationError(String msg, Throwable t) {
        super(msg, t);
    }

}
