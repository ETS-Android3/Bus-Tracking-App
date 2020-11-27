package edu.neag.tia.RestClient;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class ResComment {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("comment")
    @Expose
    private String comment;

    @SerializedName("creationTime")
    @Expose
    private Date creationTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }
}
