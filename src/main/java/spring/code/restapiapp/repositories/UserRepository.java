package spring.code.restapiapp.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import spring.code.restapiapp.models.User;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}