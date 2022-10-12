package ru.skypro.homework.service;

import ru.skypro.homework.model.dto.*;
import ru.skypro.homework.model.entity.Role;
import org.springframework.stereotype.Service;



public interface AuthService {
    boolean login(String userName, String password);

    boolean register(RegisterReq registerReq);

    boolean changePassword(String username, String oldPassword, String newPassword);
}
