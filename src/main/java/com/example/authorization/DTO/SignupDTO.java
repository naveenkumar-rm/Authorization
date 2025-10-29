package com.example.authorization.DTO;

public class SignupDTO {
    String name;
    String Role;
    String Email;
    String Password;


    public SignupDTO() {
    }

    public SignupDTO(String name, String role, String email, String password) {
        this.name = name;
        Role = role;
        Email = email;
        Password = password;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }


}
