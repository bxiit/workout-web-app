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
import spring.code.restapiapp.dto.UserDTO;
import spring.code.restapiapp.services.UserService;
import spring.code.restapiapp.util.BodyDataNotCreatedException;
import spring.code.restapiapp.util.CustomerNotCreatedException;

import java.util.List;

@RequestMapping("/api")
@Slf4j
@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    private final UserService userService;

    @GetMapping("/welcome")
    public ResponseEntity<String> welcomePage() {
        return ResponseEntity.ok("Welcome");
    }
    @GetMapping("/all-customers")
    public List<UserDTO> getCustomers() {
        return userService.getAllCustomers();
    }

    @PostMapping("/users")
    public ResponseEntity<String> createUser(@RequestBody @Valid UserDTO userDTO,
                                             BindingResult bindingResult) {
        userService.addUser(userService.convertToUser(userDTO));

        return ResponseEntity.ok("User created successfully");
    }

    @GetMapping("users/{username}")
    @PreAuthorize("hasAnyAuthority('USER')")
    public UserDTO getCustomer(@PathVariable("username") String username) {
        return userService.findCustomerByUsername(username);
    }

    @PatchMapping("users/{username}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<HttpStatus> updateCustomer(@PathVariable("username") String username,
                                                     @RequestBody @Valid UserDTO userDTO,
                                                     BindingResult bindingResult) {
        validatingCustomer(bindingResult);
        userService.updateCustomer(username, userService.convertToUser(userDTO));

        return ResponseEntity.ok(HttpStatus.OK);
    }
    @DeleteMapping("users/{username}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<String> deleteUser(@PathVariable("username") String username) {
        userService.deleteCustomer(username);

        return ResponseEntity.ok("User has been deleted successfully");
    }

    @GetMapping("users/{username}/body")
    @PreAuthorize("hasAuthority('USER')")
    public BodyDataDTO getBodyData(@PathVariable("username") String username) {
        return userService.getBodyData(username);
    }

    @PostMapping("users/{username}/body")
    @PreAuthorize("hasAnyAuthority('USER')")
    public ResponseEntity<HttpStatus> newBodyData(@PathVariable("username") String username,
                                                  @RequestBody @Valid BodyDataDTO bodyDataDTO,
                                                  BindingResult bindingResult) {
        validatingBodyData(bindingResult);

        userService.addBodyData(username, userService.convertToBodyData(bodyDataDTO));

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
