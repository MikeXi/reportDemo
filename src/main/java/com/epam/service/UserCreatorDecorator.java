package com.epam.service;


import com.epam.model.User;

public class UserCreatorDecorator implements UserCreator {
    private UserCreator wrappee;

    UserCreatorDecorator(UserCreator userCreator){
        this.wrappee = userCreator;
    }

    @Override
    public User createUser() {
        return wrappee.createUser();
    }
}
