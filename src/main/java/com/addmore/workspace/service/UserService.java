package com.addmore.workspace.service;

import com.addmore.workspace.entity.Authority;
import com.addmore.workspace.entity.Dept;
import com.addmore.workspace.entity.User;
import com.addmore.workspace.entity.UserAuthority;
import com.addmore.workspace.entity.dto.UserAuthDto;
import com.addmore.workspace.entity.dto.UserDto;
import com.addmore.workspace.entity.request.UserRequest;
import com.addmore.workspace.exception.AlreadyExistException;
import com.addmore.workspace.repository.AuthorityRepository;
import com.addmore.workspace.repository.DeptRepository;
import com.addmore.workspace.repository.UserAuthorityRepository;
import com.addmore.workspace.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final DeptRepository deptRepository;
    private final AuthorityRepository authorityRepository;
    private final UserAuthorityRepository userAuthorityRepository;
    private final PasswordEncoder passwordEncoder;



    // create
    public Map<String, Object> createUser(UserRequest request) {
        Map<String, Object> resultMap = new HashMap<>();
        if(userRepository.findByUsername(request.getUsername()).isPresent())
            throw new AlreadyExistException("해당 아이디는 이미 존재합니다.");

        Dept targetDept = deptRepository.findById(request.getDeptId()).orElseThrow(()->new NoSuchElementException("해당 ID의 부서가 없습니다."));

        User newUser = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .email(request.getEmail())
                .birth(request.getBirth())
                .joinDate(LocalDate.now())
                .dept(targetDept)
                .enabled(true)
                .build();
        userRepository.save(newUser);

        resultMap.put("userId", newUser.getId());
        return resultMap;
    }

    // modify
    public void modifyUser(UserRequest request) {
        User targetUser = userRepository.findById(request.getId())
                .orElseThrow(()-> new NoSuchElementException("해당 사용자가 존재하지 않습니다."));
        Dept targetDept = deptRepository.findById(request.getDeptId()).orElseThrow(()->new NoSuchElementException("해당 ID의 부서가 없습니다."));

        targetUser.setName(request.getName());
        targetUser.setEmail(request.getEmail());
        targetUser.setBirth(request.getBirth());
        targetUser.setJoinDate(request.getJoinDate());
        targetUser.setDept(targetDept);

        userRepository.save(targetUser);
    }

    // remove
    public void removeUser(UserRequest request) {
        User targetUser = userRepository.findById(request.getId())
                .orElseThrow(()-> new NoSuchElementException("해당 사용자가 존재하지 않습니다."));

        List<UserAuthority> userAuthorities =  userAuthorityRepository.findUserAuthoritiesByUserEquals(targetUser);
        for(UserAuthority auth : userAuthorities) {
            userAuthorityRepository.delete(auth);
        }
        userRepository.delete(targetUser);
    }

    // findByName

    // findAll
    public Map<String, Object> findUserList(String name, Pageable pageable) {
        Map<String, Object> resultMap = new HashMap<>();
        Page<User> userPageData = null;
        if(StringUtils.hasText(name)) {
            userPageData = userRepository.findAllByNameLikeOrderByCreatedAtDesc(name, pageable);
        } else {
            userPageData = userRepository.findAllByOrderByCreatedAtDesc(pageable);
        }
        List<UserRequest> userReqList = userPageData.getContent().stream().map(user -> UserRequest.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .name(user.getName())
                        .email(user.getEmail())
                        .birth(user.getBirth())
                        .joinDate(user.getJoinDate())
                        .build()).toList();
        resultMap.put("total_count", userPageData.getTotalElements());
        resultMap.put("total_page", userPageData.getTotalPages());
        resultMap.put("user_list", userReqList);

        return resultMap;
    }

    public UserDto findUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new NoSuchElementException("해당 ID의 사용자가 없습니다."));
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .email(user.getEmail())
                .birth(user.getBirth())
                .deptId(user.getDept().getId())
                .joinDate(user.getJoinDate())
                .build();
    }

    public void saveUserAuthority(List<UserAuthDto> requestAuthList) {
        if(requestAuthList == null || requestAuthList.isEmpty()) throw new IllegalArgumentException("저장 요청 권한이 없습니다.");
        for(UserAuthDto dto : requestAuthList) {
            User targetUser = userRepository.findById(dto.getUserId()).orElseThrow(()-> new NoSuchElementException("해당 ID의 사용자가 없습니다."));
            Authority targetAuth = authorityRepository.findById(dto.getId()).orElseThrow(()-> new NoSuchElementException("해당 ID의 권한이 없습니다."));
            Optional<UserAuthority> userAuthority = userAuthorityRepository.findUserAuthorityByUserEqualsAndAuthorityEquals(targetUser, targetAuth);
            if(userAuthority.isPresent()) continue;
            userAuthorityRepository.save(UserAuthority.builder()
                    .user(targetUser)
                    .authority(targetAuth)
                    .build());
        }
    }

     public Map<String, Object> findUserListByDeptId (String deptId) {
        Map<String, Object> resultMap = new HashMap<>();
        if(!StringUtils.hasText(deptId)) throw new IllegalArgumentException("부서 ID 정보가 없습니다.");
        Dept targetDept = deptRepository.findById(deptId).orElseThrow(()-> new NoSuchElementException("해당 ID의 부서가 없습니다."));
        List<UserDto> userList = userRepository.findAllByDeptEquals(targetDept).stream().map(user -> {
            return UserDto.builder()
                    .id(user.getId())
                    .username(user.getUsername())
                    .name(user.getName())
                    .build();
        }).toList();
        resultMap.put("userList", userList);
        return resultMap;
     }
}
