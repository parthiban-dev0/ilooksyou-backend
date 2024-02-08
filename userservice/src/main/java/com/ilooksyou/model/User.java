package com.ilooksyou.model;


import com.ilooksyou.dto.UserRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String fullName;
    private String email;

    @NotBlank(message = "Mobile number is required")
    @Size(min = 10, max = 15, message = "Mobile number should be between 10 and 15 digits")
    private String mobileNumber;

    private String password;

    private String bio;
    private String gender;
    private String image;

    public static User map(UserRequest userRequest){
        User user = new User();
        return map(userRequest,user);
    }

    public static User map(UserRequest userRequest, User user){
        user.setFullName(userRequest.fullName());
        user.setEmail(userRequest.email());
        user.setMobileNumber(userRequest.mobileNumber());
        if(!userRequest.password().isEmpty()){
            user.setPassword(userRequest.password());
        }
        user.setBio(userRequest.bio());
        user.setGender(userRequest.gender());
        return user;
    }
}