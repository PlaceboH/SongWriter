package com.songwriter.backend.facade;

import com.songwriter.backend.dto.MarkDTO;
import com.songwriter.backend.entity.Mark;
import org.springframework.stereotype.Component;

@Component
public class MarkFacade {
    public MarkDTO markToMarkDTO(Mark mark) {
        MarkDTO markDTO = new MarkDTO();
        markDTO.setId(mark.getId());
        markDTO.setUsername(mark.getUsername());
        markDTO.setStars(mark.getStars());
        markDTO.setMessage(mark.getMessage());

        return markDTO;
    }
}
