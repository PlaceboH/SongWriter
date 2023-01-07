package com.songwriter.backend.servicesTesting;

import com.songwriter.backend.entity.ImageModel;
import com.songwriter.backend.entity.MusicWork;
import com.songwriter.backend.entity.Post;
import com.songwriter.backend.entity.User;
import com.songwriter.backend.entity.enums.ERole;
import com.songwriter.backend.repository.ImageRepository;
import com.songwriter.backend.repository.UserRepository;
import com.songwriter.backend.services.ImageUploadService;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ImageUploadServiceTest {

    @InjectMocks
    private ImageUploadService imageUploadService;
    @Mock
    private ImageRepository imageRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private Principal principal;

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

        Post post = new Post();
        post.setTitle("post");
        post.setId(1000L);
        post.setCaption("caption");

        user.setPosts(List.of(post));

        MusicWork musicWork = new MusicWork();
        musicWork.setId(1000L);
        musicWork.setTitle("test music work title");
        musicWork.setCaption("test music work caption");
        musicWork.setDescription("test desc");

        user.setMusicWorks(List.of(musicWork));

        return user;
    }

    private ImageModel createImageModal() {
        ImageModel imageModel = new ImageModel();
        imageModel.setId(1000L);
        imageModel.setName("image name");
        imageModel.setImageBytes(HexFormat.of().parseHex("e04fd020ea3a6910a2d808002b30309d"));

        return imageModel;
    }


    @BeforeEach
    void init() {
        Optional<User> optionalUser = Optional.of(createTestUser());
        lenient().when(principal.getName()).thenReturn("antek@gmail.com");
        lenient().when(userRepository.findUserByUsername(Mockito.any(String.class))).thenReturn(optionalUser);
    }

    @Test
    void testUploadImageToUser() throws IOException {
        log.info("Started testing method testUploadImageToUser");
        // Arrange
        when(imageRepository.findByUserId(Mockito.any(Long.class))).thenReturn(Optional.of(createImageModal()));
        MultipartFile multipartFile = new MockMultipartFile("test.png", HexFormat.of().parseHex("e04fd020ea3a6910a2d808002b30309d"));
        // Act
        imageUploadService.uploadImageToUser(multipartFile, principal);

        // Verify
        Mockito.verify(imageRepository, times(1)).save(Mockito.any(ImageModel.class));

        log.info("Finished testing method testUploadImageToUser");
    }

    @Test
    void testUploadImageToPost() throws IOException {
        log.info("Started testing method testUploadImageToPost");
        // Arrange
        when(imageRepository.findByUserId(Mockito.any(Long.class))).thenReturn(Optional.of(createImageModal()));
        MultipartFile multipartFile = new MockMultipartFile("test.png", HexFormat.of().parseHex("e04fd020ea3a6910a2d808002b30309d"));
        // Act
        imageUploadService.uploadImageToPost(multipartFile, principal, 1000L);

        // Verify
        Mockito.verify(imageRepository, times(1)).save(Mockito.any(ImageModel.class));

        log.info("Finished testing method testUploadImageToPost");
    }

    @Test
    void testUploadImageToMusicWork() throws IOException {
        log.info("Started testing method testUploadImageToMusicWork");
        // Arrange
        when(imageRepository.findByUserId(Mockito.any(Long.class))).thenReturn(Optional.of(createImageModal()));
        MultipartFile multipartFile = new MockMultipartFile("test.png", HexFormat.of().parseHex("e04fd020ea3a6910a2d808002b30309d"));
        // Act
        imageUploadService.uploadImageToMusicWork(multipartFile, principal, 1000L);

        // Verify
        Mockito.verify(imageRepository, times(1)).save(Mockito.any(ImageModel.class));

        log.info("Finished testing method testUploadImageToMusicWork");
    }

    @Test
    void testGetImageToUser() {
        log.info("Started testing method testGetImageToUser");
        // Arrange
        ImageUploadService tub = new ImageUploadService(imageRepository, userRepository);
        byte[] imgBytes = HexFormat.of().parseHex("e04fd020ea3a6910a2d808002b30309d");
        ImageModel testImage = createImageModal();
        testImage.setImageBytes(ReflectionTestUtils.invokeMethod(tub, "compressBytes", (Object) imgBytes));
        when(imageRepository.findByUserId(Mockito.any(Long.class))).thenReturn(Optional.of(testImage));

        // Act
        ImageModel imageModel = imageUploadService.getImageToUser(principal);

        // Assert
        assertNotNull(imageModel);
        assertEquals(imageModel.getName(), testImage.getName());
        assertEquals(imageModel.getImageBytes(), testImage.getImageBytes());

        log.info("Finished testing method testGetImageToUser");
    }

    @Test
    void testGetImageToPost() {
        log.info("Started testing method testGetImageToPost");
        // Arrange
        ImageUploadService tub = new ImageUploadService(imageRepository, userRepository);
        byte[] imgBytes = HexFormat.of().parseHex("e04fd020ea3a6910a2d808002b30309d");
        ImageModel testImage = createImageModal();
        testImage.setImageBytes(ReflectionTestUtils.invokeMethod(tub, "compressBytes", (Object) imgBytes));
        when(imageRepository.findByPostId(Mockito.any(Long.class))).thenReturn(Optional.of(testImage));

        // Act
        ImageModel imageModel = imageUploadService.getImageToPost(1000L);

        // Assert
        assertNotNull(imageModel);
        assertEquals(imageModel.getName(), testImage.getName());
        assertEquals(imageModel.getImageBytes(), testImage.getImageBytes());

        log.info("Finished testing method testGetImageToPost");
    }

    @Test
    void testGetImageToMusicWork() {
        log.info("Started testing method testGetImageToMusicWork");
        // Arrange
        ImageUploadService tub = new ImageUploadService(imageRepository, userRepository);
        byte[] imgBytes = HexFormat.of().parseHex("e04fd020ea3a6910a2d808002b30309d");
        ImageModel testImage = createImageModal();
        testImage.setImageBytes(ReflectionTestUtils.invokeMethod(tub, "compressBytes", (Object) imgBytes));
        when(imageRepository.findByMusicWorkId(Mockito.any(Long.class))).thenReturn(Optional.of(testImage));

        // Act
        ImageModel imageModel = imageUploadService.getImageToMusicWork(1000L);

        // Assert
        assertNotNull(imageModel);
        assertEquals(imageModel.getName(), testImage.getName());
        assertEquals(imageModel.getImageBytes(), testImage.getImageBytes());

        log.info("Finished testing method testGetImageToMusicWork");
    }

    @Test
    void testCompressBytes() {
        log.info("Started testing method testCompressBytes");
        // Arrange
        ImageUploadService tub = new ImageUploadService(imageRepository, userRepository);
        byte[] imgBytes = HexFormat.of().parseHex("e04fd020ea3a6910a2d808002b30309d");

        // Act
        byte[] result = ReflectionTestUtils.invokeMethod(tub, "compressBytes", (Object) imgBytes);

        // Assert
        assertNotNull(result);
        log.info("Finished testing method testCompressBytes");
    }

    @Test
    void testDecompressBytes() throws Exception {
        log.info("Started testing method testDecompressBytes");
        // Arrange
        ImageUploadService tub = new ImageUploadService(imageRepository, userRepository);
        byte[] imgBytes = ReflectionTestUtils.invokeMethod(tub, "compressBytes", (Object)  HexFormat.of().parseHex("e04fd020ea3a6910a2d808002b30309d"));

        // Act
        byte[] result = ReflectionTestUtils.invokeMethod(tub, "decompressBytes", (Object) imgBytes);

        // Assert
        assertNotNull(result);
        assertArrayEquals(result,  HexFormat.of().parseHex("e04fd020ea3a6910a2d808002b30309d"));
        log.info("Finished testing method testDecompressBytes");
    }

}
