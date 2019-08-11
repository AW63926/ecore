package org.ecore.filestorage;

import org.ecore.filestorage.StorageException;

public class StorageFileNotFoundException extends StorageException {

    public StorageFileNotFoundException(String message) {
        super(message);
    }
    //test
    public StorageFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
