package com.example.jwtdemo.service;

import com.example.jwtdemo.dto.JwtDto;
import com.example.jwtdemo.model.Users;
import com.example.jwtdemo.repo.UserRepository;
import com.example.jwtdemo.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private JwtUtil jwtUtil;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public String createUser(Users newUser) {
        newUser.setPassword(encoder.encode(newUser.getPassword()));
        repo.save(newUser);
        return "User registered successfully";
    }

    @Override
    public JwtDto signinWithHashing(String email, String password) {
        Users user = repo.findByEmail(email);
        if (user == null || !encoder.matches(password, user.getPassword())) {
            return new JwtDto(email, "", "Login failed");
        }
        String token = jwtUtil.generateToken(user);
        return new JwtDto(email, "", token);
    }

    @Override
    public List<Users> getAllUser() {
        return repo.findAll();
    }

    @Override
    public Users getUser(int id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("No user found"));
    }

    @Override
    public String updateUser(int id, Users updatedUser) {
        Optional<Users> optionalUser = repo.findById(id);
        if (optionalUser.isPresent()) {
            Users user = optionalUser.get();
            user.setName(updatedUser.getName());
            user.setEmail(updatedUser.getEmail());
            user.setPassword(encoder.encode(updatedUser.getPassword()));
            repo.save(user);
            return "User updated successfully";
        }
        return "User not found";
    }

    @Override
    public String saveUserRecord(Users userObj) {
        repo.save(userObj);
        return "User saved successfully";
    }

    @Override
    public Users getUserById(int id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("No user found"));
    }

    @Override
    public UserDetails findUser(String email) throws UsernameNotFoundException {
        Users user = repo.findByEmail(email);
        if (user == null) throw new UsernameNotFoundException("User not found");
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(),
                List.of(new SimpleGrantedAuthority(user.getRole()))
        );
    }
}
