package com.example.authorization.Repo;

import com.example.authorization.Model.Authmodel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepo extends JpaRepository<Authmodel,Integer> {
    AuthRepo findByEmailAndPassword(String email,String Password);
    Authmodel findByEmail(String email);

}
