package com.ilooksyou.dto;

import com.ilooksyou.model.User;

public record UserResponse(String Id, String fullName, String email, String password, String mobileNumber, String bio, String gender, String image) {

    public static UserResponse map(User user){
        return new UserResponse(user.getId(),user.getFullName(), user.getEmail(), user.getPassword(), user.getMobileNumber(), user.getBio(), user.getGender(), user.getImage());
    }
}
