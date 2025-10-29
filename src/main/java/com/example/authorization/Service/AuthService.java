package com.example.authorization.Service;
import com.example.authorization.DTO.JWTDTO;
import com.example.authorization.DTO.LoginDTO;
import com.example.authorization.Model.Authmodel;
import java.util.List;

public interface AuthService {
        public String createUser(Authmodel newUser);
        public JWTDTO   signinUser(String email,String password);
        public JWTDTO signinWithHashing(String email, String password);
        public List<Authmodel> getAllUser();
        public Authmodel getUser(int id);
        public String updateUser(int id,Authmodel editUser);
        public String deleteUser(int id);
        public String saveUserRecord(Authmodel userObj);
        public Authmodel getUserById(int id);
    }
