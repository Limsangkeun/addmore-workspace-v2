package com.addmore.workspace.controller;

import com.addmore.workspace.entity.dto.UserAuthDto;
import com.addmore.workspace.entity.dto.UserDto;
import com.addmore.workspace.entity.request.SaveUserAuthorityRequest;
import com.addmore.workspace.entity.request.UserRequest;
import com.addmore.workspace.entity.request.UserSearchParam;
import com.addmore.workspace.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
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
    public ResponseEntity<Map<String, Object>> createUser(@RequestBody UserRequest userRequest) {
        return new ResponseEntity<>(userService.createUser(userRequest), HttpStatus.OK);
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

    @GetMapping("/find/{id}")
    public ResponseEntity<UserDto> findUser(@PathVariable(name = "id") String id) {
        return new ResponseEntity<>(userService.findUser(id), HttpStatus.OK);
    }

    @GetMapping("/find/users-in-dept/{deptId}")
    public ResponseEntity<Map<String, Object>> findUsersInDept(@PathVariable(name = "deptId") String deptId) {
        return new ResponseEntity<>(userService.findUserListByDeptId(deptId), HttpStatus.OK);
    }

    @PostMapping("/save-user-authorities")
    public void saveUserAuthority(@RequestBody SaveUserAuthorityRequest request) {
        userService.saveUserAuthority(request.getUserAuthDtoList());
    }
}
