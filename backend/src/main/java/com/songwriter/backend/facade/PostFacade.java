package com.songwriter.backend.facade;

import com.songwriter.backend.dto.PostDTO;
import com.songwriter.backend.entity.Post;
import org.springframework.stereotype.Component;

@Component
public class PostFacade {

    public PostDTO postToPostDTO(Post post) {
        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setUsername(post.getUser().getUsername());
        postDTO.setTitle(post.getTitle());
        postDTO.setCaption(post.getCaption());
        postDTO.setLikes(post.getLikes());
        postDTO.setUsersLiked(post.getLikedUsers());


        return postDTO;
    }
}
