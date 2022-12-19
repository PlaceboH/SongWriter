package com.songwriter.backend.repository;

import com.songwriter.backend.entity.Mark;
import com.songwriter.backend.entity.MusicWork;
import com.songwriter.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarkRepository extends JpaRepository<Mark, Long> {
    List<Mark> findAllByMusicWorkIdOrderByCreationDateDesc(Long musicWorkId);
    List<Mark> findAllByOrderByCreationDateDesc();
    List<Mark> findMarksByMusicWorkId(Long id);
}
