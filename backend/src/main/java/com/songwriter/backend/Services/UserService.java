package com.songwriter.backend.Services;

import com.songwriter.backend.entity.User;
import com.songwriter.backend.entity.enums.ERole;
import com.songwriter.backend.exceptions.UserExistException;
import com.songwriter.backend.payload.request.SignupRequest;
import com.songwriter.backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    public static final Logger LOG = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void createUser(SignupRequest userSignupRequest){
        User user = new User();
        user.setEmail(userSignupRequest.getEmail());
        user.setName(userSignupRequest.getFirstname());
        user.setLastname(userSignupRequest.getLastname());
        user.setUsername(userSignupRequest.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(userSignupRequest.getPassword()));
        user.getRoles().add(ERole.ROLE_USER);
        try {
            LOG.info("Saving user {}", userSignupRequest.getUsername());
            userRepository.save(user);
        }catch (Exception ex) {
            LOG.error("Error during registration, {}", ex.getMessage());
            throw new UserExistException("User " + user.getUsername() + " already exist. Please check credentials" );
        }

    }
}
