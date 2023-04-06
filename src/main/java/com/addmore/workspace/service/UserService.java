package com.addmore.workspace.service;

import com.addmore.workspace.entity.User;
import com.addmore.workspace.entity.request.UserRequest;
import com.addmore.workspace.exception.AlreadyExistException;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // create
    public void createUser(UserRequest request) {
        if(userRepository.findByUsername(request.getUsername()).isPresent())
            throw new AlreadyExistException("해당 아이디는 이미 존재합니다.");

        User newUser = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .email(request.getEmail())
                .birth(request.getBirth())
                .joinDate(LocalDate.now())
                .enabled(true)
                .build();
        userRepository.save(newUser);
    }

    // modify
    public void modifyUser(UserRequest request) {
        User targetUser = userRepository.findById(request.getId())
                .orElseThrow(()-> new NoSuchElementException("해당 사용자가 존재하지 않습니다."));
        targetUser.setName(request.getName());
        targetUser.setEmail(request.getEmail());
        targetUser.setBirth(request.getBirth());
        targetUser.setJoinDate(request.getJoinDate());

        userRepository.save(targetUser);
    }

    // remove
    public void removeUser(UserRequest request) {
        User targetUser = userRepository.findById(request.getId())
                .orElseThrow(()-> new NoSuchElementException("해당 사용자가 존재하지 않습니다."));
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
}
