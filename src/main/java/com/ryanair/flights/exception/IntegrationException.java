package com.ryanair.flights.exception;

import com.ryanair.flights.enums.ErrorEnum;
import org.springframework.http.HttpStatus;

public class IntegrationException extends RuntimeException {

    private HttpStatus httpCode;
    private ErrorEnum errorEnum;
    private String errorMsg;

    public IntegrationException(HttpStatus httpCode, ErrorEnum errorEnum) {
        super();
        this.httpCode = httpCode;
        this.errorEnum = errorEnum;
    }

    public IntegrationException(HttpStatus httpCode, ErrorEnum errorEnum, String errorMsg) {
        super(errorMsg);
        this.httpCode = httpCode;
        this.errorEnum = errorEnum;
        this.errorMsg = errorMsg;
    }

    public HttpStatus getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(HttpStatus httpCode) {
        this.httpCode = httpCode;
    }

    public ErrorEnum getErrorEnum() {
        return errorEnum;
    }

    public void setErrorEnum(ErrorEnum errorEnum) {
        this.errorEnum = errorEnum;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}