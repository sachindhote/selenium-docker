package com.automation.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Properties;

public class Config {

    private static final Logger log = LoggerFactory.getLogger(Config.class);
    private static final String DEFAULT_PROPERTIES = "config/default.properties";
    private static Properties properties;

    public static void initialize(){

        // load default properties
        properties = loadProperties();

        // check for any override using command line
        for(String key: properties.stringPropertyNames()){
            if(System.getProperties().containsKey(key)){
                properties.setProperty(key, System.getProperty(key));
            }
        }

        // print properties
        log.info("Test Properties");
        log.info("-----------------");
        for(String key: properties.stringPropertyNames()){
            log.info("{}={}", key, properties.getProperty(key));
        }
        log.info("-----------------");

    }

    // TO USE THE PROPERTIES BY ANY CLASS BY CALLING THIS METHOD
    public static String get(String key){
        return properties.getProperty(key);
    }

    //to load the properties file
    private static Properties loadProperties(){
        Properties properties = new Properties();
        try(InputStream stream = ResourceLoader.getResource(DEFAULT_PROPERTIES)){
            properties.load(stream);
        }catch (Exception e){
            log.error("unable to read the property file {}", DEFAULT_PROPERTIES, e);
        }
        return properties;
    }

}
