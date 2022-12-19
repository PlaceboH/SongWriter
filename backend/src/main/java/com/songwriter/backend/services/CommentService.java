package com.songwriter.backend.services;

import com.songwriter.backend.dto.CommentDTO;
import com.songwriter.backend.entity.Comment;
import com.songwriter.backend.entity.Post;
import com.songwriter.backend.entity.User;
import com.songwriter.backend.exceptions.PostNotFoundException;
import com.songwriter.backend.repository.CommentRepository;
import com.songwriter.backend.repository.MusicWorkRepository;
import com.songwriter.backend.repository.PostRepository;
import com.songwriter.backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class CommentService {
    public static final Logger LOG = LoggerFactory.getLogger(CommentService.class);
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, PostRepository postRepository, MusicWorkRepository musicWorkRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public Comment saveComment(Long id, CommentDTO commentDTO, Principal principal) {
        User user = getUserByPrincipal(principal);
        Comment comment = new Comment();

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("Post cannot be found for user: " + user.getUsername()));
        comment.setPost(post);
        comment.setUserId(user.getId());
        comment.setUsername(user.getUsername());
        comment.setMessage(commentDTO.getMessage());

        LOG.info("Saving comment {}",comment.getMessage());

        return commentRepository.save(comment);
    }

    @Async
    public CompletableFuture<List<Comment>> getAllCommentsForPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Cannot found any posts"));
        return CompletableFuture.completedFuture(commentRepository.findAllByPost(post));
    }


    public void deleteComment(Long commentId) {
        Optional<Comment> comment = commentRepository.findById(commentId);
        comment.ifPresent(commentRepository::delete);
    }

    private User getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found with username" + username));
    }

}
