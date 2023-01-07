package com.songwriter.backend.facadeTesting;

import com.songwriter.backend.dto.MusicWorkDTO;
import com.songwriter.backend.entity.MusicWork;
import com.songwriter.backend.entity.User;
import com.songwriter.backend.facade.MusicWorkFacade;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
public class MusicWorkFacadeTest {
    MusicWorkFacade musicWorkFacade = new MusicWorkFacade();

    @Test
    void testMusicWorkFacade() {
        log.info("Started testing method testMusicWorkFacade");

        // Arrange
        User user = new User();
        user.setId(1000L);
        user.setUsername("Antek");
        user.setName("Antek");
        user.setLastname("Testerowski");
        user.setBio("some bio");

        MusicWork musicWork = new MusicWork();
        musicWork.setId(1000L);
        musicWork.setTitle("Test music work");
        musicWork.setLyrics("Some lyrics");
        musicWork.setChords("Am C D G");
        musicWork.setDescription("some desc");
        musicWork.setCaption("some caption");
        musicWork.setUser(user);

        // Act
        MusicWorkDTO musicWorkDTO = musicWorkFacade.musicWorkToMusicWorkDTO(musicWork);

        // Assert
        assertNotNull(musicWorkDTO);
        assertEquals(musicWork.getId(), musicWorkDTO.getId());
        assertEquals(musicWork.getTitle(), musicWorkDTO.getTitle());
        assertEquals(musicWork.getDescription(), musicWorkDTO.getDescription());
        assertEquals(musicWork.getChords(), musicWorkDTO.getChords());
        assertEquals(musicWork.getLyrics(), musicWorkDTO.getLyrics());
        assertEquals(musicWork.getUser().getUsername(), musicWorkDTO.getUsername());

        log.info("Finished testing method testMusicWorkFacade");
    }

}
