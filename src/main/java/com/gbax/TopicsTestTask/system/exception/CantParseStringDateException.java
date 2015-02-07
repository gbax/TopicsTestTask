package com.gbax.TopicsTestTask.system.exception;

/**
 * Created by Баянов on 07.02.2015.
 */
public class CantParseStringDateException extends Exception {

    private String error;

    public CantParseStringDateException(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

}
