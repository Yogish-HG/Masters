package com.project.accomatch.LoggerPack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerClass {

    private static final Logger logger = LoggerFactory.getLogger(LoggerClass.class);

    /**
     * Static method to get the logger instance.
     * @author Yogish Honnadevipura Gopalakrishna
     * @return The SLF4J logger instance for the class.
     */
    public static Logger getLogger(){
        return logger;
    }
}
