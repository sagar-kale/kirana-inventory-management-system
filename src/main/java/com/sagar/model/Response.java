package com.sagar.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class Response<T> {
    private Integer statusCode;
    private HttpStatus status;
    private boolean isError = false;
    private String errMessage;
    private boolean isValidationError = false;
    private String validationMsg;
    private T entity;
    private List<T> entities;
    private boolean isSuccess = false;
    private Map<String, String> validationErrors;
    private String msg;

    public Response(Integer statusCode, String errMessage, boolean isError) {
        this.statusCode = statusCode;
        this.errMessage = errMessage;
        this.isError = isError;
    }
}
