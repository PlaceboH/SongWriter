package com.songwriter.backend.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Set;
@Data
public class PostDTO {
    private Long id;
    @NotEmpty
    private String title;
    private String caption;
    private String username;
    private Integer likes;
    private Set<String> usersLiked;
    private List<CommentDTO> comments;
}
