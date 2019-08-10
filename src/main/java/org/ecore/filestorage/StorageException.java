package org.ecore.filestorage;

public class StorageException extends RuntimeException {

    public StorageException(String message) {
        super(message);
    }
    //test

    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}

