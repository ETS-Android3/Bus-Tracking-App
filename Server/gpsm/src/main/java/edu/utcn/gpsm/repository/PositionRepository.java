package edu.utcn.gpsm.repository;

import edu.utcn.gpsm.model.Position;
import edu.utcn.gpsm.model.User;
import io.swagger.models.auth.In;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface PositionRepository extends JpaRepository<Position, Integer> {

    Position save(Position position);

    Optional<Position> findById(final Integer id);

    void deleteById(final Integer id);

 List<Position> findPositionByTerminalIdAndCreationTimeBetween(String terminalId, Date startDate, Date endDate);


}
