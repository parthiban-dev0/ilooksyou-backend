package com.ilooksyou.service;

import com.ilooksyou.dto.UserRequest;
import com.ilooksyou.dto.UserResponse;

import java.util.List;
import java.util.Optional;

public interface UserService {

    UserResponse createUser(UserRequest userRequest);

    UserResponse updateUser(UserRequest userRequest, String id);

    List<UserResponse> listUsers();

    Optional<UserResponse> getUserByEmail(String email);
}
