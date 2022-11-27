package com.songwriter.backend.entity;

import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class Mark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer stars;

    @ManyToOne(fetch = FetchType.EAGER)
    private Comment comment;
    @ManyToOne(fetch = FetchType.EAGER)
    private MusicWork musicWork;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String message;
    @Column(updatable = false)
    private LocalDateTime creationDate;
    @PrePersist
    protected void onCreate(){
        this.creationDate = LocalDateTime.now();
    }
}
