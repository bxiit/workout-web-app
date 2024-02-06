package spring.code.restapiapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import spring.code.restapiapp.models.BodyData;

@Getter
@Setter
@AllArgsConstructor // Lombok annotation to generate an all-args constructor
@NoArgsConstructor
public class CustomerDTO {
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    private String username;

    @Min(value = 0, message = "Age can not be negative")
    private Integer age;

    @Email
    private String email;

    private BodyData bodyData;
}
