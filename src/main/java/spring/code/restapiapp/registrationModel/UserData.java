package spring.code.restapiapp.registrationModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import spring.code.restapiapp.models.Role;

import java.time.LocalDateTime;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document
public class UserData {
    @Id
    private String username;

    private String email;

    private String password;

    private Set<Role> role;
}
