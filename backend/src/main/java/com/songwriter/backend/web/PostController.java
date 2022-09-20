package com.songwriter.backend.web;

import com.songwriter.backend.dto.PostDTO;
import com.songwriter.backend.entity.Post;
import com.songwriter.backend.facade.PostFacade;
import com.songwriter.backend.services.PostService;
import com.songwriter.backend.validators.ResponseErrorValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("api/post")
@CrossOrigin
public class PostController {

    @Autowired
    private PostService postService;
    @Autowired
    private PostFacade postFacade;
    @Autowired
    private ResponseErrorValidator responseErrorValidator;


    @PostMapping("/create")
    public ResponseEntity<Object> createPost(@Valid @RequestBody PostDTO postDTO, BindingResult bindingResult, Principal principal) {
        ResponseEntity<Object> errors = responseErrorValidator.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        Post post = postService.createPost(postDTO, principal);
        PostDTO createPostDTO = postFacade.postToPostDTO(post);

        return new ResponseEntity<>(createPostDTO, HttpStatus.OK);
    }

    @GetMapping("/update")
    public ResponseEntity<Object> updatePost(@Valid @RequestBody PostDTO postDTO, BindingResult bindingResult, Principal principal) {
        ResponseEntity<Object> errors = responseErrorValidator.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        Post post = postService.updatePost(postDTO, principal);
        PostDTO updatePostDTO = postFacade.postToPostDTO(post);

        return new ResponseEntity<>(updatePostDTO, HttpStatus.OK);
    }

}
