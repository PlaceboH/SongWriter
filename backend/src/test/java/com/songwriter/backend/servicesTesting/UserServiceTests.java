package com.songwriter.backend.servicesTesting;

import com.songwriter.backend.dto.UserDTO;
import com.songwriter.backend.entity.User;
import com.songwriter.backend.entity.enums.ERole;
import com.songwriter.backend.facade.UserFacade;
import com.songwriter.backend.payload.request.SignupRequest;
import com.songwriter.backend.repository.UserRepository;
import com.songwriter.backend.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.Principal;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@Slf4j
class UserServiceTests {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;
    @Mock
    private Principal principal;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncode;
    private final UserFacade userFacade = new UserFacade();

    private SignupRequest registerUser() {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setUsername("antek@gmail.com");
        signupRequest.setEmail("antek@gmail.com");
        signupRequest.setFirstname("Antek");
        signupRequest.setLastname("Testerowki");
        signupRequest.setPassword("Antek1234/");
        signupRequest.setConfirmPassword("Antek1234/");

        return signupRequest;
    }
    private User createUser() {
        User user = new User();
        user.setName("Antek");
        user.setUsername("antek@gmail.com");
        user.setLastname("Testerowki");
        user.setId(123L);
        user.setEmail("antek@gmail.com");
        user.setBio("Some Bio");
        user.setPassword("Antek123/");
        user.setRoles(Collections.singleton(ERole.ROLE_USER));
        return user;
    }

    @BeforeEach
    void init() {
        // Arrange
        Optional<User> optionalUser = Optional.of(createUser());
        lenient().when(userRepository.save(Mockito.any(User.class))).thenReturn(Mockito.mock(User.class));
        lenient().when(principal.getName()).thenReturn("antek@gmail.com");
        lenient().when(userRepository.findUserByUsername(Mockito.any(String.class))).thenReturn(optionalUser);
        lenient().when(userRepository.findUserById(Mockito.any(Long.class))).thenReturn(optionalUser);
    }

    @Test
    void testCreateUserSuccessful() {
        log.info("Started testing method testCreateSubjectSuccessful");
        SignupRequest mockSignupRequest = registerUser();

        userService.createUser(mockSignupRequest);

        verify(userRepository, times(1)).save(Mockito.any(User.class));
        log.info("Finished testing method testCreateSubjectSuccessful");
    }

    @Test
    void testUpdateUserSuccessful() {
        log.info("Started testing method testUpdateUserSuccessful");
        // Arrange
        UserDTO mockUserDTO = userFacade.userToUserDTO(createUser());
        mockUserDTO.setFirstname("Adam");

        // Act
        userService.updateUser(mockUserDTO, principal);

        // Verify
        verify(userRepository, times(1)).save(Mockito.any(User.class));
        verify(userRepository, times(1)).findUserByUsername(Mockito.any(String.class));
        log.info("Finished testing method testUpdateUserSuccessful");
    }

    @Test
    void testGetUserById() {
        log.info("Started testing method testGetUserById");

        User user = userService.getUserById(123L);

        assertEquals("Antek", user.getName());
        assertEquals("antek@gmail.com", user.getUsername());
        assertEquals("antek@gmail.com", user.getEmail());

        verify(userRepository, times(1)).findUserById(Mockito.any(Long.class));
        log.info("Finished testing method testGetUserById");
    }

    @Test
    void testGetUserByUsername() {
        log.info("Started testing method testGetUserByUsername");

        User user = userService.getUserByUsername("antek@gmail.com");

        assertEquals("Antek", user.getName());
        assertEquals("antek@gmail.com", user.getUsername());
        assertEquals("antek@gmail.com", user.getEmail());

        verify(userRepository, times(1)).findUserByUsername(Mockito.any(String.class));
        log.info("Finished testing method testGetUserByUsername");
    }

    @Test
    void testGetCurrentUser() {
        log.info("Started testing method testGetCurrentUser");

        User user = userService.getCurrentUser(principal);

        assertEquals("Antek", user.getName());
        assertEquals("antek@gmail.com", user.getUsername());
        assertEquals("antek@gmail.com", user.getEmail());

        verify(userRepository, times(1)).findUserByUsername(Mockito.any(String.class));
        log.info("Finished testing method testGetCurrentUser");
    }

}
