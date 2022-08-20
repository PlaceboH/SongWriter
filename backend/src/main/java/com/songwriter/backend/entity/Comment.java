package com.songwriter.backend.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    private Post post;
    @ManyToOne(fetch = FetchType.EAGER)
    private MusicWork musicWork;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String message;
    @Column(nullable = false)
    private Long userId;

    private Integer likes;

    @Column(updatable = false)
    private LocalDateTime creationDate;
    @PrePersist
    protected void onCreate(){
        this.creationDate = LocalDateTime.now();
    }


}
