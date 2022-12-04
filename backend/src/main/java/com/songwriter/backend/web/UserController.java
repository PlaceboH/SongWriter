package com.songwriter.backend.web;

import com.nimbusds.jose.util.Pair;
import com.songwriter.backend.dto.UserDTO;
import com.songwriter.backend.entity.User;
import com.songwriter.backend.facade.UserFacade;
import com.songwriter.backend.payload.response.MessageResponse;
import com.songwriter.backend.services.UserService;
import com.songwriter.backend.validators.ResponseErrorValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("api/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserFacade userFacade;
    @Autowired
    private ResponseErrorValidator responseErrorValidator;

    @GetMapping("/")
    public ResponseEntity<UserDTO> getCurrentUser(Principal principal) {
        User user = userService.getCurrentUser(principal);
        UserDTO userDTO = userFacade.userToUserDTO(user);

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserProfile(@PathVariable("userId") String userId) {
        User user = userService.getUserById(Long.parseLong(userId));
        UserDTO userDTO = userFacade.userToUserDTO(user);

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }


    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getAllUsers() throws ExecutionException, InterruptedException {
        List<UserDTO> userDTOList = userService.getAllUsers()
                .get()
                .stream()
                .map(userFacade::userToUserDTO).toList();

        return new ResponseEntity<>(userDTOList, HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<Object> updateUser(@Valid @RequestBody UserDTO userDTO, BindingResult bindingResult, Principal principal) {
        ResponseEntity<Object> errors = responseErrorValidator.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        User user = userService.updateUser(userDTO, principal);
        UserDTO userDTOUpdated = userFacade.userToUserDTO(user);

        return new ResponseEntity<>(userDTOUpdated, HttpStatus.OK);
    }


    @PostMapping("/{userId}/follow")
    public  void followUser(@PathVariable("userId") String userId, Principal principal) {
        userService.follow(Long.parseLong(userId), principal);
    }

    @PostMapping("/{userId}/unfollow")
    public void unfollowUser(@PathVariable("userId") String userId, Principal principal) {
        userService.unfollow(Long.parseLong(userId), principal);
    }

    @GetMapping("/{userId}/following")
    public  ResponseEntity<List<String>> getFollowingUsers(@PathVariable("userId") String userId) {
        return new ResponseEntity<>(userService.getAllFollowingUsernamesForUser(Long.parseLong(userId)), HttpStatus.OK);
    }

    @GetMapping("/{userId}/followers")
    public  ResponseEntity<List<String>> getUserFollowers(@PathVariable("userId") String userId) {
        return new ResponseEntity<>(userService.getAllFollowersUsernamesForUser(Long.parseLong(userId)), HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity<MessageResponse> deleteUser(Principal principal) {
        userService.deleteUser(principal);

        return new ResponseEntity<>(new MessageResponse("User was deleted"), HttpStatus.OK);
    }

}
