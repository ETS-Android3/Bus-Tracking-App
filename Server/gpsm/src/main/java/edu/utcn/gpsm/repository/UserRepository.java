package edu.utcn.gpsm.repository;

import edu.utcn.gpsm.model.User;
import io.swagger.models.auth.In;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Max;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {



    User findByEmail(final String email);

    Optional<User> findById(final Integer id);

    User save(User user);

}
