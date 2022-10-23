package com.songwriter.backend.ControllersTesting;

import com.songwriter.backend.entity.User;
import com.songwriter.backend.entity.enums.ERole;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WebMvcTest
@Slf4j
public class UserControllerTests {

    private MockMvc mockMvc;

    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private Principal principal;
    private User createTestUser() {
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
        Optional<User> optionalUser = Optional.of(createTestUser());
        lenient().when(userRepository.save(Mockito.any(User.class))).thenReturn(Mockito.mock(User.class));
        lenient().when(principal.getName()).thenReturn("antek@gmail.com");
        lenient().when(userRepository.findUserByUsername(Mockito.any(String.class))).thenReturn(optionalUser);
        lenient().when(userRepository.findUserById(Mockito.any(Long.class))).thenReturn(optionalUser);
    }



    @Test
    void getUserHttpRequest() throws Exception {
        User user = createTestUser();

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk()).andReturn();

        ModelAndView mav = mvcResult.getModelAndView();

        verify(userRepository, times(1)).findUserByUsername(Mockito.any(String.class));
//        ModelAndViewAssert.assertViewName(mav.);

    }


}
