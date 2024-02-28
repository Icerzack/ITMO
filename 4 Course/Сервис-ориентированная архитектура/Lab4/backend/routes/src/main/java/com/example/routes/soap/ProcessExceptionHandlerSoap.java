package com.example.routes.soap;

import com.example.routes.dto.ErrorDefaultDTO;
import com.example.routes.exception.EntityNotFoundException;
import com.example.routes.exception.NotValidParamsException;
import com.example.routes.soap.exception.NotFoundException;
import com.example.routes.soap.soapgen.Error;
import com.example.routes.soap.soapgen.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.ws.soap.SoapFaultException;
import org.springframework.ws.soap.server.endpoint.SoapFaultDefinition;
import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;

import javax.persistence.NoResultException;

@ControllerAdvice
public class ProcessExceptionHandlerSoap {
    private final ResponseUtils responseUtils;

    @Autowired
    public ProcessExceptionHandlerSoap(ResponseUtils responseUtils) {
        this.responseUtils = responseUtils;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<Error> validationException(MethodArgumentNotValidException e) {
        e.printStackTrace();
        return responseUtils.buildResponseWithMessage(HttpStatus.BAD_REQUEST, e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Error> validationParamException(MethodArgumentTypeMismatchException e){
        e.printStackTrace();
        return responseUtils.buildResponseWithMessage(HttpStatus.BAD_REQUEST, "Invalid param supplied");
    }


    @ExceptionHandler(NoResultException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Error> noResultException(NoResultException e){
        e.printStackTrace();
        return responseUtils.buildResponseWithMessage(HttpStatus.NOT_FOUND, "Not Found");
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ResponseEntity<Error> validationException(HttpMessageNotReadableException e){
        e.printStackTrace();
        return responseUtils.buildResponseWithMessage(HttpStatus.METHOD_NOT_ALLOWED, "Validation exception");
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Error> methodNotFound(HttpRequestMethodNotSupportedException e){
        return responseUtils.buildResponseWithMessage(HttpStatus.NOT_FOUND, "Not Found");
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Error> handleNotFoundException(EntityNotFoundException e) {
        return responseUtils.buildResponseWithMessage(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(NotValidParamsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Error> handleIllegalArgumentException(NotValidParamsException e) {
        return responseUtils.buildResponseWithMessage(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Error> handleThrowable(Throwable e) {
        e.printStackTrace();
        return responseUtils.buildResponseWithMessage(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
}
