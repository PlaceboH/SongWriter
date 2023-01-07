package com.songwriter.backend.controllersTesting;

import com.songwriter.backend.dto.MusicWorkDTO;
import com.songwriter.backend.entity.MusicWork;
import com.songwriter.backend.entity.User;
import com.songwriter.backend.entity.enums.ERole;
import com.songwriter.backend.facade.MusicWorkFacade;
import com.songwriter.backend.repository.MusicWorkRepository;
import com.songwriter.backend.services.MusicWorkService;
import com.songwriter.backend.validators.ResponseErrorValidator;
import com.songwriter.backend.web.MusicWorkController;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.security.Principal;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class MusicWorkControlerTests {

    @InjectMocks
    private MusicWorkController musicWorkController;
    @Mock
    private MusicWorkService musicWorkService;
    @Mock
    private MusicWorkFacade mockMusicWorkFacade;
    @Mock
    private MusicWorkRepository musicWorkRepository;
    @Mock
    private Principal principal;
    @Mock
    private ResponseErrorValidator responseErrorValidator;
    private MusicWorkFacade musicWorkFacade = new MusicWorkFacade();
    private MusicWork musicWork;

    private User createTestUser() {
        User user = new User();
        user.setName("Antek");
        user.setUsername("antek@gmail.com");
        user.setLastname("Testerowki");
        user.setId(123L);
        user.setEmail("antek@gmail.com");
        user.setBio("Some Bio");
        user.setPassword("Antek123/");
        user.setRoles(Collections.singleton(ERole.ROLE_USER));

        return user;
    }

    private MusicWork createMusicWork() {
        MusicWork musicWork = new MusicWork();
        musicWork.setId(1000L);
        musicWork.setUser(createTestUser());
        musicWork.setTitle("test music work title");
        musicWork.setCaption("test music work caption");
        musicWork.setChords("Am C G D");
        musicWork.setDescription("test desc");
        musicWork.setLyrics("tet music work lyrics");

        return musicWork;
    }

    @BeforeEach
    void init() {
        musicWork = createMusicWork();
        MusicWorkDTO musicWorkDTO = musicWorkFacade.musicWorkToMusicWorkDTO(musicWork);;
        when(mockMusicWorkFacade.musicWorkToMusicWorkDTO(Mockito.any(MusicWork.class))).thenReturn(musicWorkDTO);
    }

    @Test
    public void testCreateMusicWorkHttp() {
        log.info("Started testing method testCreateMusicWorkHttp");
        // Arrange
        MusicWorkDTO musicWorkDTO = musicWorkFacade.musicWorkToMusicWorkDTO(musicWork);
        BindingResult result = mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(true);
        when(musicWorkService.createMusicWork(Mockito.any(MusicWorkDTO.class),
                Mockito.any(Principal.class))).thenReturn(musicWork);

        // Act
        ResponseEntity<Object> resp = musicWorkController.createMusicWork(musicWorkDTO, result, principal);

        // Assert
        assertEquals(resp.getStatusCode(), HttpStatus.OK);
        assertEquals(resp.getBody(), musicWorkDTO);

        log.info("Finished testing method testCreateMusicWorkHttp");
    }

}
