package com.addmore.workspace.repository;

import com.addmore.workspace.entity.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, String> {
    Page<Company> findAllByNameContaining(String name, Pageable pageable);
}
