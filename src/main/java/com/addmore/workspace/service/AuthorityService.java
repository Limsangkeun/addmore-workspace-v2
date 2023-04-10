package com.addmore.workspace.service;

import com.addmore.workspace.entity.Authority;
import com.addmore.workspace.entity.request.AuthorityRequest;
import com.addmore.workspace.exception.AlreadyExistException;
import com.addmore.workspace.repository.AuthorityRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthorityService {

    private final AuthorityRepository authorityRepository;

    private final EntityManagerFactory entityManagerFactory;

    public void createAuth(AuthorityRequest auth) {
        if(!StringUtils.hasText(auth.getId())) auth.setId(UUID.randomUUID().toString());
        if(authorityRepository.findByName(auth.getName()).isPresent()) throw new AlreadyExistException("NE:해당 권한명은 이미 존재합니다.");

        authorityRepository.save(Authority.builder()
                .name(auth.getName())
                .build());
    }

    public void modifyAuth(AuthorityRequest auth) {
        Authority targetAuth = authorityRepository.findById(auth.getId()).orElseThrow(()->new NoSuchElementException("NE:해당 권한은 존재하지 않습니다."));
        targetAuth.setName(auth.getName());
        authorityRepository.save(targetAuth);
    }

    public void removeAuth(AuthorityRequest auth) {
        Authority targetAuth = authorityRepository.findById(auth.getId()).orElseThrow(()->new NoSuchElementException("NE:해당 권한은 존재하지 않습니다."));
        authorityRepository.delete(targetAuth);
    }

    public List<AuthorityRequest> findAuthList(String name) {
        if(StringUtils.hasText(name)) {
            return authorityRepository.findAllByNameStartsWithOrderByCreatedAtDesc(name)
                    .stream().map(auth->AuthorityRequest.builder()
                            .id(auth.getId())
                            .name(auth.getName())
                            .createdAt(auth.getCreatedAt().toLocalDate())
                            .createdBy(auth.getCreatedBy())
                            .build()).toList();
        }
        return authorityRepository.findAll(Sort.by("createdAt").descending())
                .stream().map(auth -> AuthorityRequest.builder()
                        .id(auth.getId())
                        .name(auth.getName())
                        .createdAt(auth.getCreatedAt().toLocalDate())
                        .createdBy(auth.getCreatedBy())
                        .build())
                .toList();
    }

    public Map<String, Object> findAllWithUserId(String userId) {
        Map<String, Object> resultMap = new HashMap<>();

        return resultMap;
    }
}
