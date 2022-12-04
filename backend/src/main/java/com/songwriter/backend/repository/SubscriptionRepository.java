package com.songwriter.backend.repository;

import com.songwriter.backend.entity.Subscription;
import com.songwriter.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface SubscriptionRepository  extends JpaRepository<Subscription, Long> {
    List<Subscription> getSubscriptionsByFollower(User follower);
    List<Subscription> getSubscriptionsByFollowing(User follower);
    Subscription findSubscriptionByFollowerAndFollowing(User follower, User following);

}
