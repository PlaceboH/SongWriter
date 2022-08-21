package com.songwriter.backend.entity;

import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;

@Data
@Entity
public class Mark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonIgnore
    private Long userId;
    @JsonIgnore
    private Long musicWorkId;
    @Column(nullable = false)
    private String username;
}