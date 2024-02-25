package spring.code.restapiapp.registrationModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserRegistrationInfo {
    @JsonProperty("userData")
    private UserData userData;
    @JsonProperty("userGoals")
    private UserGoals userGoals;
    @JsonProperty("bodyCount")
    private BodyCount bodyCount;
}
