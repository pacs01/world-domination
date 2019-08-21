package io.scherler.games.risk.controllers;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(Long id) {
        super("Resource '" + id + "' not found!");
    }

    public ResourceNotFoundException(String resource) {
        super("Resource '" + resource + "' not found!");
    }
}
