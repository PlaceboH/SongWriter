package com.songwriter.backend.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
public class MusicWorkDTO {
    private Long id;
    private String caption;
    private String chords;
    private String description;
    private String lyrics;
    @NotEmpty
    private String title;
    private String username;
    private Integer likes;
    private Set<MarkDTO> markDTOSet;
}
