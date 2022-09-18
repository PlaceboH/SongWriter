package com.songwriter.backend.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CommentDTO {
    private Long id;
    private String message;
    @NotEmpty
    private String username;
    private Integer likes;
}
