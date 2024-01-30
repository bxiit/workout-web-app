package spring.code.restapiapp.util;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PersonErrorResponse {
    private String message;
    private long timestamp;

    public PersonErrorResponse(String message, long timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }

}
