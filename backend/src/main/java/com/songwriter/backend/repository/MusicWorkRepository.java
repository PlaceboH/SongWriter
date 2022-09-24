package com.songwriter.backend.repository;

import com.songwriter.backend.entity.MusicWork;
import com.songwriter.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MusicWorkRepository extends JpaRepository<MusicWork, Long> {
    List<MusicWork> findAllByUserOrderByCreationDateDesc(User user);
    List<MusicWork> findAllByOrderByCreationDateDesc();
    Optional<MusicWork> findMusicWorkByIdAndUser(Long id, User user);
    Optional<MusicWork> findMusicWorkById(Long id);
    Optional<MusicWork> findMusicWorkByTitleOrDescriptionOrderByCreationDateDesc(String title, String description);
}
