package com.songwriter.backend.facade;

import com.songwriter.backend.dto.UserDTO;
import com.songwriter.backend.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserFacade {

    public UserDTO userToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFirstname(user.getName());
        userDTO.setLastname(user.getLastname());
        userDTO.setBio(user.getBio());
        userDTO.setUsername(user.getUsername());

        return userDTO;
    }
}
