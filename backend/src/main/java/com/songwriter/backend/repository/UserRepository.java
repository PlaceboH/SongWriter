package com.songwriter.backend.repository;

import com.songwriter.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAllByOrderByCreationDateDesc();
    Optional<User> findUserByUsername(String username);
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserById(Long id);
    List<User>  findAllByUsernameOrNameOrLastname(String username, String name, String lastname);
}
