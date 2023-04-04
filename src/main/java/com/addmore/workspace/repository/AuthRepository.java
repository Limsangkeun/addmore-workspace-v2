package com.addmore.workspace.repository;

import com.addmore.workspace.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthRepository extends JpaRepository<Authority, String> {
}
