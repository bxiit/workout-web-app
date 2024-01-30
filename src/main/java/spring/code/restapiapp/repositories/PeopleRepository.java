package spring.code.restapiapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.code.restapiapp.models.Person;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
}
