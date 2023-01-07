package com.songwriter.backend.facadeTesting;

import com.songwriter.backend.dto.UserDTO;
import com.songwriter.backend.entity.User;
import com.songwriter.backend.facade.UserFacade;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class UserFacadeTest {
    UserFacade userFacade = new UserFacade();

    @Test
    void testUserFacade() {
        log.info("Started testing method testUserFacade");

        // Arrange
        User user = new User();
        user.setId(1000L);
        user.setUsername("Antek");
        user.setName("Antek");
        user.setLastname("Testerowski");
        user.setBio("some bio");

        // Act
        UserDTO userDTO = userFacade.userToUserDTO(user);

        // Assert
        assertNotNull(userDTO);
        assertEquals(user.getUsername(), userDTO.getUsername());
        assertEquals(user.getName(), userDTO.getFirstname());
        assertEquals(user.getLastname(), userDTO.getLastname());
        assertEquals(user.getId(), userDTO.getId());

        log.info("Finished testing method testUserFacade");
    }
}
