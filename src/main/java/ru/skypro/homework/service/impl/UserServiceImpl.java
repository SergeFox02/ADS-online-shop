package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.skypro.homework.model.dto.*;
import ru.skypro.homework.model.entity.User;
import ru.skypro.homework.model.mapper.UserMapper;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.UserService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service for working with Users
 */

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

    /**
     * Get all users
     *
     * @return all users
     */
    @Override
    public ResponseWrapperUser getUsers() {
        Collection<UserDto> userDtoCollection = userRepository.findAll().stream()
                .map(userMapper::toUserDto)
                .collect(Collectors.toList());
        return new ResponseWrapperUser(userDtoCollection);
    }

    /**
     * Get userDto by id
     *
     * @param id of user
     * @return userDto
     * @throws ResponseStatusException {@code HttpStatus.NOT_FOUND} if ads not found
     */
    @Override
    public UserDto getUserDto(int id) {
        if (userRepository.findById(id).isPresent()){
            return userMapper.toUserDto(userRepository.findById(id).get());
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    /**
     * Add new user
     *
     * @param user new user
     * @return adding user
     */
    @Override
    public CreateUser addUser(CreateUser user) {
        logger.info("Call addUser");
        User response = userRepository.save(userMapper.toUser(user));
        return userMapper.toCreateUser(response);
    }

    /**
     * Update user
     *
     * @param userDto new date obout user
     * @return updating user
     */
    @Override
    public UserDto updateUser(UserDto userDto) {
        logger.info("Call update user");
        User updateUser = userMapper.toUser(userDto);
        User response = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (updateUser.getFirstName() != null) response.setFirstName(updateUser.getFirstName());
        if (updateUser.getLastName() != null) response.setLastName(updateUser.getLastName());
        if (updateUser.getPhone() != null) response.setPhone(updateUser.getPhone());
        userRepository.save(response);

        return userMapper.toUserDto(response);
    }

    /**
     * Set new password oj user
     *
     * @param newPassword new password
     * @return new password
     * @throws ResponseStatusException {@code HttpStatus.NOT_FOUND} if ads or comment not found
     * @throws ResponseStatusException return {@code  HttpStatus.FORBIDDEN} if user is not Admin or not enough rights odf user
     */
    @Override
    public NewPassword setPassword(NewPassword newPassword) {
        logger.info("Call update setPassword");
        User userAuthorized = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userRepository.findById(userAuthorized.getId()).isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(newPassword.getCurrentPassword(), userAuthorized.getPassword())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        String newPass = passwordEncoder.encode(newPassword.getNewPassword());
        userAuthorized.setPassword(newPass);
        userRepository.save(userAuthorized);
        return newPassword;
    }

}
