package by.bsuir.kostyademens.exception;

import java.nio.file.FileSystemException;

public class FolderAlreadyExistsException extends Exception {
    public FolderAlreadyExistsException(String message) {
        super(message);
    }
}
