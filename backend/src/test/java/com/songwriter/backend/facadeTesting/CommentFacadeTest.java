package com.songwriter.backend.facadeTesting;

import com.songwriter.backend.dto.CommentDTO;
import com.songwriter.backend.entity.Comment;
import com.songwriter.backend.facade.CommentFacade;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
public class CommentFacadeTest {
    CommentFacade commentFacade = new CommentFacade();

    @Test
    void testCommentFacade() {
        log.info("Started testing method testCommentFacade");

        // Arrange
        Comment comment = new Comment();
        comment.setId(1000L);
        comment.setMessage("test comment");
        comment.setUsername("Antek");

        // Act
        CommentDTO commentDTO = commentFacade.commentToCommentDTO(comment);

        // Assert
        assertNotNull(commentDTO);
        assertEquals(comment.getUsername(), commentDTO.getUsername());
        assertEquals(comment.getMessage(), commentDTO.getMessage());
        assertEquals(comment.getId(), commentDTO.getId());

        log.info("Finished testing method testCommentFacade");
    }

}
