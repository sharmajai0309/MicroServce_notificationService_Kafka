package com.notification.api.exception.handler;

import com.notification.api.exception.AbstractException;
import com.notification.api.exception.ResourceNotFoundException;
import com.notification.api.exception.ValidationException;
import com.notification.api.utils.CommanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.function.Supplier;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<String>handleValidateException(ValidationException exception){
        return genericExceptionHandler(exception,
                () -> ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(exception.getMessage()));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException exception){
        return genericExceptionHandler(exception,
                () -> ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(exception.getMessage()));
    }


    public ResponseEntity<String> genericExceptionHandler(final AbstractException exception, final Supplier<ResponseEntity<String>> runner) {

//        used Supplier for Lazy execution
        if(CommanUtils.isNotEmpty(exception.getStatusCode())){
            return ResponseEntity.status(exception.getStatusCode()).body(exception.getErrorMessage());
        }
        return runner.get();
    }
}

/*


 ValidationException / ResourceNotFoundException
           |
           v
  handleXException()
           |
           v
 genericExceptionHandler()
           |
     ----------------
     |              |
statusCode? YES   statusCode? NO
     |              |
     v              v
Custom status     Supplier.run()
     |              |
     v              v
HTTP Response   HTTP Response


 */