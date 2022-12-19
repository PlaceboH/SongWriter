package com.songwriter.backend.facade;

import com.songwriter.backend.dto.MusicWorkDTO;
import com.songwriter.backend.entity.MusicWork;
import org.springframework.stereotype.Component;

@Component
public class MusicWorkFacade {
    public MusicWorkDTO musicWorkToMusicWorkDTO(MusicWork musicWork) {
        MusicWorkDTO musicWorkDTO = new MusicWorkDTO();
        musicWorkDTO.setId(musicWork.getId());
        musicWorkDTO.setUsername(musicWork.getUser().getUsername());
        musicWorkDTO.setTitle(musicWork.getTitle());
        musicWorkDTO.setChords(musicWork.getChords());
        musicWorkDTO.setDescription(musicWork.getDescription());
        musicWorkDTO.setCaption(musicWork.getCaption());
        musicWorkDTO.setLyrics(musicWork.getLyrics());

        return musicWorkDTO;
    }
}
