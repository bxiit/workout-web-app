package spring.code.restapiapp.util;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CustomerWithThisUsernameIsAlreadyExists extends RuntimeException{
    public CustomerWithThisUsernameIsAlreadyExists(String message) {
        super(message);
    }
}
