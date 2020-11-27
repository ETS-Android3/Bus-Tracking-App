package edu.utcn.gpsm.repository;

import edu.utcn.gpsm.model.security.Role;
import io.swagger.models.auth.In;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
