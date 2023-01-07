package com.songwriter.backend.facadeTesting;

import com.songwriter.backend.dto.MarkDTO;
import com.songwriter.backend.entity.Mark;
import com.songwriter.backend.facade.MarkFacade;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
@Slf4j
public class MarkFacadeTest {
    MarkFacade markFacade = new MarkFacade();

    @Test
    void testMarkFacade() {
        log.info("Started testing method testMarkFacade");

        // Arrange
        Mark mark = new Mark();
        mark.setId(1000L);
        mark.setMessage("test mark");
        mark.setUsername("Antek");
        mark.setStars(4);

        // Act
        MarkDTO markDTO = markFacade.markToMarkDTO(mark);

        // Assert
        assertNotNull(markDTO);
        assertEquals(mark.getUsername(), markDTO.getUsername());
        assertEquals(mark.getMessage(), markDTO.getMessage());
        assertEquals(mark.getStars(), markDTO.getStars());
        assertEquals(mark.getId(), markDTO.getId());

        log.info("Finished testing method testMarkFacade");
    }
}
