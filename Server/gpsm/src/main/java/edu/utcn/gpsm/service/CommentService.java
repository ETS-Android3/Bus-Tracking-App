package edu.utcn.gpsm.service;

import edu.utcn.gpsm.config.ResourceNotFoundException;
import edu.utcn.gpsm.model.Comment;
import edu.utcn.gpsm.model.dto.CommentDTO;
import edu.utcn.gpsm.model.security.CustomUserDetails;
import edu.utcn.gpsm.repository.CommentRepository;
import edu.utcn.gpsm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * author: Bogdan
 */
@Service
public class CommentService implements CommentServiceIF {
    @Autowired
    private CommentRepository commentRepository;

    @Override
    public Comment addComment(CustomUserDetails customUserDetails, CommentDTO commentDTO) throws ResourceNotFoundException {

        if(Objects.isNull(commentDTO)){
            throw new ResourceNotFoundException("Body is empty!");
        }

        Comment comment = new Comment();

        comment.setUser(customUserDetails.getUser());
        comment.setCreationTime(new Date());
        comment.setComment(String.valueOf(commentDTO.getComment()));

        commentRepository.save(comment);
        return comment;
    }

    @Override
    public List<Comment> getAll() throws ResourceNotFoundException {
        return commentRepository.findAll();
    }
}
