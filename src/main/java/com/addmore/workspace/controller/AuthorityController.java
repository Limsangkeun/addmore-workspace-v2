package com.addmore.workspace.controller;

import com.addmore.workspace.entity.request.AuthorityRequest;
import com.addmore.workspace.service.AuthorityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/authority")
@RequiredArgsConstructor
public class AuthorityController {

    private final AuthorityService authorityService;

    @PostMapping("/create")
    public void createAuthority(@RequestBody AuthorityRequest request) {
        authorityService.createAuth(request);
    }

    @PostMapping("/modify")
    public void modifyAuthority(@RequestBody AuthorityRequest request) {
        authorityService.modifyAuth(request);
    }

    @PostMapping("/remove")
    public void removeAuthority(@RequestBody AuthorityRequest request) {
        authorityService.removeAuth(request);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Map<String, Object>> findAuthoritiesWithUserId(@PathVariable(name = "id") String id) {
        return new ResponseEntity<>(authorityService.findAllWithUserId(id), HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity<Map<String, Object>> findAuthorities(@RequestParam(defaultValue = "") String name) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("authList", authorityService.findAuthList(name));
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }
}
