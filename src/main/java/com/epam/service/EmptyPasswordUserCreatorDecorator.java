package com.epam.service;

import com.epam.model.User;
import com.epam.model.UserBuilder;

public class EmptyPasswordUserCreatorDecorator extends UserCreatorDecorator{
    public static final String TESTDATA_USER_NAME = "testdata.user.name";

    public EmptyPasswordUserCreatorDecorator(UserCreator userCreator){
        super(userCreator);
    }

    @Override
    public User createUser(){
        return new UserBuilder().setUsername(TestDataReader.getTestData(TESTDATA_USER_NAME)).setPassword("").createUser();
    }
}
