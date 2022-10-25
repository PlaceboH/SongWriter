package com.songwriter.backend.web;

import com.songwriter.backend.dto.MusicWorkDTO;
import com.songwriter.backend.entity.MusicWork;
import com.songwriter.backend.facade.MusicWorkFacade;
import com.songwriter.backend.payload.response.MessageResponse;
import com.songwriter.backend.services.MusicWorkService;
import com.songwriter.backend.validators.ResponseErrorValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("api/music-work")
@CrossOrigin
public class MusicWorkController {

    @Autowired
    private MusicWorkService musicWorkService;
    @Autowired
    private MusicWorkFacade musicWorkFacade;
    @Autowired
    private ResponseErrorValidator responseErrorValidator;

    @PostMapping("/create")
    public ResponseEntity<Object> createMusicWork(@Valid @RequestBody MusicWorkDTO musicWorkDTO, BindingResult bindingResult, Principal principal) {
        ResponseEntity<Object> errors = responseErrorValidator.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        MusicWork musicWork = musicWorkService.createMusicWork(musicWorkDTO, principal);
        MusicWorkDTO createMusicWorkDTO = musicWorkFacade.musicWorkToMusicWorkDTO(musicWork);

        return new ResponseEntity<>(createMusicWorkDTO, HttpStatus.OK);
    }

    @GetMapping("/update")
    public ResponseEntity<Object> updateMusicWork(@Valid @RequestBody MusicWorkDTO musicWorkDTO, BindingResult bindingResult, Principal principal) {
        ResponseEntity<Object> errors = responseErrorValidator.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        MusicWork musicWork = musicWorkService.updateMusicWork(musicWorkDTO, principal);
        MusicWorkDTO updateMusicWorkDTO = musicWorkFacade.musicWorkToMusicWorkDTO(musicWork);

        return new ResponseEntity<>(updateMusicWorkDTO, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<MusicWorkDTO>> getAllWorks() throws ExecutionException, InterruptedException {
        List<MusicWorkDTO> musicWorkDTOList = musicWorkService.getAllMusicWork()
                .get()
                .stream()
                .map(musicWorkFacade::musicWorkToMusicWorkDTO).toList();

        return new ResponseEntity<>(musicWorkDTOList, HttpStatus.OK);
    }

    @GetMapping("/user/musicWorks")
    public ResponseEntity<List<MusicWorkDTO>> getAllWorksForUser(Principal principal) throws ExecutionException, InterruptedException {
        List<MusicWorkDTO> musicWorkDTOList = musicWorkService.getAllMusicWorkForUser(principal)
                .get()
                .stream()
                .map(musicWorkFacade::musicWorkToMusicWorkDTO).toList();

        return new ResponseEntity<>(musicWorkDTOList, HttpStatus.OK);
    }


    @PostMapping("/{musicWorkId}/{username}/likeWork")
    public ResponseEntity<MusicWorkDTO> likeMusicWork(@PathVariable("musicWorkId") String musicWorkId, @PathVariable("username") String username) {
        MusicWork musicWork = musicWorkService.likeMusicWork(Long.parseLong(musicWorkId), username);
        MusicWorkDTO musicWorkDTO = musicWorkFacade.musicWorkToMusicWorkDTO(musicWork);

        return new ResponseEntity<>(musicWorkDTO, HttpStatus.OK);
    }

    @PostMapping("/{musicWorkId}/deleteWork")
    public ResponseEntity<MessageResponse> deleteMusicWork(@PathVariable("musicWorkId") String musicWorkId, Principal principal) {
        musicWorkService.deleteMusicWork(Long.parseLong(musicWorkId), principal);

        return new ResponseEntity<>(new MessageResponse("Music work was deleted"), HttpStatus.OK);
    }

}
