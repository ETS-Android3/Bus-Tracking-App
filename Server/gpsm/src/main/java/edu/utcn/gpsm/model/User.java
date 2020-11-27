package edu.utcn.gpsm.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.utcn.gpsm.model.security.Role;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import org.springframework.jmx.export.annotation.ManagedNotification;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(hidden = true)
    private Integer id;

    private String firstName;

    private String lastName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false,unique = true)
    private String  email;

    @OneToMany(targetEntity = Position.class,
               orphanRemoval = true,
               cascade = CascadeType.ALL,
               fetch = FetchType.LAZY,
               mappedBy = "user")
    private Set<Position> positions = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name="user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>(0);
//
//    public Set<Position> getPositions() {
//        return positions;
//    }
//
//    public void setPositions(Set<Position> positions) {
//        this.positions = positions;
//    }

    @OneToMany(targetEntity = Comment.class,
    orphanRemoval = true,
    cascade = CascadeType.ALL,
    fetch = FetchType.LAZY,
    mappedBy = "user")
    @JsonIgnore
    private Set<Comment> comments = new HashSet<>();

    public User(){

    }

    public User(String firstName, String lastName, String password, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonIgnore
    @JsonProperty("password")
    public String getHiddenPassword(){
        return "***";
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    @JsonIgnore
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
