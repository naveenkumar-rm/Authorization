package com.example.jwtdemo.service;

import com.example.jwtdemo.dto.JwtDto;
import com.example.jwtdemo.model.Users;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {
    String createUser(Users newUser);
    JwtDto signinWithHashing(String email, String password);
    List<Users> getAllUser();
    Users getUser(int id);
    String updateUser(int id, Users updatedUser);
    String saveUserRecord(Users userObj);
    Users getUserById(int id);
    UserDetails findUser(String email);
}
