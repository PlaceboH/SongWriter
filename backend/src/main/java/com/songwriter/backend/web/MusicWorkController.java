package com.songwriter.backend.web;

import com.songwriter.backend.dto.MusicWorkDTO;
import com.songwriter.backend.entity.MusicWork;
import com.songwriter.backend.facade.MusicWorkFacade;
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

}
