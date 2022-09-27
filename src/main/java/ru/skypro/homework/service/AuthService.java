package ru.skypro.homework.service;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.RegisterReq;
import ru.skypro.homework.dto.Role;


public interface AuthService {
    boolean login(String userName, String password);

    boolean register(RegisterReq registerReq, Role role);

    boolean changePassword(String username, String oldPassword, String newPassword);
}
