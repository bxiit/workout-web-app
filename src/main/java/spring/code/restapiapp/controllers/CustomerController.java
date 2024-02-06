package spring.code.restapiapp.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import spring.code.restapiapp.dto.CustomerDTO;
import spring.code.restapiapp.models.BodyData;
import spring.code.restapiapp.services.CustomerService;
import spring.code.restapiapp.util.*;

import java.util.List;

@RequestMapping("customers")
@RestController
@AllArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping("/welcome")
    public ResponseEntity<String> welcomePage() {
        return ResponseEntity.ok("welcome page for unauthorized customers");
    }

    @PostMapping("/register/admin")
    public ResponseEntity<String> addUser(@RequestBody @Valid CustomerDTO customerDTO,
                                          BindingResult bindingResult,
                                          @RequestParam("roles") String roles) {
        validatingCustomer(bindingResult);

        customerService.addAdmin(customerService.convertToCustomer(customerDTO), roles);
        return ResponseEntity.ok("Customer saved successfully!");
    }

    @PostMapping("/register/customer")
    public ResponseEntity<String> addUser(@RequestBody @Valid CustomerDTO customerDTO,
                                          BindingResult bindingResult) {
        validatingCustomer(bindingResult);

        customerService.addAdmin(customerService.convertToCustomer(customerDTO));
        return ResponseEntity.ok("Customer saved successfully!");
    }

    @GetMapping("/all-customers")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<CustomerDTO> getCustomers() {
        return customerService.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public CustomerDTO getCustomer(@PathVariable("id") int id) {
        return customerService.findOne(id);
    }


    @PostMapping("/{id}/edit")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<HttpStatus> updateCustomer(@PathVariable("id") int id,
                                                     @RequestBody @Valid CustomerDTO customerDTO,
                                                     BindingResult bindingResult) {
        validatingCustomer(bindingResult);

        customerService.save(id, customerService.convertToCustomer(customerDTO));

        return ResponseEntity.ok(HttpStatus.OK);
    }


    @PostMapping("/{id}/body")
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public ResponseEntity<HttpStatus> createBodyData(@PathVariable("id") int id,
                                                     @RequestBody @Valid BodyData bodyData,
                                                     BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
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
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private void validatingCustomer(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }

            throw new CustomerNotCreatedException(errorMsg.toString());
        }
    }
}
