package com.example.jwtdemo.controller;

import com.example.jwtdemo.dto.JwtDto;
import com.example.jwtdemo.model.Users;
import com.example.jwtdemo.security.JwtUtil;
import com.example.jwtdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    public static final String UPLOAD_DIR = System.getProperty("user.dir") + "/src/main/resources/images";

    @Autowired
    private UserService service;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public String registerUser(@RequestBody Users newUser) {
        return service.createUser(newUser);
    }

    @PostMapping("/login")
    public JwtDto login(@RequestBody JwtDto userObj) {
        return service.signinWithHashing(userObj.getEmail(), userObj.getPassword());
    }

    @PutMapping("/update/{id}")
    public String updateUser(@RequestHeader("Authorization") String token,
                             @PathVariable int id,
                             @RequestBody Users updatedUser) {
        token = token.replace("Bearer ", "");
        int tokenId = jwtUtil.extractUserId(token);
        if (tokenId != id) {
            return "Unauthorized to update this user";
        }
        return service.updateUser(id, updatedUser);
    }

    @GetMapping("/all")
    public List<Users> getAllUsers() {
        return service.getAllUser();
    }

    @GetMapping("/{id}")
    public Users getOneUser(@PathVariable int id) {
        return service.getUser(id);
    }

    @GetMapping("/admin/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminPage() {
        return "Welcome Admin!";
    }

    @GetMapping("/profile")
    @PreAuthorize("hasRole('USER')")
    public String userPage() {
        return "Welcome User!";
    }

    @PostMapping("/uploadImage")
    public ResponseEntity<String> saveUserWithImage(@ModelAttribute Users user,
                                                    @RequestParam("profileImg") MultipartFile file) throws IOException {
        String filename = file.getOriginalFilename();
        Path filepath = Paths.get(UPLOAD_DIR, filename);
        Files.write(filepath, file.getBytes());
        user.setPhoto(filepath.toString());
        return new ResponseEntity<>(service.saveUserRecord(user), HttpStatus.CREATED);
    }
}
