package spring.code.restapiapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.code.restapiapp.models.BodyData;

public interface BodyDataRepository extends JpaRepository<BodyData, Integer> {
}
