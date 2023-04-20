package com.addmore.workspace.controller;

import com.addmore.workspace.entity.dto.CompanyDto;
import com.addmore.workspace.entity.request.CompanyRequest;
import com.addmore.workspace.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api/company")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> findAllCompany(@RequestParam(required = false) String name, @RequestParam int page, @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return new ResponseEntity<>(companyService.findAll(name, pageable), HttpStatus.OK);
    }

    @PostMapping("/status")
    public void changeCallStatus(@RequestBody CompanyDto companyDto) {
        companyService.changeCallStatus(companyDto);
    }
}
