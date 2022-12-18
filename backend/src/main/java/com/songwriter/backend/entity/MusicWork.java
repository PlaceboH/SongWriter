package com.songwriter.backend.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
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
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, mappedBy = "musicWork", orphanRemoval = true)
    private Set<Mark> marks = new HashSet<>();

    @Column(updatable = false)
    private LocalDateTime creationDate;

    @PrePersist
    protected void onCreate() {
        this.creationDate = LocalDateTime.now();
    }
}
