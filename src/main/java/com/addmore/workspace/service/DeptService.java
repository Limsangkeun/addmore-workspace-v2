package com.addmore.workspace.service;

import com.addmore.workspace.entity.Dept;
import com.addmore.workspace.entity.dto.DeptDto;
import com.addmore.workspace.entity.request.DeptRequest;
import com.addmore.workspace.repository.DeptReposiotory;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class DeptService {
    private final DeptReposiotory deptReposiotory;

    public Map<String, Object> createDept(@NonNull String name) {
        Map<String, Object> resultMap = new HashMap<>();
        deptReposiotory.findByName(name).ifPresent((dept -> {
            String msg = "해당 부서명은 이미 존재합니다. : " +  dept.getName();
            resultMap.put("flag", false);
            resultMap.put("msg", msg);
            throw new EntityExistsException(msg);
        }));
        Dept newDept = Dept.builder()
                .name(name)
                        .build();
        deptReposiotory.save(newDept);
        resultMap.put("flag", true);
        return resultMap;
    }

    public Map<String, Object> checkDuplication(@NonNull String name) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("flag", true);
        deptReposiotory.findByName(name).ifPresent((dept -> {
            String msg = "해당 부서명은 이미 존재합니다. : " +  dept.getName();
            resultMap.put("flag", false);
        }));
        return resultMap;
    }

    public Map<String, Object> findDeptList(String name, @NonNull Pageable pageable) {
        Map<String, Object> resultMap = new HashMap<>();

        AtomicReference<Page<Dept>> deptData = null;
        if(Optional.of(name).isPresent()) {
            deptData.set(deptReposiotory.findAllByName(name, pageable));
        }else {
            deptData.set(deptReposiotory.findAll(pageable));
        };

        List<DeptDto> deptRequestList = deptData.get().getContent().stream().map(dept -> {
           return DeptDto.builder()
                   .id(dept.getId())
                   .name(dept.getName())
                   .createdAt(dept.getCreatedAt())
                   .createdBy(dept.getCreatedBy())
                   .build();
        }).toList();

        resultMap.put("total_count", deptData.get().getTotalElements());
        resultMap.put("total_page", deptData.get().getTotalPages());
        resultMap.put("dept_list", deptRequestList);
        return resultMap;
    }
}
