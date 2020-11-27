package edu.utcn.gpsm.controller;

import edu.utcn.gpsm.config.ResourceNotFoundException;
import edu.utcn.gpsm.model.Comment;
import edu.utcn.gpsm.model.dto.CommentDTO;
import edu.utcn.gpsm.model.security.CustomUserDetails;
import edu.utcn.gpsm.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * author: Bogdan
 */
@RestController
@RequestMapping("/comment")
@CrossOrigin(origins = "http://localhost:5500")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/addComment")
    public ResponseEntity<Comment> addComment(@RequestBody final CommentDTO commentDTO) throws ResourceNotFoundException{
        final CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(commentService.addComment(customUserDetails, commentDTO));
    }

    @GetMapping("/getAllComments")
    public ResponseEntity<List<Comment>> getAll() throws ResourceNotFoundException{
        return ResponseEntity.ok(commentService.getAll());
    }

}
