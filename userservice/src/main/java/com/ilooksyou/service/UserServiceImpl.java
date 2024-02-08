package com.ilooksyou.service;

import com.ilooksyou.dto.UserRequest;
import com.ilooksyou.dto.UserResponse;
import com.ilooksyou.model.User;
import com.ilooksyou.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserResponse createUser(UserRequest userRequest) {
        User user = User.map(userRequest);
        User savedUser = userRepository.save(user);
        return UserResponse.map(savedUser);
    }

    @Override
    public UserResponse updateUser(UserRequest userRequest, String id) {
        return null;
    }

    @Override
    public List<UserResponse> listUsers() {
        return userRepository.findAll().stream().map(UserResponse::map).toList();
    }

    @Override
    public Optional<UserResponse> getUserByEmail(String email) {
        return userRepository.findByEmail(email).map(UserResponse::map);
    }
}
