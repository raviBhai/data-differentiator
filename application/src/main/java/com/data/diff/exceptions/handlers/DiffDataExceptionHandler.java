package com.data.diff.exceptions.handlers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.data.diff.constants.Constants;
import com.data.diff.exceptions.EmptyLeftDataException;
import com.data.diff.exceptions.EmptyRightDataException;
import com.data.diff.exceptions.LeftAndRightDataFieldsNotSameException;
import com.data.diff.exceptions.LeftAndRightDataSizeNotSameException;
import com.data.diff.exceptions.NoRecordFoundException;

@ControllerAdvice
@RestController
public class DiffDataExceptionHandler extends ResponseEntityExceptionHandler implements Constants {

    @ExceptionHandler({
            EmptyLeftDataException.class,
            EmptyRightDataException.class,
            LeftAndRightDataSizeNotSameException.class,
            LeftAndRightDataFieldsNotSameException.class
    })
    public final ResponseEntity handleEmptyLeftOrRightDataException(Exception ex) {
        return new ResponseEntity(response(FAILURE, ex.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(NoRecordFoundException.class)
    public final ResponseEntity handleNoRecordFoundException(Exception ex) {
        return new ResponseEntity(response(FAILURE, ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    private Map<String, String> response(String status, String message) {
        Map<String, String> map = new HashMap<>();
        map.put(STATUS, status);
        map.put(RESULT, message);
        return map;
    }
}
