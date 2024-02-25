package spring.code.restapiapp.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document
public class User {
    @Id
    private String id;

    private String username;

    private Integer age;

    private BodyData bodyData;

    private String email;

    private String password;

    private LocalDateTime updated;

    private Role roles;
}
