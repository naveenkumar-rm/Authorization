package com.example.authorization.DTO;

public class JWTDTO {

    String Email;
    String Password;
    String Token;

    public JWTDTO(String email, String password, String token) {
        Email = email;
        Password = password;
        Token = token;
    }


    public JWTDTO() {
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

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }


}
