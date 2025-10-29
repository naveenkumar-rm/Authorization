package com.example.authorization.Service;

import com.example.authorization.DTO.JWTDTO;
import com.example.authorization.Model.Authmodel;
import com.example.authorization.Repo.AuthRepo;
import com.example.authorization.Security.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class Authserviceimpl implements AuthService {
    @Autowired
    private AuthRepo userRepo;

    @Autowired
    private AuthUtil jwtUtilObj;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public String createUser(Authmodel newUser) {
        String encodedPassword = encoder.encode(newUser.getPassword());
        newUser.setPassword(encodedPassword);
        userRepo.save(newUser);
        return "User registered successfully";
    }

    @Override
    public JWTDTO signinUser(String email, String password) {
        Authmodel user = userRepo.findByEmail(email);
        if (user == null) {
            return new JWTDTO(email, "", "User not found");
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            return new JWTDTO(email, "", "Invalid password");
        }
        String token = jwtUtilObj.generateToken(user);
        return new JWTDTO(email, "", token);
    }

    @Override
    public JWTDTO signinWithHashing(String email, String password) {
        Authmodel user = userRepo.findByEmail(email);
        if (user == null) {
            return new JWTDTO(email, "", "User not found");
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            return new JWTDTO(email, "", "Invalid password");
        }
        String token = jwtUtilObj.generateToken(user);
        return new JWTDTO(email, "", token);
    }

    @Override
    public List<Authmodel> getAllUser() {
        return userRepo.findAll();
    }

    @Override
    public Authmodel getUser(int id) {
        return userRepo.findById(id).orElseThrow(() -> new RuntimeException("No user found"));
    }

    @Override
    public String updateUser(int id, Authmodel editUser) {
        Optional<Authmodel> existingUser = userRepo.findById(id);
        if (existingUser.isPresent()) {
            Authmodel user = existingUser.get();
            user.setName(editUser.getName());
            user.setEmail(editUser.getEmail());
            user.setPassword(encoder.encode(editUser.getPassword()));
            userRepo.save(user);
            return "User updated successfully";
        }
        return "User not found";
    }

    @Override
    public String deleteUser(int id) {
        if (userRepo.existsById(id)) {
            userRepo.deleteById(id);
            return "User deleted successfully";
        }
        return "No user found";
    }

    @Override
    public String saveUserRecord(Authmodel userObj) {
        userRepo.save(userObj);
        return "User saved successfully";
    }

    @Override
    public Authmodel getUserById(int id) {
        return userRepo.findById(id).orElseThrow(() -> new RuntimeException("No user found"));
    }
}
