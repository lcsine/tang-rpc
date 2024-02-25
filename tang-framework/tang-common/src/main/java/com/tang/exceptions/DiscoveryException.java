package com.tang.exceptions;

public class DiscoveryException extends RuntimeException {
    public DiscoveryException() {
        super();
    }

    public DiscoveryException(Throwable cause) {
        super(cause);
    }

    public DiscoveryException(String message) {
        super(message);
    }
}
