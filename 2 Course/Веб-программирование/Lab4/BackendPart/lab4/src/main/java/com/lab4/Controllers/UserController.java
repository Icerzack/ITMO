package com.lab4.Controllers;

import com.lab4.Repos.RoleRepository;
import com.lab4.DataClasses.UserData;
import com.lab4.Authentification.JWTUtils;
import com.lab4.Models.RoleEnum;
import com.lab4.Models.User;
import com.lab4.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    private final JWTUtils jwtUtils;

    private final PasswordEncoder passwordEncoder;

    private final UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    public UserController(UserService userService, PasswordEncoder passwordEncoder, JWTUtils jwtUtils) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    @CrossOrigin
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> createUser(@RequestBody UserData userData) {
        try {
            if (userService.findByUsername(userData.getUsername()) != null) {
                throw new IllegalArgumentException();
            }
            userService.addUser(new User(
                    userData.getUsername(),
                    passwordEncoder.encode(userData.getPassword()),
                    roleRepository.findByName(RoleEnum.USER.getName())
            ));
            return ResponseEntity.ok().body(generateResponseJSON("New user with username " + userData.getUsername() + " created!"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(generateResponseJSON("This username " + userData.getUsername() + " is already exist!"));
        }

    }

    @CrossOrigin
    @RequestMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody UserData userData) {
        try {
            User userFromDB = userService.findByUsername(userData.getUsername());
            if (userFromDB == null) {
                throw new IllegalArgumentException();
            } else if (!passwordEncoder.matches(userData.getPassword(), userFromDB.getPassword())) {
                throw new IllegalAccessException();
            } else {
                String token = jwtUtils.generateToken(userData.getUsername());
                return ResponseEntity.ok().body(String.format("{\"token\": \"%s\"}", token));
            }
        } catch (IllegalArgumentException | IllegalAccessException e) {
            return ResponseEntity.badRequest().body(generateResponseJSON("Incorrect username or password!"));
        }
    }

    private static String generateResponseJSON(String message){
        return String.format("{\"message\": \"%s\"}", message);
    }

}
