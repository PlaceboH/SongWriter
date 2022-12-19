package com.songwriter.backend.dto;

import com.songwriter.backend.entity.User;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
public class UserDTO {
    private Long id;
    @NotEmpty
    private String firstname;
    @NotEmpty
    private String lastname;
    private String username;
    private String bio;
}
