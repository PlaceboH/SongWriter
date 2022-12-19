package com.songwriter.backend.web;

import com.songwriter.backend.entity.ImageModel;
import com.songwriter.backend.payload.response.MessageResponse;
import com.songwriter.backend.services.ImageUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping("api/image")
public class ImageUploadController {

    @Autowired
    private ImageUploadService imageUploadService;

    @PostMapping("/upload")
    public ResponseEntity<MessageResponse> uploadImageToUser(@RequestParam("imageFile") MultipartFile imageFile,
                                                             Principal principal) throws IOException {
        imageUploadService.uploadImageToUser(imageFile, principal);

        return ResponseEntity.ok(new MessageResponse("Image uploaded successfully"));
    }
    @GetMapping("/profileImage")
    public ResponseEntity<ImageModel> getImageForUser(Principal principal) {
        ImageModel imageModel = imageUploadService.getImageToUser(principal);

        return new ResponseEntity<>(imageModel, HttpStatus.OK);
    }

    @GetMapping("/{userId}/userImage")
    public ResponseEntity<ImageModel> getImageForUser(@PathVariable("userId") String userId) {
        ImageModel imageModel = imageUploadService.getImageToUserById(Long.parseLong(userId));

        return new ResponseEntity<>(imageModel, HttpStatus.OK);
    }
    @PostMapping("/{postId}/postUpload")
    public ResponseEntity<MessageResponse> uploadImageToPost(@PathVariable("postId") String postId,
                                                             @RequestParam("imageFile") MultipartFile imageFile,
                                                             Principal principal) throws IOException {
        imageUploadService.uploadImageToPost(imageFile, principal, Long.parseLong(postId));

        return ResponseEntity.ok(new MessageResponse("Image uploaded successfully"));
    }

    @GetMapping("/{postId}/postImage")
    public ResponseEntity<ImageModel> getImageForPost(@PathVariable("postId") String postId) {
        ImageModel imageModel = imageUploadService.getImageToPost(Long.parseLong(postId));

        return new ResponseEntity<>(imageModel, HttpStatus.OK);
    }

    @PostMapping("/{musicWorkId}/musicWorkUpload")
    public ResponseEntity<MessageResponse> uploadImageToMusicWork(@PathVariable("musicWorkId") String musicWorkId,
                                                             @RequestParam("imageFile") MultipartFile imageFile,
                                                             Principal principal) throws IOException {
        imageUploadService.uploadImageToMusicWork(imageFile, principal, Long.parseLong(musicWorkId));

        return ResponseEntity.ok(new MessageResponse("Image uploaded successfully"));
    }

    @GetMapping("/{musicWorkId}/musicWorkImage")
    public ResponseEntity<ImageModel> getImageForMusicWork(@PathVariable("musicWorkId") String musicWorkId) {
        ImageModel imageModel = imageUploadService.getImageToMusicWork(Long.parseLong(musicWorkId));

        return new ResponseEntity<>(imageModel, HttpStatus.OK);
    }
}
