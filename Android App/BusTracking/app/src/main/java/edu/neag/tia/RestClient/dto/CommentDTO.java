package edu.neag.tia.RestClient.dto;

public class CommentDTO {

    private String comment;

    public CommentDTO(String comment) {
        this.comment = comment;
    }

    public CommentDTO() {
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
