package com.songwriter.backend.controllersTesting;

import com.google.common.collect.Lists;
import com.songwriter.backend.dto.UserDTO;
import com.songwriter.backend.entity.User;
import com.songwriter.backend.entity.enums.ERole;
import com.songwriter.backend.facade.UserFacade;
import com.songwriter.backend.repository.UserRepository;
import com.songwriter.backend.services.UserService;
import com.songwriter.backend.validators.ResponseErrorValidator;
import com.songwriter.backend.web.UserController;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


@Slf4j
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class UserControllerTests {
    @InjectMocks
    private UserController userController;
    @Mock
    private UserService userService;
    @Mock
    private UserFacade mockUserFacade;
    @Mock
    private UserRepository userRepository;
    @Mock
    private Principal principal;

    @Mock
    private ResponseErrorValidator responseErrorValidator;

    private UserFacade userFacade = new UserFacade();
    private User user;

    private User createTestUser() {
        User user = new User();
        user.setName("Antek");
        user.setUsername("antek@gmail.com");
        user.setLastname("Testerowki");
        user.setId(1000L);
        user.setEmail("antek@gmail.com");
        user.setBio("Some Bio");
        user.setPassword("Antek123/");
        user.setRoles(Collections.singleton(ERole.ROLE_USER));

        return user;
    }

    @BeforeEach
    void init() {
        // Arrange
        user = createTestUser();
        UserDTO userDTO = userFacade.userToUserDTO(user);
        Optional<User> optionalUser = Optional.of(user);
        lenient().when(principal.getName()).thenReturn("antek@gmail.com");
        lenient().when(mockUserFacade.userToUserDTO(Mockito.any(User.class))).thenReturn(userDTO);
        lenient().when(userRepository.save(Mockito.any(User.class))).thenReturn(Mockito.mock(User.class));
        lenient().when(userRepository.findUserByUsername(Mockito.any(String.class))).thenReturn(optionalUser);
        lenient().when(userRepository.findUserById(Mockito.any(Long.class))).thenReturn(optionalUser);
        lenient().when(responseErrorValidator.mapValidationService(Mockito.any(BindingResult.class))).thenReturn(null);
    }

    @Test
    void getUserHttpRequest() {
        log.info("Started testing method getUserHttpRequest");
        // Arrange
        when(userService.getCurrentUser(Mockito.any(Principal.class))).thenReturn(user);

        // Act
        ResponseEntity<UserDTO> resp = userController.getCurrentUser(principal);

        // Assert
        assertEquals(resp.getStatusCode(), HttpStatus.OK);
        assertEquals(resp.getBody().getUsername(), user.getUsername());
        log.info("Finished testing method getUserHttpRequest");
    }

    @Test
    void getUserProfileHttpRequest() {
        log.info("Started testing method getUserProfileHttpRequest");
        // Arrange
        when(userService.getUserById(Mockito.any(Long.class))).thenReturn(user);

        // Act
        ResponseEntity<UserDTO> resp = userController.getUserProfile(user.getId().toString());

        // Assert
        assertEquals(resp.getStatusCode(), HttpStatus.OK);
        assertEquals(resp.getBody().getUsername(), user.getUsername());
        log.info("Finished testing method getUserProfileHttpRequest");
    }

    @Test
    void getUserByUsernameHttpRequest() {
        log.info("Started testing method getUserByUsernameHttpRequest");
        // Arrange
        when(userService.getUserById(Mockito.any(Long.class))).thenReturn(user);

        // Act
        ResponseEntity<UserDTO> resp = userController.getUserProfile(user.getId().toString());

        // Assert
        assertEquals(resp.getStatusCode(), HttpStatus.OK);
        assertEquals(resp.getBody().getUsername(), user.getUsername());
        log.info("Finished testing method getUserByUsernameHttpRequest");
    }

    @Test
    void getAllUsersHttpRequest() throws ExecutionException, InterruptedException {
        log.info("Started testing method getUserByUsernameHttpRequest");
        // Arrange
        CompletableFuture<List<User>> users = CompletableFuture.supplyAsync(() -> Lists.newArrayList(user));
        when(userService.getAllUsers()).thenReturn(users);

        // Act
        ResponseEntity<List<UserDTO>> resp = userController.getAllUsers();

        // Assert
        assertEquals(resp.getStatusCode(), HttpStatus.OK);
        assertEquals(resp.getBody().get(0).getUsername(), user.getUsername());

        log.info("Finished testing method getUserByUsernameHttpRequest");
    }

    @Test
    void updateUserTest() {
        log.info("Started testing method updateUserTest");
        // Arrange
        UserDTO userDTO = userFacade.userToUserDTO(user);
        BindingResult result = mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(true);
        when(userService.updateUser(Mockito.any(UserDTO.class), Mockito.any(Principal.class))).thenReturn(user);

        // Act
        ResponseEntity<Object> resp = userController.updateUser(userDTO, result, principal);

        // Assert
        assertEquals(resp.getStatusCode(), HttpStatus.OK);
        log.info("Finished testing method updateUserTest");
    }

}
