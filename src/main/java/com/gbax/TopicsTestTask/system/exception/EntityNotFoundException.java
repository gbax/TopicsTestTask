package com.gbax.TopicsTestTask.system.exception;

import com.gbax.TopicsTestTask.enums.Errors;

/**
 * Created by abayanov
 * Date: 26.08.14
 */
public class EntityNotFoundException extends Exception {

    private Errors error;

    public EntityNotFoundException(Errors error) {
        this.error = error;
    }


    public Errors getError() {
        return error;
    }
}
