package com.qayas.Exceptions;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(UUID userId) {
        super("User with Id: " + userId + " was not found.");
    }
}
