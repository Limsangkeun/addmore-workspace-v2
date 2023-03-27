package com.addmore.workspace.repository;

import com.addmore.workspace.entity.Dept;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeptReposiotory extends JpaRepository<Dept, String> {
    Optional<Dept> findByName(String name);
}