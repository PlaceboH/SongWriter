package com.songwriter.backend.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="follower_user_fk")
    private User follower;

    @ManyToOne
    @JoinColumn(name="following_user_fk")
    private User following;

    public Subscription(User follower, User following) {
        this.follower = follower;
        this.following = following;
    }

    public Subscription() {}
}
