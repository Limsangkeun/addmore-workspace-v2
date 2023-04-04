package com.addmore.workspace.service;

import com.addmore.workspace.entity.Dept;
import com.addmore.workspace.entity.dto.DeptDto;
import com.addmore.workspace.entity.request.DeptRequest;
import com.addmore.workspace.repository.DeptReposiotory;
import com.sun.jdi.request.DuplicateRequestException;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.datetime.standard.DateTimeFormatterFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.security.InvalidParameterException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
@Log4j2
public class DeptService {
    private final DeptReposiotory deptReposiotory;

    public void createDept(@NonNull String name) {
        if(!StringUtils.hasText(name)) {
            throw new InvalidParameterException("부서명을 입력하세요.");
        }
        deptReposiotory.findByName(name).ifPresent((dept -> {
            throw new EntityExistsException("해당 부서명은 이미 존재합니다. : " +  dept.getName());
        }));
        Dept newDept = Dept.builder()
                .name(name)
                        .build();
        deptReposiotory.save(newDept);
    }

    public Map<String, Object> checkDuplication(@NonNull String name) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("isValid", true);
        deptReposiotory.findByName(name).ifPresent((dept -> {
            resultMap.put("isValid", false);
        }));
        return resultMap;
    }

    public Map<String, Object> findDeptList(String name, @NonNull Pageable pageable) {
        Map<String, Object> resultMap = new HashMap<>();

        Page<Dept> deptData = null;
        if(StringUtils.hasText(name)) {
            log.info("findAllByNameLikeIgnoreCaseOrderByCreatedAtDesc");
            deptData = deptReposiotory.findAllByNameStartsWithIgnoreCaseOrderByCreatedAtDesc(name, pageable);
        }else {
            log.info("findAllOrderByCreatedAt");
            deptData = deptReposiotory.findAllByOrderByCreatedAtDesc(pageable);
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
