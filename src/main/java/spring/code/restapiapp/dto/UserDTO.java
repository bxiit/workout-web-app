package spring.code.restapiapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDTO {
    private String username;

    private String password;

    @Email
    private String email;

    @Min(value = 0, message = "Age can not be negative")
    @Max(value = 120, message = "You are too old")
    private Integer age;

    private BodyDataDTO bodyData;
}
