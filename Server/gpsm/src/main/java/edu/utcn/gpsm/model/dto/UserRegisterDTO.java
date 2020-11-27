package edu.utcn.gpsm.model.dto;

public class UserRegisterDTO {

    private String email;
    private String password;
    private String lastName;
    private String firstName;


    public UserRegisterDTO() {
    }

    public UserRegisterDTO(String email, String password, String lastName, String firstName) {
        this.email = email;
        this.password = password;
        this.lastName = lastName;
        this.firstName = firstName;

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
