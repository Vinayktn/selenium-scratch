package com.vinaykumar.hrmauto.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    private static Properties props = new Properties();

    static {
        InputStream in = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties");
        try {
            props.load(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String get(String key) {
        return System.getProperty(key, props.getProperty(key));
    }
}