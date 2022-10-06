package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.model.dto.ResponseWrapperUser;
import ru.skypro.homework.model.dto.UserDto;
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
    public User findUserById(Long userId) {
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
    public boolean deleteUser(Long userId) {
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
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        User user1 = userService.findUserByEmail(user.getEmail()).orElseThrow(() -> new UsernameNotFoundException("lssdlkfj"));
//        List list = new ArrayList<>();
//        list.add(user1);
//        ResponseWrapperUser users = new ResponseWrapperUser(list);
        Collection<UserDto> userDtoCollection = userRepository.findAll().stream()
                .map(user -> userMapper.toUserDto(user))
                .collect(Collectors.toList());
        return new ResponseWrapperUser(userDtoCollection);
    }

    @Override
    public UserDto getUserById(long id) {
        if (userRepository.findById(id).isPresent()){
            return userMapper.toUserDto(userRepository.getById(id));
        }
        return null;
    }

}
