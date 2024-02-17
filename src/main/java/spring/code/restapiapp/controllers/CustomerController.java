package spring.code.restapiapp.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import spring.code.restapiapp.dto.BodyDataDTO;
import spring.code.restapiapp.dto.CustomerDTO;
import spring.code.restapiapp.services.CustomerService;
import spring.code.restapiapp.util.*;

import java.util.List;

@RequestMapping("/api")
@Slf4j
@RestController
@AllArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping("/welcome")
    public ResponseEntity<String> welcomePage() {
        return ResponseEntity.ok("welcome page for unauthorized customers");
    }

    @GetMapping("/all-customers")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<CustomerDTO> getCustomers() {
        return customerService.getAllCustomers();
    }

    @PostMapping("/register/customer")
    public ResponseEntity<String> addUser(@RequestBody @Valid CustomerDTO customerDTO,
                                          BindingResult bindingResult) {
        validatingCustomer(bindingResult);
        customerService.addCustomer(customerService.convertToCustomer(customerDTO));

        return ResponseEntity.ok("Customer saved successfully!");
    }

    @GetMapping("/{username}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public CustomerDTO getCustomer(@PathVariable("username") String username) {
        return customerService.findCustomerByUsername(username);
    }


    @PutMapping("/{username}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<HttpStatus> updateCustomer(@PathVariable("username") String username,
                                                     @RequestBody @Valid CustomerDTO customerDTO,
                                                     BindingResult bindingResult) {
        validatingCustomer(bindingResult);
        customerService.updateCustomer(username, customerService.convertToCustomer(customerDTO));

        return ResponseEntity.ok(HttpStatus.OK);
    }


    @GetMapping("/{username}/body")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public BodyDataDTO getBodyData(@PathVariable("username") String username) {
        return customerService.getBodyData(username);
    }

    @PostMapping("/{username}/body")
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public ResponseEntity<HttpStatus> newBodyData(@PathVariable("username") String username,
                                                  @RequestBody @Valid BodyDataDTO bodyDataDTO,
                                                  BindingResult bindingResult) {
        validatingBodyData(bindingResult);

        customerService.addBodyData(username, customerService.convertToBodyData(bodyDataDTO));

        return ResponseEntity.ok(HttpStatus.OK);
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
    private void validatingBodyData(BindingResult bindingResult) {
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
    }
}
