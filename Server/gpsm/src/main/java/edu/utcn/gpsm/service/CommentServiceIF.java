package edu.utcn.gpsm.service;

import edu.utcn.gpsm.config.ResourceNotFoundException;
import edu.utcn.gpsm.model.Comment;
import edu.utcn.gpsm.model.dto.CommentDTO;
import edu.utcn.gpsm.model.security.CustomUserDetails;

import java.util.List;

/**
 * author: Bogdan
 */
public interface CommentServiceIF {
    Comment addComment(final CustomUserDetails customUserDetails, final CommentDTO commentDTO) throws ResourceNotFoundException;
    List<Comment> getAll() throws ResourceNotFoundException;

}
