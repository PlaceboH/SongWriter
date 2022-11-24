package com.songwriter.backend.dto;

import com.songwriter.backend.entity.enums.EComment;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CommentDTO {
    private Long id;
    @NotEmpty
    private String message;
    private String username;
    private Integer likes;
    @NotEmpty
    private EComment eComment;
}
