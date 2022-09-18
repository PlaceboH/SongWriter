package com.songwriter.backend.services;

import com.songwriter.backend.dto.PostDTO;
import com.songwriter.backend.entity.ImageModel;
import com.songwriter.backend.entity.Post;
import com.songwriter.backend.entity.User;
import com.songwriter.backend.entity.enums.ERole;
import com.songwriter.backend.exceptions.PostNotFoundException;
import com.songwriter.backend.repository.ImageRepository;
import com.songwriter.backend.repository.PostRepository;
import com.songwriter.backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    public static final Logger LOG = LoggerFactory.getLogger(PostService.class);
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;

    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository, ImageRepository imageRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.imageRepository = imageRepository;
    }

    public Post createPost(PostDTO postDTO, Principal principal) {
        User user = getUserByPrincipal(principal);
        Post post = new Post();
        post.setUser(user);
        post.setCaption(postDTO.getCaption());
        post.setTitle(postDTO.getTitle());
        post.setLikes(0);

        LOG.info("Saving post for user: {}", user.getUsername());

        return postRepository.save(post);
    }

    public List<Post> getAllPost() {
        return postRepository.findAllByOrderByCreationDateDesc();
    }

    public Post getPostById(Long postId, Principal principal) {
        User user = getUserByPrincipal(principal);

        return postRepository.findPostByIdAndUser(postId, user)
                .orElseThrow(() -> new PostNotFoundException("Post cannot be found username: " + user.getUsername()));
    }

    public List<Post> getAllPostForUser(Principal principal) {
        User user = getUserByPrincipal(principal);

        return postRepository.findAllByUserOrderByCreationDateDesc(user);
    }

    public Post likePost(Long postId, String username) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post cannot be found"));

        Optional<String> userLiked = post.getLikedUsers()
                .stream()
                .filter(user -> user.equals(username)).findAny();
        if (userLiked.isPresent()) {
            post.setLikes(post.getLikes() - 1);
            post.getLikedUsers().remove(username);
        } else {
            post.setLikes(post.getLikes() + 1);
            post.getLikedUsers().add(username);
        }

        return postRepository.save(post);
    }

    public void deletePost(Long postId, Principal principal) {
        Post post = getPostById(postId, principal);
        Optional<ImageModel> imageModel = imageRepository.findByPostId(post.getId());
        postRepository.delete(post);
        imageModel.ifPresent(imageRepository::delete);
    }

    public void deletePostAsAdmin(Long postId, Principal principal) {
        User user = getUserByPrincipal(principal);
        if (user.getRoles().contains(ERole.ROLE_ADMIN)) {
            Post post = postRepository.findPostById(postId)
                    .orElseThrow(() -> new PostNotFoundException("Post " + postId + " cannot be found"));
            postRepository.delete(post);
            LOG.info("Post {} was deleted by user: {}", post.getTitle(), user.getUsername());
        } else {
            LOG.info("Post cannot be deleted, because user: {} is not and Admin", user.getUsername());
        }
    }

    private User getUserByPrincipal(Principal principal) {
        String username = principal.getName();

        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found with username" + username));
    }

}
