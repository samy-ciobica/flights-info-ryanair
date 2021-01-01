package com.ryanair.flights.exception;


import com.ryanair.flights.enums.ErrorEnum;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;


@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionController.class);


    private ErrorMessageDto error = new ErrorMessageDto();

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        error.setMessage(HttpStatus.UNSUPPORTED_MEDIA_TYPE.getReasonPhrase());
        error.setErrorCode(String.valueOf(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()));
        return new ResponseEntity<>(error, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        error.setErrorCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
        error.setMessage(ex.getParameterName() + ": is required");
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        error.setErrorCode(HttpStatus.NOT_FOUND.toString());
        error.setMessage(ErrorEnum.ERR_404.getMsg());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, WebRequest request) {
        error.setErrorCode(HttpStatus.BAD_REQUEST.toString());
        error.setMessage(ErrorEnum.ERR_400.getMsg() + ": Invalid parameter: '" + ex.getName() + "'");
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        error.setErrorCode(ErrorEnum.ERR_404.getCode());
        error.setMessage(ErrorEnum.ERR_404.getMsg());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @Override
    protected @ResponseBody
    ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        error.setErrorCode(ErrorEnum.ERR_400.getCode());
        error.setMessage(ErrorEnum.ERR_400.getMsg());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected @ResponseBody
    ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        error.setErrorCode(ErrorEnum.ERR_400.getCode());
        error.setMessage(ErrorEnum.ERR_400.getMsg());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected @ResponseBody
    ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        error.setErrorCode(String.valueOf(HttpStatus.METHOD_NOT_ALLOWED.value()));
        error.setMessage(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(value = {IntegrationException.class})
    public ResponseEntity<ErrorMessageDto> handleIntegrationException(IntegrationException e) {
        error.setErrorCode(e.getErrorEnum().getCode());
        error.setMessage(StringUtils.isEmpty(e.getErrorMsg()) ? e.getErrorEnum().getMsg() : e.getErrorMsg());
        LOGGER.error("IntegrationException: [{} - {}] ", e.getErrorEnum().getCode(), e.getErrorEnum().getMsg());
        return new ResponseEntity<>(error, e.getHttpCode());
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseEntity<ErrorMessageDto> handleConstraintViolationException(ConstraintViolationException e) {
        error.setErrorCode(HttpStatus.BAD_REQUEST.toString());
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        StringBuilder strBuilder = new StringBuilder();
        for (ConstraintViolation<?> violation : violations) {
            strBuilder.append(violation.getPropertyPath()).append(":").append(violation.getMessage()).append("\n");
        }
        error.setMessage(strBuilder.toString());
        LOGGER.error("ConstraintViolationException: {}", strBuilder.toString());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}
