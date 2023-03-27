package com.addmore.workspace.controller;

import com.addmore.workspace.entity.request.DeptRequest;
import com.addmore.workspace.service.DeptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@Log4j2
@RestController
@RequestMapping("/api/dept")
public class DeptController {
    private final DeptService deptService;

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createDept(@NonNull DeptRequest deptRequest) {
        log.info("======== create dept ========");
        log.info(deptRequest.toString());
        return new ResponseEntity<>(deptService.createDept(deptRequest.getName()), HttpStatus.OK);
    }

    @PostMapping("/check-duplication")
    public ResponseEntity<Map<String, Object>> checkDuplication(@NonNull DeptRequest deptRequest) {
        return new ResponseEntity<>(deptService.checkDuplication(deptRequest.getName()), HttpStatus.OK);
    }
}
