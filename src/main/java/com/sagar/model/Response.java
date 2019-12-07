package com.sagar.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Response<T> {
    private Integer statusCode;
    private HttpStatus status;
    private String errMessage;
    private boolean isValidationError;
    private String validationMsg;
    private T entity;
    private List<T> entities;
    private boolean isSuccess;

    public Response(Integer statusCode, String errMessage) {
        this.statusCode = statusCode;
        this.errMessage = errMessage;
    }
}
