package com.epam.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestDataReader {
    private static final Logger logger = LogManager.getRootLogger();
    private static Properties properties = new Properties();

    public static void setEnvoronment(String env) throws IOException {
        logger.info("\n" + "Test environment is: " + env);
        switch (env){
            case("QA"):{
                BufferedReader bufferedReader = new BufferedReader(new FileReader("./src/config/qa.properties"));
                properties.load(bufferedReader);
            }
            default:{
                BufferedReader bufferedReader = new BufferedReader(new FileReader("./src/config/dev.properties"));
                properties.load(bufferedReader);
            }
        }
    }
    public static String getTestData(String key){
        return properties.getProperty(key);
//        return resourceBundle.getString(key);
    }
}