package spring.code.restapiapp.registrationModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserGoals {
    @JsonProperty("userGoals")
    String[] goals;
}