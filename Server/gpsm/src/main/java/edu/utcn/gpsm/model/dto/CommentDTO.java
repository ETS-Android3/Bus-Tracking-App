package edu.utcn.gpsm.model.dto;

import java.util.Date;

/**
 * author: Bogdan
 */
public class CommentDTO {

    private Date creationDate;
    private String comment;

    public CommentDTO(Date creationDate, String comment) {
        this.creationDate = creationDate;
        this.comment = comment;
    }

    public CommentDTO() {
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
