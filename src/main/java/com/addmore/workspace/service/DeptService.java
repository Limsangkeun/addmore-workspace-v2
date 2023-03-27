package com.addmore.workspace.service;

import com.addmore.workspace.entity.Dept;
import com.addmore.workspace.repository.DeptReposiotory;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

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
}
