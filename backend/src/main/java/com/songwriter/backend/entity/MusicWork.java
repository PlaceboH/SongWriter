package com.songwriter.backend.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
public class MusicWork {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(columnDefinition = "text")
    private String lyrics;
    @Column(columnDefinition = "text")
    private String description;
    @Column
    private String chords;
    @Lob
    @Column(columnDefinition = "LONGBLOB", unique = false)
    private byte[] notes;
    private String caption;
    private Integer likes;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @OneToMany(fetch = FetchType.LAZY)
    private Set<Mark> marks = new HashSet<>();

    @Column
    @ElementCollection(targetClass = String.class)
    private Set<String> likedUsers = new HashSet<>();

    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, mappedBy = "post", orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();
    @Column(updatable = false)
    private LocalDateTime creationDate;

    @PrePersist
    protected void onCreate() {
        this.creationDate = LocalDateTime.now();
    }
}
