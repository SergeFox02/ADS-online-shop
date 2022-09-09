package ru.skypro.homework.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/users")
public class UserController {

    @PostMapping
    public ResponseEntity<?> addUser(){
        return ResponseEntity.ok("Add users");
    }

    @GetMapping("/me")
    public ResponseEntity<?> getUsers(){
        return ResponseEntity.ok("Get users");
    }

    @PatchMapping("/me")
    public ResponseEntity<?> updateUser(){
        return ResponseEntity.ok("Update user");
    }

    @PostMapping("/set_password")
    public ResponseEntity<?> setPassword(){
        return ResponseEntity.ok("Set password of user");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id){
        return ResponseEntity.ok("Get users with pk = " + id);
    }
}
