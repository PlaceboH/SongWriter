package com.songwriter.backend.web;

import com.songwriter.backend.dto.PostDTO;
import com.songwriter.backend.entity.Post;
import com.songwriter.backend.facade.PostFacade;
import com.songwriter.backend.payload.response.MessageResponse;
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
import java.util.List;
import java.util.concurrent.ExecutionException;

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

    @GetMapping("/all")
    public ResponseEntity<List<PostDTO>> getAllPosts() throws ExecutionException, InterruptedException {
        List<PostDTO> postDTOList = postService.getAllPost()
                .get()
                .stream()
                .map(postFacade::postToPostDTO).toList();

        return new ResponseEntity<>(postDTOList, HttpStatus.OK);
    }

    @GetMapping("/user/posts")
    public ResponseEntity<List<PostDTO>> getAllPostsForUser(Principal principal) throws ExecutionException, InterruptedException {
        List<PostDTO> postDTOList = postService.getAllPostForUser(principal)
                .get()
                .stream()
                .map(postFacade::postToPostDTO).toList();

        return new ResponseEntity<>(postDTOList, HttpStatus.OK);
    }

    @PostMapping("/{postId}/{username}/like")
    public ResponseEntity<PostDTO> likePost(@PathVariable("postId") String postId, @PathVariable("username") String username) {
        Post post = postService.likePost(Long.parseLong(postId), username);
        PostDTO postDTO = postFacade.postToPostDTO(post);

        return new ResponseEntity<>(postDTO, HttpStatus.OK);
    }

    @PostMapping("/{postId}/delete")
    public ResponseEntity<MessageResponse> deletePost(@PathVariable("postId") String postId, Principal principal) {
         postService.deletePost(Long.parseLong(postId), principal);

         return new ResponseEntity<>(new MessageResponse("Post was deleted"), HttpStatus.OK);
    }

}
