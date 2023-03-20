package com.addmore.workspace.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/")
public class CommonController {

    @RequestMapping
    public String main() {
        return "index";
    }

    @RequestMapping("/api/test")
    public ResponseEntity<Map<String, Object>> testApi() {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("name", "sangkeun");
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }
}
