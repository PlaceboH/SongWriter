package com.songwriter.backend.repository;

import com.songwriter.backend.entity.Mark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarkRepository extends JpaRepository<Mark, Long> {
    List<Mark> findMarksByMusicWorkId(Long id);
}
