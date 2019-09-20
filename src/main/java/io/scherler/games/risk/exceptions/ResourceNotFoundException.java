package io.scherler.games.risk.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String resource, Long id) {
        super("Resource '" + resource + "' with id '" + id + "' not found!");
    }

    public ResourceNotFoundException(String resource, String criteria) {
        super("Resource '" + resource + "' with '" + criteria + "' not found!");
    }
}
