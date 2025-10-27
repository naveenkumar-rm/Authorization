package com.example.jwtdemo.repo;

import com.example.jwtdemo.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Integer> {
    Users findByEmail(String email);
}
