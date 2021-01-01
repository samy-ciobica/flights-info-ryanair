package com.ryanair.flights.enums;


public enum ErrorEnum {

    //HTTP ERRORS
    ERR_404("404", "Resource not found."),
    ERR_400("007", "Bad parameters."),
    ERR_422("008", "Unprocessable entity."),

    ERR_504("504", "Server timeout"),
    ERR_503_PARSING_DATA("90001", "Error parsing data.");


    private String code;
    private String msg;

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    ErrorEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ErrorEnum valueOfCode(String code) {
        for (ErrorEnum error : ErrorEnum.values()) {
            if (error.getCode().equalsIgnoreCase(code)) {
                return error;
            }
        }
        return null;
    }


    }