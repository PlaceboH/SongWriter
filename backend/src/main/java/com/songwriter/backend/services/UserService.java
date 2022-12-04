package com.songwriter.backend.services;

import com.nimbusds.jose.util.Pair;
import com.songwriter.backend.entity.Subscription;
import com.songwriter.backend.repository.ImageRepository;
import com.songwriter.backend.dto.UserDTO;
import com.songwriter.backend.entity.ImageModel;
import com.songwriter.backend.entity.User;
import com.songwriter.backend.entity.enums.ERole;
import com.songwriter.backend.exceptions.UserExistException;
import com.songwriter.backend.payload.request.SignupRequest;
import com.songwriter.backend.repository.SubscriptionRepository;
import com.songwriter.backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Service
@Transactional
public class UserService {
    public static final Logger LOG = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;
    private final SubscriptionRepository subscriptionRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       ImageRepository imageRepository,
                       SubscriptionRepository subscriptionRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.imageRepository = imageRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void createUser(SignupRequest userSignupRequest) {
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
        } catch (Exception ex) {
            LOG.error("Error during registration, {}", ex.getMessage());
            throw new UserExistException("User " + user.getUsername() + " already exist. Please check credentials");
        }
    }

    public User updateUser(UserDTO userDTO, Principal principal) {
        User user = getUserByPrincipal(principal);
        user.setName(userDTO.getFirstname());
        user.setLastname(userDTO.getLastname());
        user.setBio(userDTO.getBio());

        return userRepository.save(user);
    }

    public void follow(Long toFollowUserId, Principal principal) {
        LOG.info("Trying to follow user with id: {}", toFollowUserId);

        User follower = getUserByPrincipal(principal);
        User following = userRepository.findById(toFollowUserId).orElseThrow();
        Subscription subscription = new Subscription(follower,  following);

        subscriptionRepository.save(subscription);

        LOG.info("Success following user with id: {}", toFollowUserId);
    }

    public void unfollow(Long toUnfollowId,  Principal principal) {
        LOG.info("Trying to unfollow user with id: {}", toUnfollowId);

        User follower = getUserByPrincipal(principal);
        User following = userRepository.findById(toUnfollowId).orElseThrow();
        Subscription subscription = subscriptionRepository.findSubscriptionByFollowerAndFollowing(follower, following);

        subscriptionRepository.delete(subscription);

        LOG.info("Success unfollowing user with id: {}", toUnfollowId);
    }

    public List<String> getAllFollowingUsernamesForUser(Long userId) {
        LOG.info("Trying to get list of following users for user id: {}", userId);

        User user = userRepository.findById(userId).orElseThrow();
        List<Subscription> subscriptions = subscriptionRepository.getSubscriptionsByFollower(user);
        List<String> following = new ArrayList<>();
        subscriptions.forEach(s -> following.add((s.getFollowing().getUsername())));

        LOG.info("Success! get list of following users for user id: {}", userId);

        return  following;
    }

    public List<String> getAllFollowersUsernamesForUser(Long userId) {
        LOG.info("Trying to get list of followers for user id: {}", userId);

        User user = userRepository.findById(userId).orElseThrow();
        List<Subscription> subscriptions = subscriptionRepository.getSubscriptionsByFollowing(user);
        List<String> followers = new ArrayList<>();
        subscriptions.forEach(s -> {
            System.out.println(s.getFollower().getUsername());
            followers.add((s.getFollower().getUsername()));
        });

        LOG.info("Success! get list of followers users for user id: {}", userId);

        return  followers;
    }

    @Async
    public CompletableFuture<List<User>> getAllUsers() {
        return CompletableFuture.completedFuture(userRepository.findAllByOrderByCreationDateDesc());
    }
    public User getUserById(long userId) {
        return userRepository.findUserById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
    public User getCurrentUser(Principal principal) {
        return getUserByPrincipal(principal);
    }

    private User getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found with username" + username));
    }

    public void deleteUser(Principal principal) {
        User user = getUserByPrincipal(principal);
        Optional<ImageModel> imageModel = imageRepository.findByUserId(user.getId());
        userRepository.delete(user);
        imageModel.ifPresent(imageRepository::delete);
    }

}
