package com.songwriter.backend.services;

import com.songwriter.backend.dto.MusicWorkDTO;
import com.songwriter.backend.entity.MusicWork;
import com.songwriter.backend.entity.User;
import com.songwriter.backend.entity.enums.ERole;
import com.songwriter.backend.exceptions.MusicWorkNotFoundException;
import com.songwriter.backend.repository.MusicWorkRepository;
import com.songwriter.backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class MusicWorkService {
    public static final Logger LOG = LoggerFactory.getLogger(MusicWorkService.class);
    public final MusicWorkRepository musicWorkRepository;
    public final UserRepository userRepository;
    @Autowired
    public MusicWorkService(MusicWorkRepository musicWorkRepository, UserRepository userRepository) {
        this.musicWorkRepository = musicWorkRepository;
        this.userRepository = userRepository;
    }

    public MusicWork createMusicWork(MusicWorkDTO musicWorkDTO, Principal principal) {
        User user = getUserByPrincipal(principal);
        MusicWork musicWork = new MusicWork();
        musicWork.setUser(user);
        musicWork.setCaption(musicWorkDTO.getCaption());
        musicWork.setTitle(musicWorkDTO.getTitle());
        musicWork.setChords(musicWorkDTO.getChords());
        musicWork.setLyrics(musicWorkDTO.getLyrics());
        musicWork.setDescription(musicWorkDTO.getDescription());

        LOG.info("Saving musicWork for user: {}", user.getUsername());

        return musicWorkRepository.save(musicWork);
    }

    public MusicWork updateMusicWork(MusicWorkDTO musicWorkDTO, Principal principal) {
        MusicWork musicWork = getMusicWorkById(musicWorkDTO.getId(), principal);
        musicWork.setTitle(musicWorkDTO.getTitle());
        musicWork.setChords(musicWorkDTO.getChords());
        musicWork.setLyrics(musicWorkDTO.getLyrics());
        musicWork.setDescription(musicWorkDTO.getDescription());

        return musicWorkRepository.save(musicWork);
    }

    @Async
    public CompletableFuture<List<MusicWork>> getAllMusicWork() {
        return CompletableFuture.completedFuture(musicWorkRepository.findAllByOrderByCreationDateDesc());
    }

    public MusicWork getMusicWorkById(Long musicWorkId, Principal principal) {
        User user = getUserByPrincipal(principal);

        return musicWorkRepository.findMusicWorkByIdAndUser(musicWorkId, user)
                .orElseThrow(() -> new MusicWorkNotFoundException("MusicWork cannot be found username: " + user.getUsername()));
    }

    @Async
    public CompletableFuture<List<MusicWork>> getAllMusicWorkForUser(Principal principal) {
        User user = getUserByPrincipal(principal);

        return CompletableFuture.completedFuture(musicWorkRepository.findAllByUserOrderByCreationDateDesc(user));
    }
    @Async
    public CompletableFuture<List<MusicWork>> getAllWorksForChosenUser(Long userId, Principal principal) {
        User user = userRepository.findUserById(userId)
                .orElseThrow(() -> new MusicWorkNotFoundException("Username cannot be found"));

//        User subscribedUser = userRepository.findUserById(userId)
//                .orElseThrow(() -> new MusicWorkNotFoundException("Username cannot be found"));
        return CompletableFuture.completedFuture(musicWorkRepository.findAllByUserOrderByCreationDateDesc(user));
    }


    public void deleteMusicWork(Long musicWorkId, Principal principal) {
        MusicWork musicWork = getMusicWorkById(musicWorkId, principal);
        musicWorkRepository.delete(musicWork);
    }

    public void deleteMusicWorkAsAdmin(Long musicWorkId, Principal principal) {
        User user = getUserByPrincipal(principal);
        if (user.getRoles().contains(ERole.ROLE_ADMIN)) {
            MusicWork musicWork = musicWorkRepository.findMusicWorkById(musicWorkId)
                    .orElseThrow(() -> new MusicWorkNotFoundException("MusicWork " + musicWorkId + " cannot be found"));
            musicWorkRepository.delete(musicWork);
            LOG.info("MusicWork {} was deleted by user: {}", musicWork.getTitle(), user.getUsername());
        } else {
            LOG.info("MusicWork cannot be deleted, because user: {} is not and Admin", user.getUsername());
        }
    }

    private User getUserByPrincipal(Principal principal) {
        String username = principal.getName();

        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found with username" + username));
    }

}
