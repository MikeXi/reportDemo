package com.epam.model;

public class UserBuilder implements UserIService {
    private String username;
    private String password;

    @Override
    public UserBuilder setUsername(String username) {
        this.username = username;
        return this;
    }

    @Override
    public UserBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public User createUser() {
        return new User(username, password);
    }
}