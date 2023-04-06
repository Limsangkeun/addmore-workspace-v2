package com.addmore.workspace.repository;

import com.addmore.workspace.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);
    Page<User> findAllByNameLikeOrderByCreatedAtDesc(String name, Pageable pageable);
    Page<User> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
