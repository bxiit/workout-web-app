package spring.code.restapiapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import spring.code.restapiapp.util.*;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    private ResponseEntity<CustomerErrorResponse> handleException(CustomerNotFoundException exception) {
        CustomerErrorResponse response = new CustomerErrorResponse(
                "Person with this id does not exist",
                System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<CustomerErrorResponse> handleException(CustomerNotCreatedException exception) {
        CustomerErrorResponse response = new CustomerErrorResponse(
                exception.getMessage(),
                System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<CustomerErrorResponse> handleException(NotCurrentCustomerException exception) {
        CustomerErrorResponse response = new CustomerErrorResponse(
                exception.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<BodyDataErrorResponse> handleException(BodyDataNotCreatedException exception) {
        BodyDataErrorResponse response = new BodyDataErrorResponse(
                exception.getMessage(), System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<CustomerErrorResponse> handleException(CustomerWithThisUsernameIsAlreadyExists exception) {
        CustomerErrorResponse response = new CustomerErrorResponse(
                exception.getMessage(), System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
