package com.my.workflow.exception;

public class TaskApiException  extends RuntimeException {
    public TaskApiException(String exception) {
        super(exception);
    }
}