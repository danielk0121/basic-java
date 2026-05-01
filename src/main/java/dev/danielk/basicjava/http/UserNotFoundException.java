package dev.danielk.basicjava.http;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("user not found: " + id);
    }
}
