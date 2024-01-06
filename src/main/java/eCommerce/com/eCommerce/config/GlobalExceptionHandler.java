package eCommerce.com.eCommerce.config;

import eCommerce.com.eCommerce.exception.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
       List<String> errors = ex.getBindingResult()
               .getFieldErrors()
               .stream()
               .map(FieldError::getDefaultMessage)
               .collect(Collectors.toList());
       return new ResponseEntity<>(getErrorsMap(errors),new HttpHeaders(), HttpStatus.BAD_REQUEST);

    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, List<String>>> handleUserNotFoundException(UserNotFoundException ex) {
        List<String> errors = Collections.singletonList(ex.getMessage());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(UserPaymentNotFoundException.class)
    public ResponseEntity<Map<String,List<String>>> handleUserPaymentNotFoundException(UserPaymentNotFoundException ex){
        List<String> errors = Collections.singletonList(ex.getMessage());
        return new ResponseEntity<>(getErrorsMap(errors),new HttpHeaders(),HttpStatus.NOT_FOUND);

    }
    @ExceptionHandler(DuplicateValueException.class)
    public ResponseEntity<Map<String,List<String>>> handleDuplicateValueException(DuplicateValueException ex){
        List<String> errors = Collections.singletonList(ex.getMessage());
        return new ResponseEntity<>(getErrorsMap(errors),new HttpHeaders(),HttpStatus.BAD_REQUEST);

    }
    @ExceptionHandler(TypeNotFoundException.class)
    public ResponseEntity<Map<String, List<String>>> handleTypeNotFoundException(TypeNotFoundException ex) {
        List<String> errors = Collections.singletonList(ex.getMessage());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Map<String, List<String>>> handleProductNotFoundException(ProductNotFoundException ex) {
        List<String> errors = Collections.singletonList(ex.getMessage());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<Map<String, List<String>>> handleCategoryNotFoundException(CategoryNotFoundException ex) {
        List<String> errors = Collections.singletonList(ex.getMessage());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(SubcategoryNotFoundException.class)
    public ResponseEntity<Map<String, List<String>>> handleSubcategoryNotFoundException(SubcategoryNotFoundException ex) {
        List<String> errors = Collections.singletonList(ex.getMessage());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }


/*    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Map<String, List<String>>> handleGeneralExceptions(Exception ex) {
        List<String> errors = Collections.singletonList(ex.getMessage());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<Map<String, List<String>>> handleRuntimeExceptions(RuntimeException ex) {
        List<String> errors = Collections.singletonList(ex.getMessage());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }*/

    private Map<String,List<String>> getErrorsMap(List<String> errors) {
        Map<String,List<String>> errorResponse = new HashMap<>();
        errorResponse.put("errors",errors);
        return errorResponse;

    }
}
