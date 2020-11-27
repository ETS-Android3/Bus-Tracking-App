package edu.utcn.gpsm.repository;

import edu.utcn.gpsm.model.Comment;
import edu.utcn.gpsm.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * author: Bogdan
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    Optional<Comment> findById(final Integer id);

    Comment save(Comment comment);
}
