package com.epam.service;

import com.epam.model.User;
import com.epam.model.UserBuilder;

public class EmptyUserNameUserCreatorDecorator extends UserCreatorDecorator{
    public static final String TESTDATA_USER_PASSWORD = "testdata.user.password";

    public EmptyUserNameUserCreatorDecorator(UserCreator userCreator){
        super(userCreator);
    }

    @Override
    public User createUser(){
        return new UserBuilder().setUsername("").setPassword(TestDataReader.getTestData(TESTDATA_USER_PASSWORD)).createUser();
    }
}
