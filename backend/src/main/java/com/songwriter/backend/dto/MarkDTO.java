package com.songwriter.backend.dto;

import lombok.Data;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class MarkDTO {
    private Long id;
    @NotNull
    private Integer stars;
    @NotEmpty
    private String message;
    private String username;
}
