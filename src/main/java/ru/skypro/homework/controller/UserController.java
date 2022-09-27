package ru.skypro.homework.controller;


import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.Model.User;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.service.UserService;

import java.util.logging.Logger;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/users")
public class UserController {

    Logger logger = Logger.getLogger(String.valueOf(UserController.class));

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService,
                          UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping
    public ResponseEntity<?> addUser(){
        return ResponseEntity.ok("Add users");
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> getUsers(Authentication auth) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("User not found!"));
        logger.info("Get me method called");
        return ResponseEntity.ok(userMapper.userToDtoMapper(user));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PatchMapping("/me")
    public ResponseEntity<UserDto> updateUser(Authentication auth, @RequestBody UserDto userDto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("User not found!"));
        user.setPhone(userDto.getPhone());
        user.setLastName(userDto.getLastName());
        user.setFirstName(userDto.getFirstName());
        User updatedUser = userService.updateUser(user);

        return ResponseEntity.ok(userMapper.userToDtoMapper(updatedUser));
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
