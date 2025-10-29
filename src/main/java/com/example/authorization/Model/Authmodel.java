package com.example.authorization.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "Institution")
public class Authmodel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int ID;
    String name;
    String Role;
    String Email;
    String Password;


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
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


    public Authmodel() {
    }

    public Authmodel(int ID, String name, String role, String email, String password) {
        this.ID = ID;
        this.name = name;
        Role = role;
        Email = email;
        Password = password;
    }
}
