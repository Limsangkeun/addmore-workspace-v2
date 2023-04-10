package com.addmore.workspace.controller;

import com.addmore.workspace.entity.request.DeptRequest;
import com.addmore.workspace.service.DeptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Log4j2
@RestController
@RequestMapping("/api/dept")
public class DeptController {
    private final DeptService deptService;

    @PostMapping("/create")
    public void createDept(@NonNull @RequestBody DeptRequest deptRequest) {
        deptService.createDept(deptRequest);
    }

    @PostMapping("/modify")
    public void modifyDept(@NonNull @RequestBody DeptRequest deptRequest) {
        deptService.modifyDept(deptRequest);
    }

    @PostMapping("/remove")
    public void removeDept(@NonNull @RequestBody DeptRequest deptRequest) {
        deptService.removeDept(deptRequest);
    }

    @PostMapping("/find")
    public ResponseEntity<Map<String, Object>> findDeptList(@NonNull @RequestBody DeptRequest deptRequest) {
        Pageable pageable = PageRequest.of(deptRequest.getPage(), deptRequest.getSize());
        return new ResponseEntity<>(deptService.findDeptList(deptRequest.getName(), pageable), HttpStatus.OK);
    }
}
