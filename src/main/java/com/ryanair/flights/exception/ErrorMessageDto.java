package com.ryanair.flights.exception;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorMessageDto {

    @JsonProperty("errorCode")
    private String errorCode;
    @JsonProperty("message")
    private String message;

    public ErrorMessageDto() {
    }

    public ErrorMessageDto(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
