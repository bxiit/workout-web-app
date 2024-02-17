package spring.code.restapiapp.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;
import spring.code.restapiapp.models.BodyData;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CustomerDTO {
    private String username;

    private String password;

    @Email
    private String email;

    @Min(value = 0, message = "Age can not be negative")
    @Max(value = 120, message = "You are too old")
    private Integer age;

    private BodyDataDTO bodyData;
}
