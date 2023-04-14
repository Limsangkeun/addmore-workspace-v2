package com.addmore.workspace.service;

import com.addmore.workspace.entity.Dept;
import com.addmore.workspace.entity.dto.DeptDto;
import com.addmore.workspace.entity.request.DeptRequest;
import com.addmore.workspace.repository.DeptRepository;
import com.addmore.workspace.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.security.InvalidParameterException;
import java.util.*;

@Service
@RequiredArgsConstructor
@Log4j2
public class DeptService {
    private final DeptRepository deptRepository;
    private final UserRepository userRepository;

    public void createDept(DeptRequest request) {
        if(!StringUtils.hasText(request.getName())) {
            throw new InvalidParameterException("부서명을 입력하세요.");
        }
        deptRepository.findByName(request.getName()).ifPresent((dept -> {
            throw new EntityExistsException("해당 부서명은 이미 존재합니다.");
        }));
        Dept newDept = Dept.builder()
                .name(request.getName())
                .build();
        deptRepository.save(newDept);
    }

    public void modifyDept(DeptRequest request) {
        Dept targetDept = deptRepository.findById(request.getId()).orElseThrow(() -> {
            throw new NoSuchElementException("대상 부서가 존재하지 않습니다.");
        });
        deptRepository.findByName(request.getName()).ifPresent((dept -> {
            throw new EntityExistsException("해당 부서명은 이미 존재합니다.");
        }));
        targetDept.setName(request.getName());
        deptRepository.save(targetDept);
    }

    public void removeDept(DeptRequest request) {
        Dept targetDept = deptRepository.findById(request.getId()).orElseThrow(() -> {
            throw new NoSuchElementException("대상 부서가 존재하지 않습니다.");
        });
        if(!userRepository.findAllByDeptEquals(targetDept).isEmpty()) throw new IllegalArgumentException("해당 부서에 속한 사용자가 존재하여 삭제 할 수 없습니다.");

        deptRepository.delete(targetDept);
    }

    public Map<String, Object> findDeptList(String name, @NonNull Pageable pageable) {
        Map<String, Object> resultMap = new HashMap<>();

        Page<Dept> deptData = null;
        if(StringUtils.hasText(name)) {
            log.info("findAllByNameLikeIgnoreCaseOrderByCreatedAtDesc");
            deptData = deptRepository.findAllByNameStartsWithIgnoreCaseOrderByCreatedAtDesc(name, pageable);
        }else {
            log.info("findAllOrderByCreatedAt");
            deptData = deptRepository.findAllByOrderByCreatedAtDesc(pageable);
        };

        List<DeptDto> deptRequestList = deptData.getContent().stream().map(dept -> DeptDto.builder()
                .id(dept.getId())
                .name(dept.getName())
                .createdAt(dept.getCreatedAt().toLocalDate())
                .createdBy(dept.getCreatedBy())
                .build()).toList();

        resultMap.put("total_count", deptData.getTotalElements());
        resultMap.put("total_page", deptData.getTotalPages());
        resultMap.put("dept_list", deptRequestList);
        return resultMap;
    }
}
