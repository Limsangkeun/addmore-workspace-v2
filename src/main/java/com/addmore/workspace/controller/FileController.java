package com.addmore.workspace.controller;

import com.addmore.workspace.service.FileService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/file")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @GetMapping("/{groupId}")
    public ResponseEntity<Map<String, Object>> findAllByGroupId(@PathVariable(name = "groupId") String groupId) {
        return new ResponseEntity<>(fileService.findAllByGroupId(groupId), HttpStatus.OK);
    }

    @PostMapping("/image-upload")
    public ResponseEntity<Map<String, Object>> uploadImage(MultipartFile file) throws IOException {
        return new ResponseEntity<>(fileService.uploadImage(file), HttpStatus.OK);
    }

    @PostMapping("/upload")
    public ResponseEntity<Map<String, Object>> uploadFiles(@RequestParam(name = "files")List<MultipartFile> files) throws IOException {
        return new ResponseEntity<>(fileService.uploadFiles(files), HttpStatus.OK);
    }

    @PostMapping("/download/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable(name = "id") String fileId) throws IOException {

        // 파일 로드
        Path filePath = fileService.downloadFileById(fileId);

        // HTTP 응답 생성
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(filePath));
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + URLEncoder.encode(filePath.getFileName().toString(), Charset.forName("UTF-8")) + "\"");
        headers.add(HttpHeaders.CONTENT_TYPE, Files.probeContentType(filePath));
        headers.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(resource.contentLength()));
        ResponseEntity<Resource> responseEntity = ResponseEntity.ok().headers(headers).body(resource);

        return responseEntity;
    }

    @GetMapping("/download-all/{groupId}")
    public ResponseEntity<Resource> downloadAllFile(@PathVariable(name = "groupId") String groupId) throws IOException {
        Path zipFilePath = fileService.downloadAllFile(groupId);

        // HTTP 응답 생성
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(zipFilePath));
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + zipFilePath.getFileName().toString() + "\"");
        headers.add(HttpHeaders.CONTENT_TYPE, "application/zip");
        headers.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(resource.contentLength()));
        ResponseEntity<Resource> responseEntity = ResponseEntity.ok().headers(headers).body(resource);

        // 압축 파일 삭제
        Files.delete(zipFilePath);

        return responseEntity;
    }
}
