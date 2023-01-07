package com.songwriter.backend.servicesTesting;

import com.songwriter.backend.dto.MusicWorkDTO;
import com.songwriter.backend.entity.MusicWork;
import com.songwriter.backend.entity.User;
import com.songwriter.backend.entity.enums.ERole;
import com.songwriter.backend.exceptions.MusicWorkNotFoundException;
import com.songwriter.backend.facade.MusicWorkFacade;
import com.songwriter.backend.repository.MusicWorkRepository;
import com.songwriter.backend.repository.UserRepository;
import com.songwriter.backend.services.MusicWorkService;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@Slf4j
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class MusicWorkServiceTests {

    @InjectMocks
    private MusicWorkService musicWorkService;
    @Mock
    private MusicWorkRepository musicWorkRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private Principal principal;
    private MusicWorkFacade musicWorkFacade = new MusicWorkFacade();

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
    void testCreateMusicWork() {
        log.info("Started testing method testCreateMusicWork");
        // Arrange
        when(musicWorkRepository.findMusicWorkById(Mockito.any(Long.class))).thenReturn(Optional.of(createMusicWork()));
        MusicWorkDTO newMusicWorkDTO = musicWorkFacade.musicWorkToMusicWorkDTO(createMusicWork());

        // Act
        musicWorkService.createMusicWork(newMusicWorkDTO, principal);

        // Verify
        Mockito.verify(musicWorkRepository, times(1)).save(Mockito.any(MusicWork.class));
        log.info("Finished testing method testCreateMusicWork");
    }

    @Test
    void testUpdateMusicWork() {
        log.info("Started testing method testUpdateMusicWork");
        // Arrange
        when(musicWorkRepository.findMusicWorkByIdAndUser(Mockito.any(Long.class),
                Mockito.any(User.class))).thenReturn(Optional.of(createMusicWork()));
        MusicWorkDTO newMusicWorkDTO = musicWorkFacade.musicWorkToMusicWorkDTO(createMusicWork());

        // Act
        musicWorkService.updateMusicWork(newMusicWorkDTO, principal);

        // Verify
        Mockito.verify(musicWorkRepository, times(1)).save(Mockito.any(MusicWork.class));
        log.info("Finished testing method testUpdateMusicWork");
    }


    @Test
    void testDeleteMusicWorkSuccessful() {
        log.info("Started testing method testDeleteMusicWorkSuccessful");
        // Arrange
        when(musicWorkRepository.findMusicWorkByIdAndUser(Mockito.any(Long.class),
                Mockito.any(User.class))).thenReturn(Optional.of(createMusicWork()));
        // Act
        boolean isDeleted = musicWorkService.deleteMusicWork(1000L, principal);

        // Verify and Assert
        assertTrue(isDeleted);
        Mockito.verify(musicWorkRepository, times(1)).delete(Mockito.any(MusicWork.class));
        log.info("Finished testing method testDeleteMusicWorkSuccessful");
    }


    @Test
    void testDeleteMusicWorkUnsuccessful() {
        log.info("Started testing method testDeleteMusicWorkUnsuccessful");
        // Act
        MusicWorkNotFoundException thrown = assertThrows(MusicWorkNotFoundException.class, () -> {
           musicWorkService.deleteMusicWork(1000L, principal);
        });

        // Verify and Assert
        assertEquals("MusicWork cannot be found username: antek@gmail.com", thrown.getMessage());
        Mockito.verify(musicWorkRepository, times(0)).delete(Mockito.any(MusicWork.class));
        log.info("Finished testing method testDeleteMusicWorkUnsuccessful");
    }

    @Test
    void testDeleteMusicWorkAsAdminSuccessful() {
        log.info("Started testing method testDeleteMusicWorkAsAdminSuccessful");
        // Arrange
        User adminUser = createTestUser();
        adminUser.setRoles(Collections.singleton(ERole.ROLE_ADMIN));
        when(userRepository.findUserByUsername(Mockito.any(String.class))).thenReturn(Optional.of(adminUser));
        when(musicWorkRepository.findMusicWorkById(Mockito.any(Long.class))).thenReturn(Optional.of(createMusicWork()));

        // Act
        boolean isDeleted = musicWorkService.deleteMusicWorkAsAdmin(1000L, principal);

        // Verify and Assert
        assertTrue(isDeleted);
        Mockito.verify(musicWorkRepository, times(1)).delete(Mockito.any(MusicWork.class));
        log.info("Finished testing method testDeleteMusicWorkAsAdminSuccessful");
    }

    @Test
    void testDeleteMusicWorkAsAdminUnsuccessful() {
        log.info("Started testing method testDeleteMusicWorkAsAdminUnsuccessful");
        // Arrange
        when(musicWorkRepository.findMusicWorkById(Mockito.any(Long.class))).thenReturn(Optional.of(createMusicWork()));

        // Act
        boolean isDeleted = musicWorkService.deleteMusicWorkAsAdmin(1000L, principal);

        // Verify and Assert
        assertFalse(isDeleted);
        Mockito.verify(musicWorkRepository, times(0)).delete(Mockito.any(MusicWork.class));
        log.info("Finished testing method testDeleteMusicWorkAsAdminUnsuccessful");
    }

    @Test
    void testGetMusicWorkByIdSuccessful() {
        log.info("Started testing method testGetMusicWorkByIdSuccessful");
        // Arrange
        when(musicWorkRepository.findMusicWorkByIdAndUser(Mockito.any(Long.class),
                Mockito.any(User.class))).thenReturn(Optional.of(createMusicWork()));

        // Act
        musicWorkService.getMusicWorkById(1000L, principal);

        // Verify and Assert
        Mockito.verify(musicWorkRepository, times(1)).delete(Mockito.any(MusicWork.class));
        log.info("Finished testing method testGetMusicWorkByIdSuccessful");
    }

    @Test
    void testGetMusicWorkByIdUnsuccessful() {
        log.info("Started testing method testGetMusicWorkByIdUnsuccessful");
        // Act
        MusicWorkNotFoundException thrown = assertThrows(MusicWorkNotFoundException.class, () -> {
            musicWorkService.deleteMusicWork(1000L, principal);
        });

        // Verify and Assert
        assertEquals("MusicWork cannot be found username: antek@gmail.com", thrown.getMessage());
        Mockito.verify(musicWorkRepository, times(1)).findMusicWorkByIdAndUser(Mockito.any(Long.class), Mockito.any(User.class));
        log.info("Finished testing method testGetMusicWorkByIdUnsuccessful");
    }
    
}
