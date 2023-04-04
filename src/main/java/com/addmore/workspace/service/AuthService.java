package com.addmore.workspace.service;

import com.addmore.workspace.entity.Authority;
import com.addmore.workspace.entity.request.AuthRequest;
import com.addmore.workspace.repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;

    public void saveAuth(AuthRequest auth) {
        authRepository.save(Authority.builder()
                .name(auth.getName())
                .build());
    }

    public void modifyAuth(AuthRequest auth) {
        Authority targetAuth = authRepository.findById(auth.getId()).orElseThrow(()->new NoSuchElementException("해당 권한은 존재하지 않습니다."));
        targetAuth.setName(auth.getName());
    }

    public void removeAuth(AuthRequest auth) {
        Authority targetAuth = authRepository.findById(auth.getId()).orElseThrow(()->new NoSuchElementException("해당 권한은 존재하지 않습니다."));
        authRepository.delete(targetAuth);
    }

    public List<AuthRequest> findAuthList(String name) {
        if(StringUtils.hasText(name)) {

        }
        return authRepository.findAll(Sort.by("createdAt").descending())
                .stream().map(auth -> AuthRequest.builder()
                        .id(auth.getId())
                        .name(auth.getName())
                        .build())
                .toList();
    }
}
