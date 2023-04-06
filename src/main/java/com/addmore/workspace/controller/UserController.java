package com.addmore.workspace.controller;

import com.addmore.workspace.entity.request.UserRequest;
import com.addmore.workspace.entity.request.UserSearchParam;
import com.addmore.workspace.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/create")
    public void createUser(@RequestBody UserRequest userRequest) {
        userService.createUser(userRequest);
    }

    @PostMapping("/modify")
    public void modifyUser(@RequestBody UserRequest userRequest) {
        userService.modifyUser(userRequest);
    }

    @PostMapping("/remove")
    public void removeUser(@RequestBody UserRequest userRequest) {
        userService.removeUser(userRequest);
    }

    @PostMapping("/find")
    public ResponseEntity<Map<String, Object>> findUserList(@RequestBody UserSearchParam searchParam) {
        Pageable pageable = PageRequest.of(searchParam.getPage(), searchParam.getSize());
        return new ResponseEntity<>(userService.findUserList(searchParam.getName(), pageable), HttpStatus.OK);
    }
}
