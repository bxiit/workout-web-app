package spring.code.restapiapp.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import spring.code.restapiapp.models.BodyData;
import spring.code.restapiapp.models.Customer;
import spring.code.restapiapp.services.CustomerService;
import spring.code.restapiapp.util.*;

import java.util.List;

@RequestMapping("/customers")
@RestController
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping()
    public List<Customer> getCustomers() {
        return customerService.findAll();
    }

    @GetMapping("/{id}")
    public Customer getCustomer(@PathVariable("id") int id) {
        return customerService.findOne(id);
    }


    @PostMapping("/{id}/edit")
    public ResponseEntity<HttpStatus> updateCustomer(@PathVariable("id") int id,
                               @RequestBody @Valid Customer customer,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error: errors){
                errorMsg.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }

            throw new CustomerNotCreatedException(errorMsg.toString());
        }

        customerService.save(id, customer);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid Customer customer,
                                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error: errors){
                errorMsg.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }

            throw new CustomerNotCreatedException(errorMsg.toString());
        }

        customerService.save(customer);

        // HTTP response with empty body and status OK (200)
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/{id}/body")
    public ResponseEntity<HttpStatus> createBodyData(@PathVariable("id") int id,
                                                     @RequestBody @Valid BodyData bodyData,
                                                     BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error: errors){
                errorMsg.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }

            throw new BodyDataNotCreatedException(errorMsg.toString());
        }
        customerService.saveBodyData(id, bodyData);

        // HTTP response with empty body and status OK (200)
        return ResponseEntity.ok(HttpStatus.OK);
    }

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
    private ResponseEntity<BodyDataErrorResponse> handleException(BodyDataNotCreatedException exception) {
        BodyDataErrorResponse response = new BodyDataErrorResponse(
                exception.getMessage(), System.currentTimeMillis()
        );
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }
}
