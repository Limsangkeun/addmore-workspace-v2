package com.addmore.workspace.repository;

import com.addmore.workspace.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, String> {
    List<Authority> findAllByNameStartsWithOrderByCreatedAtDesc(String name);
    Optional<Authority> findByName(String name);
}
