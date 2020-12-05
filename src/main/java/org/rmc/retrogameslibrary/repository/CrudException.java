package org.rmc.retrogameslibrary.repository;

public class CrudException extends Exception {

    private static final long serialVersionUID = 1L;

    public CrudException(String message) {
        super(message);
    }

    public CrudException(String message, Throwable cause) {
        super(message, cause);
    }

    public CrudException(Throwable cause) {
        super(cause);
    }
}
