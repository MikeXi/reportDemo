package com.epam.service;

import com.epam.model.User;
import com.epam.model.UserBuilder;

public class EmailUserCreator implements UserCreator{

    @Override
    public User createUser(){
        return new UserBuilder().setUsername("").setPassword("").createUser();
    }
}
