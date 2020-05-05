package com.finra.phone.number.service.exceptions;

import org.hibernate.TypeMismatchException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.finra.phone.number.service.exceptions.ErrorCode.CONSTRAINT_VIOLATION;
import static com.finra.phone.number.service.exceptions.ErrorCode.UNKNOWN_ERROR;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice
public class RestControllerExceptionHandler {
    
    @ExceptionHandler(value = {ConstraintViolationException.class})
    @ResponseBody
    @ResponseStatus(value = BAD_REQUEST)
    public ApiError handleConstraintViolation(ConstraintViolationException ex) {
        
        Set<ConstraintViolation<?>> violationsList = ex.getConstraintViolations();
        
        List<String> errors = violationsList.stream()
                .map(violation -> violation.getPropertyPath() + " " + violation.getMessage())
                .collect(Collectors.toList());
        
        return new ApiError(CONSTRAINT_VIOLATION.getCode(), "Request input is invalid.", errors);
    }
    

    @ExceptionHandler(TypeMismatchException.class)
    @ResponseBody
    public ApiError handleTypeMismatchException(TypeMismatchException typeMismatchException) {
        
        return new ApiError(CONSTRAINT_VIOLATION.getCode(),
                "Request input is invalid.",
                singletonList(typeMismatchException.getMessage()));
    }
    
    @ExceptionHandler(value = {RuntimeException.class})
    @ResponseBody
    @ResponseStatus(value = INTERNAL_SERVER_ERROR)
    public ApiError handleUnexpectedFailures(RuntimeException ex) {
        
        return new ApiError(UNKNOWN_ERROR.getCode(),
                String.format("An unknown error occurred on the server. - %s", ex.getMessage()),
                emptyList());
    }
}
