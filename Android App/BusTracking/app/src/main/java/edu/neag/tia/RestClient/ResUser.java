package edu.neag.tia.RestClient;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResUser {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("lastName")
    @Expose
    private String lastName;

    @SerializedName("firstName")
    @Expose
    private String firstName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
