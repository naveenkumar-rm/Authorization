package com.example.authorization.Controller;

import com.example.authorization.DTO.JWTDTO;
import com.example.authorization.DTO.LoginDTO;
import com.example.authorization.Model.Authmodel;
import com.example.authorization.Security.AuthUtil;
import com.example.authorization.Service.Authserviceimpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/src/main/ImageDir/Images";

    @Autowired
    private Authserviceimpl authService;

    @Autowired
    private AuthUtil authUtil;

    // -------------------- REGISTER --------------------
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Authmodel newUser) {
        String response = authService.createUser(newUser);
        return ResponseEntity.ok(response);
    }

    // -------------------- LOGIN --------------------
    @PostMapping("/login")
    public ResponseEntity<JWTDTO> login(@RequestBody LoginDTO loginDTO) {
        JWTDTO jwtResponse = authService.signinUser(loginDTO.getEmail(), loginDTO.getPassword());
        return ResponseEntity.ok(jwtResponse);
    }

    // -------------------- LOGIN (HASHED VERSION) --------------------
    @PostMapping("/loginHashing")
    public ResponseEntity<JWTDTO> loginWithHashing(@RequestBody JWTDTO userObj) {
        JWTDTO jwtResponse = authService.signinWithHashing(userObj.getEmail(), userObj.getPassword());
        return ResponseEntity.ok(jwtResponse);
    }

    // -------------------- UPDATE USER --------------------
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateUser(
            @RequestHeader("Authorization") String token,
            @PathVariable int id,
            @RequestBody Authmodel updatedUser) {

        // Remove "Bearer " prefix if present
        token = token.replace("Ben", "");

        int tokenUserId = authUtil.extractUserId(token);

        if (tokenUserId != id) {
            return ResponseEntity.status(403)
                    .body("Unauthorized: You can only update your own account.");
        }

        String result = authService.updateUser(id, updatedUser);
        return ResponseEntity.ok(result);
    }

    // -------------------- GET ALL USERS --------------------
    @GetMapping("/all")
    public ResponseEntity<List<Authmodel>> getAllUsers() {
        List<Authmodel> users = authService.getAllUser();
        return ResponseEntity.ok(users);
    }

    // -------------------- GET ONE USER --------------------
    @GetMapping("/{id}")
    public ResponseEntity<Authmodel> getUser(@PathVariable int id) {
        Authmodel user = authService.getUser(id);
        return ResponseEntity.ok(user);
    }

    // -------------------- ADMIN DASHBOARD --------------------
    @GetMapping("/admin/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> adminPage() {
        return ResponseEntity.ok("Welcome, Admin!");
    }

    // -------------------- TUTOR DASHBOARD --------------------
    @GetMapping("/tutor/dashboard")
    @PreAuthorize("hasRole('TUTOR')")
    public ResponseEntity<String> tutorPage() {
        return ResponseEntity.ok("Welcome, Tutor!");
    }
}