package com.addmore.workspace.controller;

import com.addmore.workspace.entity.request.AuthorityRequest;
import com.addmore.workspace.service.AuthorityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/authority")
@RequiredArgsConstructor
public class AuthorityController {

    private final AuthorityService authorityService;

    @PostMapping("/save")
    public void saveAuthority(@RequestBody AuthorityRequest request) {
        authorityService.saveAuth(request);
    }

    @PostMapping("/modify")
    public void modifyAuthority(@RequestBody AuthorityRequest request) {
        authorityService.modifyAuth(request);
    }

    @PostMapping("/remove")
    public void removeAuthority(@RequestBody AuthorityRequest request) {
        authorityService.removeAuth(request);
    }

    @PostMapping("/find")
    public ResponseEntity<Map<String, Object>> findAuthorities(String name) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("authorities", authorityService.findAuthList(name));
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }
}
