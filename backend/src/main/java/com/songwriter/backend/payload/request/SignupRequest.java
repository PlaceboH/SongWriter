package com.songwriter.backend.payload.request;

import com.songwriter.backend.annotations.ValidEmail;
import com.songwriter.backend.annotations.ValidPassword;
import com.songwriter.backend.annotations.ValidPasswordMatches;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@ValidPasswordMatches
public class SignupRequest {

    @Email(message = "Should has email format")
    @NotBlank(message = "User email is required")
    @ValidEmail
    private String email;

    @NotEmpty(message = "Please enter your firstname")
    private String firstname;

    @NotEmpty(message = "Please enter your lastname")
    private String lastname;

    @NotEmpty(message = "Please enter your username")
    private String username;

    @ValidPassword
    @NotEmpty(message = "Password is required")
    private String password;

    private String confirmPassword;
}
