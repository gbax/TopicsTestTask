package com.gbax.TopicsTestTask.system.exception;

import com.gbax.TopicsTestTask.enums.Errors;

/**
 * Created by abayanov
 * Date: 28.08.14
 */
public class NotAuthorizedException extends Exception {

    private Errors error;

    public NotAuthorizedException(Errors error) {
        this.error = error;
    }


    public Errors getError() {
        return error;
    }
}
