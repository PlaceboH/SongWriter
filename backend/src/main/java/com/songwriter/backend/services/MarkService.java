package com.songwriter.backend.services;

import com.songwriter.backend.repository.MarkRepository;
import com.songwriter.backend.repository.MusicWorkRepository;
import com.songwriter.backend.repository.UserRepository;
import com.songwriter.backend.dto.MarkDTO;
import com.songwriter.backend.entity.*;
import com.songwriter.backend.exceptions.MusicWorkNotFoundException;
import com.songwriter.backend.exceptions.PostNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class MarkService {
    public static final Logger LOG = LoggerFactory.getLogger(MarkService.class);

    private final MarkRepository markRepository;
    private final MusicWorkRepository musicWorkRepository;
    private final UserRepository userRepository;

    @Autowired
    public MarkService(MarkRepository markRepository, MusicWorkRepository musicWorkRepository, UserRepository userRepository) {
        this.markRepository = markRepository;
        this.musicWorkRepository = musicWorkRepository;
        this.userRepository = userRepository;
    }

    public Mark saveMark(Long id, MarkDTO markDTO, Principal principal) {
        User user = getUserByPrincipal(principal);
        Mark mark = new Mark();

        MusicWork musicWork = musicWorkRepository.findById(id)
                .orElseThrow(() -> new MusicWorkNotFoundException("Music Work cannot be found for user: " + user.getUsername()));
        mark.setMusicWork(musicWork);
        mark.setUserId(user.getId());
        mark.setUsername(user.getUsername());
        mark.setMessage(markDTO.getMessage());
        mark.setStars(markDTO.getStars());

        LOG.info("Saving mark stars -> {}",  mark.getStars());

        return markRepository.save(mark);
    }

    @Async
    public CompletableFuture<List<Mark>> getAllMarksForMusicWork(Long musicWorkId) {
        MusicWork musicWork = musicWorkRepository.findById(musicWorkId)
                .orElseThrow(() -> new PostNotFoundException("Cannot found any music works"));

        return CompletableFuture.completedFuture(markRepository.findAllByMusicWorkIdOrderByCreationDateDesc(musicWork.getId()));
    }

    public void deleteMark(Long markId) {
        Optional<Mark> mark = markRepository.findById(markId);
        mark.ifPresent(markRepository::delete);
    }

    private User getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found with username" + username));
    }

}
