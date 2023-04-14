package com.addmore.workspace.service;

import com.addmore.workspace.entity.Authority;
import com.addmore.workspace.entity.User;
import com.addmore.workspace.entity.UserAuthority;
import com.addmore.workspace.entity.dto.UserAuthDto;
import com.addmore.workspace.entity.request.AuthorityRequest;
import com.addmore.workspace.exception.AlreadyExistException;
import com.addmore.workspace.repository.AuthorityRepository;
import com.addmore.workspace.repository.UserAuthorityRepository;
import com.addmore.workspace.repository.UserRepository;
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

    private final UserAuthorityRepository userAuthorityRepository;

    private final UserRepository userRepository;

    public void createAuth(AuthorityRequest auth) {
        if(!StringUtils.hasText(auth.getId())) auth.setId(UUID.randomUUID().toString());
        if(authorityRepository.findByName(auth.getName()).isPresent()) throw new AlreadyExistException("해당 권한명은 이미 존재합니다.");

        authorityRepository.save(Authority.builder()
                .name(auth.getName())
                .build());
    }

    public void modifyAuth(AuthorityRequest auth) {
        Authority targetAuth = authorityRepository.findById(auth.getId()).orElseThrow(()->new NoSuchElementException("해당 권한은 존재하지 않습니다."));
        targetAuth.setName(auth.getName());
        authorityRepository.save(targetAuth);
    }

    public void removeAuth(AuthorityRequest auth) {
        Authority targetAuth = authorityRepository.findById(auth.getId()).orElseThrow(()->new NoSuchElementException("해당 권한은 존재하지 않습니다."));
        if(!userAuthorityRepository.findAllByAuthorityEquals(targetAuth).isEmpty()) throw new IllegalArgumentException("해당 권한이 매핑된 사용자가 존재하여 삭제 할 수 없습니다.");
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
        if(!StringUtils.hasText(userId)) throw new IllegalArgumentException("user id 정보가 없거나, 다릅니다.");

        User targetUser = userRepository.findById(userId).orElseThrow(()-> new NoSuchElementException("해당 아이디에 해당하는 직원이 없습니다."));

        List<UserAuthority> grantedAuthList = userAuthorityRepository.findUserAuthoritiesByUserEquals(targetUser);
        List<Authority> allAuthList = authorityRepository.findAll(Sort.by("createdAt").descending());

        List<UserAuthDto> userAuthList = allAuthList.stream().map(auth ->{
            boolean isChecked = false;
            for(UserAuthority userAuth : grantedAuthList) {
                if(auth.getId().equals(userAuth.getAuthority().getId())) {
                    isChecked = true;
                    break;
                }
            }
            return UserAuthDto.builder()
                    .id(auth.getId())
                    .name(auth.getName())
                    .isChecked(isChecked)
                   .build();
        }).toList();
        resultMap.put("authList", userAuthList);
        return resultMap;
    }
}
