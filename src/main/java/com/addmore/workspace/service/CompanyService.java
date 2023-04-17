package com.addmore.workspace.service;

import com.addmore.workspace.entity.Company;
import com.addmore.workspace.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;

    public Map<String, Object> findAll(Pageable pageable) {
        Map<String, Object> resultMap = new HashMap<>();
        Page<Company> companyPageData = companyRepository.findAll(pageable);
        resultMap.put("total_count", companyPageData.getTotalElements());
        resultMap.put("total_page", companyPageData.getTotalPages());
        resultMap.put("company_list", companyPageData.getContent());

        return resultMap;
    }
}
