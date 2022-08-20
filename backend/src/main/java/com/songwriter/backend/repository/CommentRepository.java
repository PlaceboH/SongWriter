package com.songwriter.backend.repository;

import com.songwriter.backend.entity.Comment;
import com.songwriter.backend.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPost(Post post);
    Comment findByIdAndUserId(Long commentId, Long userId);
}
