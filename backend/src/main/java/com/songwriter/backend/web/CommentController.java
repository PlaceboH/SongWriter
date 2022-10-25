package com.songwriter.backend.web;

import com.songwriter.backend.dto.CommentDTO;
import com.songwriter.backend.entity.Comment;
import com.songwriter.backend.entity.enums.EComment;
import com.songwriter.backend.facade.CommentFacade;
import com.songwriter.backend.payload.response.MessageResponse;
import com.songwriter.backend.services.CommentService;
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
@RequestMapping("api/comment")
@CrossOrigin
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private CommentFacade commentFacade;
    @Autowired
    private ResponseErrorValidator responseErrorValidator;

    @PostMapping("/{id}/create")
    public ResponseEntity<Object> createComment(@Valid @RequestBody CommentDTO commentDTO, @PathVariable("id") String id, BindingResult bindingResult, Principal principal) {
        ResponseEntity<Object> errors = responseErrorValidator.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        Comment comment = commentService.saveComment(Long.parseLong(id), commentDTO, principal);
        CommentDTO createCommentDTO = commentFacade.commentToCommentDTO(comment);
        createCommentDTO.setEComment(commentDTO.getEComment());

        return new ResponseEntity<>(createCommentDTO, HttpStatus.OK);
    }

    @GetMapping("/{musicWorkId}/all")
    public ResponseEntity<List<CommentDTO>> getAllMusicWorkComments(@PathVariable("musicWorkId") String musicWorkId) throws ExecutionException, InterruptedException {
        List<CommentDTO> commentDTOList = commentService.getAllCommentsForMusicWork(Long.parseLong(musicWorkId))
                .get()
                .stream()
                .map((comment -> {
                    CommentDTO commentDTO = commentFacade.commentToCommentDTO(comment);
                    commentDTO.setEComment(EComment.COMMENT_MUSIC_WORK);
                    return commentDTO;
                })).toList();

        return new ResponseEntity<>(commentDTOList, HttpStatus.OK);
    }


    @GetMapping("/{postId}/all")
    public ResponseEntity<List<CommentDTO>> getAllPostComments(@PathVariable("postId") String postId) throws ExecutionException, InterruptedException {
        List<CommentDTO> commentDTOList = commentService.getAllCommentsForPost(Long.parseLong(postId))
                .get()
                .stream()
                .map((comment -> {
                    CommentDTO commentDTO = commentFacade.commentToCommentDTO(comment);
                    commentDTO.setEComment(EComment.COMMENT_POST);
                    return commentDTO;
                })).toList();

        return new ResponseEntity<>(commentDTOList, HttpStatus.OK);
    }


    @PostMapping("/{commentId}/delete")
    public ResponseEntity<MessageResponse> deleteComment(@PathVariable("commentId") String commentId) {
        commentService.deleteComment(Long.parseLong(commentId));

        return new ResponseEntity<>(new MessageResponse("Comment was deleted"), HttpStatus.OK);
    }

}
