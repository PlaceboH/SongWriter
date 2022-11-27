package com.songwriter.backend.dto;

import com.songwriter.backend.entity.Comment;
import lombok.Data;
import javax.validation.constraints.NotEmpty;

@Data
public class MarkDTO {
    private Long id;
    @NotEmpty
    private Integer stars;
    @NotEmpty
    private String message;
    private String username;
}
