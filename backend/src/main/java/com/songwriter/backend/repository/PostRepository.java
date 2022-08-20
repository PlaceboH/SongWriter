package com.songwriter.backend.repository;

import com.songwriter.backend.entity.Post;
import com.songwriter.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByUserOrderByCreationDateDesc(User user);
    List<Post> findAllByOrderByCreationDateDesc();
    Optional<Post> findPostByIdAndUser(Long id, User user);
}
