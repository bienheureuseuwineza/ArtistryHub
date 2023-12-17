package com.cc.creativecraze.service;

import com.cc.creativecraze.dto.UserDto;
import com.cc.creativecraze.model.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);
    User findUserByEmail(String email);
    List<UserDto> findAllUsers();

}
