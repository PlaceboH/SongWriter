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
    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;
    @JsonIgnore
    private Long musicWorkId;
    @Column(nullable = false)
    private String username;
    @Column(updatable = false)
    private LocalDateTime creationDate;
    @PrePersist
    protected void onCreate(){
        this.creationDate = LocalDateTime.now();
    }
}
