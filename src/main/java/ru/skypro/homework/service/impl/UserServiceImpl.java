package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.skypro.homework.model.dto.*;
import ru.skypro.homework.model.entity.Role;
import ru.skypro.homework.model.entity.User;
import ru.skypro.homework.model.mapper.UserMapper;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.UserService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public User findUserById(int userId) {
        return userRepository.findById(userId).orElse(new User());
    }

    @Override
    public List<User> allUsers() {
        return userRepository.findAll();
    }

    @Override
    public boolean saveUser(User user) {
        if (isPresent(user.getUsername())) {
            return false;
        } else {
            user.setPassword(encoder.encode(user.getPassword()));
            userRepository.saveAndFlush(user);
        }
        return true;
    }

    @Override
    public boolean deleteUser(int userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    @Override
    public boolean isPresent(String username) {
        return userRepository.findUserByEmail(username).isPresent();
    }

    @Override
    public ResponseWrapperUser getUsers() {
        Collection<UserDto> userDtoCollection = userRepository.findAll().stream()
                .map(userMapper::toUserDto)
                .collect(Collectors.toList());
        return new ResponseWrapperUser(userDtoCollection);
    }

    @Override
    public UserDto getUserDto(int id) {
        if (userRepository.findById(id).isPresent()){
            return userMapper.toUserDto(userRepository.findById(id).get());
        }
        return null;
    }

    @Override
    public CreateUser addUser(CreateUser user) {
        logger.info("Call addUser");
        User response = userRepository.save(userMapper.toUser(user));
        return userMapper.toCreateUser(response);
    }

    @Override
    public UserDto updateUser(UserDto user) {
        logger.info("Call update user");
        User updateUser = userMapper.toUser(user);
        if (userRepository.findById(user.getId()).isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        User userAuthorized = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (updateUser.equals(userAuthorized) || userAuthorized.getRole().equals(Role.ADMIN)){
            if (user.getEmail() != null)    updateUser.setEmail(    user.getEmail());
            if (user.getLastName() != null) updateUser.setLastName( user.getLastName());
            if (user.getPhone() != null)    updateUser.setPhone(    user.getPhone());
            userRepository.save(updateUser);

            return userMapper.toUserDto(updateUser);
        }

        throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }

}
