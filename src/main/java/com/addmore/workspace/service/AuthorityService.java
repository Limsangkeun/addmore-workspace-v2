package com.addmore.workspace.service;

import com.addmore.workspace.entity.Authority;
import com.addmore.workspace.entity.request.AuthorityRequest;
import com.addmore.workspace.repository.AuthorityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AuthorityService {

    private final AuthorityRepository authorityRepository;

    public void saveAuth(AuthorityRequest auth) {
        authorityRepository.save(Authority.builder()
                .name(auth.getName())
                .build());
    }

    public void modifyAuth(AuthorityRequest auth) {
        Authority targetAuth = authorityRepository.findById(auth.getId()).orElseThrow(()->new NoSuchElementException("해당 권한은 존재하지 않습니다."));
        targetAuth.setName(auth.getName());
    }

    public void removeAuth(AuthorityRequest auth) {
        Authority targetAuth = authorityRepository.findById(auth.getId()).orElseThrow(()->new NoSuchElementException("해당 권한은 존재하지 않습니다."));
        authorityRepository.delete(targetAuth);
    }

    public List<AuthorityRequest> findAuthList(String name) {
        if(StringUtils.hasText(name)) {

        }
        return authorityRepository.findAll(Sort.by("createdAt").descending())
                .stream().map(auth -> AuthorityRequest.builder()
                        .id(auth.getId())
                        .name(auth.getName())
                        .build())
                .toList();
    }
}
