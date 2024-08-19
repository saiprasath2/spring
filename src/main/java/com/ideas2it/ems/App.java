package com.ideas2it.ems;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
public class App 
{
    private static final Logger logger = LogManager.getLogger();
    public static void main( String[] args )
    {
        SpringApplication.run(App.class, args );
        logger.info("Application initialized.");
    }
}
