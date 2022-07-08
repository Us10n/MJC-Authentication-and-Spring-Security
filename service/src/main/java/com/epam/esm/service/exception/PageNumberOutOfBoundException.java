package com.epam.esm.service.exception;

/**
 * The type Page number out of bound exception.
 */
public class PageNumberOutOfBoundException extends RuntimeException{
    /**
     * Instantiates a new Page number out of bound exception.
     */
    public PageNumberOutOfBoundException() {
        super();
    }

    /**
     * Instantiates a new Page number out of bound exception.
     *
     * @param message the message
     */
    public PageNumberOutOfBoundException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Page number out of bound exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public PageNumberOutOfBoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Page number out of bound exception.
     *
     * @param cause the cause
     */
    public PageNumberOutOfBoundException(Throwable cause) {
        super(cause);
    }
}
