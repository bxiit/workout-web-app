package spring.code.restapiapp.util;

public class CustomerNotCreatedException extends RuntimeException{
    public CustomerNotCreatedException(String message) {
        super(message);
    }
}
