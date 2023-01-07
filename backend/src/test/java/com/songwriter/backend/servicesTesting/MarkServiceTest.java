package com.songwriter.backend.servicesTesting;

import com.songwriter.backend.dto.MarkDTO;
import com.songwriter.backend.entity.Mark;
import com.songwriter.backend.entity.MusicWork;
import com.songwriter.backend.entity.User;
import com.songwriter.backend.entity.enums.ERole;
import com.songwriter.backend.facade.MarkFacade;
import com.songwriter.backend.repository.MarkRepository;
import com.songwriter.backend.repository.MusicWorkRepository;
import com.songwriter.backend.repository.UserRepository;
import com.songwriter.backend.services.MarkService;
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

import java.security.Principal;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class MarkServiceTest {

    @InjectMocks
    private MarkService markService;

    @Mock
    private MarkRepository markRepository;

    @Mock
    private MusicWorkRepository musicWorkRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private Principal principal;

    private MarkFacade markFacade = new MarkFacade();


    private Mark createMark() {
        Mark mark = new Mark();
        mark.setId(1000L);
        mark.setMessage("test mark init");
        mark.setStars(5);
        mark.setUsername("Antek");
        mark.setUserId(1000L);
        mark.setMusicWork( new MusicWork());

        return mark;
    }
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
        Optional<User> optionalUser = Optional.of(createTestUser());
        lenient().when(principal.getName()).thenReturn("antek@gmail.com");
        lenient().when(userRepository.findUserByUsername(Mockito.any(String.class))).thenReturn(optionalUser);
    }

    @Test
    void testSaveMark() {
        log.info("Started testing method testSaveMark");
        // Arrange
        when(musicWorkRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(createMusicWork()));
        MarkDTO markDTO = markFacade.markToMarkDTO(createMark());

        // Act
        markService.saveMark(1000L, markDTO, principal);

        // Verify
        Mockito.verify(markRepository, times(1)).save(Mockito.any(Mark.class));

        log.info("Finished testing method testSaveMark");
    }


    @Test
    void testGetAllMarksForMusicWork() {
        log.info("Started testing method testGetAllMarksForMusicWork");
        // Arrange
        when(musicWorkRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(createMusicWork()));

        // Act
        markService.getAllMarksForMusicWork(1000L);

        // Verify
        Mockito.verify(markRepository, times(1)).findAllByMusicWorkIdOrderByCreationDateDesc(Mockito.any(Long.class));

        log.info("Finished testing method testGetAllMarksForMusicWork");
    }

    @Test
    void testDeleteMark() {
        log.info("Started testing method testDeleteMark");
        // Arrange
        when(markRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(createMark()));

        // Act
        markService.deleteMark(1000L);

        // Verify
        Mockito.verify(markRepository, times(1)).delete(Mockito.any(Mark.class));

        log.info("Finished testing method testDeleteMark");
    }

}
