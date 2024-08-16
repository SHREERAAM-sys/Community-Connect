package com.shree.communityconnect.bean;

import com.shree.communityconnect.constants.Role;
import jakarta.validation.constraints.*;

import java.sql.Timestamp;

public class UserBean {

    private String id;

    @NotBlank(message = "First name should not be blank")
    private String firstName;

    @NotBlank(message = "Last name should not be blank")
    private String lastName;


    @NotBlank(message = "Email should not be blank")
    @Email(message = "Please enter an valid email address")
    private String email;


    @NotBlank(message = "Password should not be blank")
    @Size(min = 8, max = 15)
    private String password;

    @NotNull(message = "Role cannot be null")
    private Role role;

    private Timestamp accountCreated;


    public UserBean() {
    }

    public UserBean(String id, String firstName, String lastName, String email, String password, Role role, Timestamp accountCreated) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.accountCreated = accountCreated;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Timestamp getAccountCreated() {
        return accountCreated;
    }

    public void setAccountCreated(Timestamp accountCreated) {
        this.accountCreated = accountCreated;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", accountCreated=" + accountCreated +
                '}';
    }
}
