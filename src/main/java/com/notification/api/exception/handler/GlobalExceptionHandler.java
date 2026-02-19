package com.notification.api.exception.handler;

import com.notification.api.exception.AbstractException;
import com.notification.api.exception.ResourceNotFoundException;
import com.notification.api.exception.ValidationException;
import com.notification.api.utils.CommanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * handle method argument not valid
     *
     * @param ex ex
     * @return {@link ResponseEntity}
     * @see ResponseEntity
     * @see Map
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex){
        Map<String,String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach((fieldError) -> errors.put(fieldError.getField(), fieldError.getDefaultMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }


    /**
     * handle validate exception
     *
     * @param exception exception
     * @return {@link ResponseEntity}
     * @see ResponseEntity
     * @see String
     */
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<String>handleValidateException(ValidationException exception){
        return genericExceptionHandler(exception,
                () -> ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(exception.getMessage()));
    }

    /**
     * handle resource not found exception
     *
     * @param exception exception
     * @return {@link ResponseEntity}
     * @see ResponseEntity
     * @see String
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException exception){
        return genericExceptionHandler(exception,
                () -> ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(exception.getMessage()));
    }


    /**
     * generic exception handler
     *
     * @param exception exception
     * @param runner runner
     * @return {@link ResponseEntity}
     * @see ResponseEntity
     * @see String
     */
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