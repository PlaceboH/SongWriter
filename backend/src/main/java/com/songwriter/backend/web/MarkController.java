package com.songwriter.backend.web;

import com.songwriter.backend.dto.MarkDTO;
import com.songwriter.backend.entity.Mark;
import com.songwriter.backend.facade.MarkFacade;
import com.songwriter.backend.payload.response.MessageResponse;
import com.songwriter.backend.services.MarkService;
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
@RequestMapping("api/mark")
@CrossOrigin
public class MarkController {

    @Autowired
    private MarkService markService;
    @Autowired
    private MarkFacade markFacade;
    @Autowired
    private ResponseErrorValidator responseErrorValidator;


    @PostMapping("/{id}/create")
    public ResponseEntity<Object> createMark(@Valid @RequestBody MarkDTO markDTO, @PathVariable("id") String id, BindingResult bindingResult, Principal principal) {

        System.out.println("My tutka");
        System.out.println(markDTO.getStars());
        System.out.println(markDTO.getMessage());

        ResponseEntity<Object> errors = responseErrorValidator.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        Mark mark = markService.saveMark(Long.parseLong(id), markDTO, principal);
        MarkDTO createMarkDTO = markFacade.markToMarkDTO(mark);

        return new ResponseEntity<>(createMarkDTO, HttpStatus.OK);
    }

    @GetMapping("/{musicWorkId}/all")
    public ResponseEntity<List<MarkDTO>> getAllMusicWorkComments(@PathVariable("musicWorkId") String musicWorkId) throws ExecutionException, InterruptedException {
        List<MarkDTO> MarkDTOList = markService.getAllMarksForMusicWork(Long.parseLong(musicWorkId))
                .get()
                .stream()
                .map((mark -> {
                    return markFacade.markToMarkDTO(mark);
                })).toList();

        return new ResponseEntity<>(MarkDTOList, HttpStatus.OK);
    }

    @PostMapping("/{markId}/delete")
    public ResponseEntity<MessageResponse> deleteComment(@PathVariable("markId") String markId) {
        markService.deleteMark(Long.parseLong(markId));

        return new ResponseEntity<>(new MessageResponse("Mark was deleted"), HttpStatus.OK);
    }

}
