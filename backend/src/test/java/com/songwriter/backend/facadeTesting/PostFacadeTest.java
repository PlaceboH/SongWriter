package com.songwriter.backend.facadeTesting;

import com.songwriter.backend.dto.PostDTO;
import com.songwriter.backend.entity.Post;
import com.songwriter.backend.entity.User;
import com.songwriter.backend.facade.PostFacade;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
public class PostFacadeTest {
    PostFacade postFacade = new PostFacade();

    @Test
    void testPostFacade() {
        log.info("Started testing method testPostFacade");

        // Arrange
        User user = new User();
        user.setId(1000L);
        user.setUsername("Antek");
        user.setName("Antek");
        user.setLastname("Testerowski");
        user.setBio("some bio");

        Post post = new Post();
        post.setId(1000L);
        post.setTitle("Test music work");
        post.setCaption("some caption");
        post.setLikes(100);
        post.setLikedUsers(new HashSet<>(Arrays.asList("Antek", "Marek", "Stasiek")));
        post.setUser(user);

        // Act
        PostDTO postDTO = postFacade.postToPostDTO(post);

        // Assert
        assertNotNull(postDTO);
        assertEquals(post.getId(), postDTO.getId());
        assertEquals(post.getTitle(), postDTO.getTitle());
        assertEquals(post.getLikedUsers(), postDTO.getUsersLiked());
        assertEquals(post.getLikes(), postDTO.getLikes());
        assertEquals(post.getUser().getUsername(), postDTO.getUsername());

        log.info("Finished testing method testPostFacade");
    }
}
