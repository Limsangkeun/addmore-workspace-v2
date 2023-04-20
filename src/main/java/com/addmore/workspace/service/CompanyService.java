package com.addmore.workspace.service;

import com.addmore.workspace.entity.Company;
import com.addmore.workspace.entity.dto.CompanyDto;
import com.addmore.workspace.entity.request.CompanyRequest;
import com.addmore.workspace.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;

    public Map<String, Object> findAll(String name, Pageable pageable) {
        Map<String, Object> resultMap = new HashMap<>();
        Page<Company> companyPageData = null;
        if(StringUtils.hasText(name)) {
            companyPageData = companyRepository.findAllByNameContaining(name, pageable);
        } else {
            companyPageData = companyRepository.findAll(pageable);
        }

        resultMap.put("total_count", companyPageData.getTotalElements());
        resultMap.put("total_page", companyPageData.getTotalPages());
        resultMap.put("company_list", companyPageData.getContent());

        return resultMap;
    }

    public void changeCallStatus(CompanyDto companyDto) {
        Company targetCompany = companyRepository.findById(companyDto.getId()).orElseThrow(()-> new NoSuchElementException("해당 아이디의 업체 정보가 없습니다."));
        targetCompany.setStatus(companyDto.getStatus());
        companyRepository.save(targetCompany);
    }
}
