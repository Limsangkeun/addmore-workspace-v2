package com.addmore.workspace.service;

import com.addmore.workspace.entity.FileItem;
import com.addmore.workspace.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
@RequiredArgsConstructor
@Log4j2
public class FileService {
    private final FileRepository fileRepository;
    private final String UPLOAD_PATH = "C:/uploads/";

    private final String TEMP_UPLOAD_PATH = "C:/uploads/temp/";

    public Map<String, Object> uploadFiles(List<MultipartFile> fileList) throws IOException {
        Map<String, Object> resultMap = new HashMap<>();
        Path uploadPath = Paths.get(UPLOAD_PATH);
        // 디렉토리가 없으면 생성
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        FileItem fileGroup = new FileItem();
        fileGroup.setId(UUID.randomUUID().toString());
        fileRepository.save(fileGroup);
        List<FileItem> fileItemList = new ArrayList<>();
        try {
            for (MultipartFile file : fileList) {
                // 파일 저장 경로 설정
                FileItem saveFile = new FileItem();
                saveFile.setId(UUID.randomUUID().toString());
                saveFile.setFileGroup(fileGroup);
                saveFile.setAttFileNm(saveFile.getId());
                saveFile.setAttFileSize(file.getSize());
                saveFile.setOriginFileNm(file.getOriginalFilename());

                String fileName = StringUtils.cleanPath(saveFile.getId());
                // 파일 저장
                Path filePath = uploadPath.resolve(fileName);
                try (OutputStream outputStream = Files.newOutputStream(filePath)) {
                    outputStream.write(file.getBytes());
                }
                fileItemList.add(saveFile);
            }
            fileRepository.saveAll(fileItemList);
        } catch (IOException exception) {
            fileRepository.delete(fileGroup);
        }
        resultMap.put("fileGroup", fileGroup.getId());
        return resultMap;
    }

    public Map<String, Object> findAllByGroupId(String groupId) {
        Map<String, Object> resultMap = new HashMap<>();
        FileItem fileGroup = fileRepository.findById(groupId).orElseThrow(()->new NoSuchElementException("해당 아이디의 파일 그룹이 없습니다."));
        List<FileItem> fileItemList = fileRepository.findAllByFileGroupEquals(fileGroup);
        resultMap.put("fileList", fileItemList);
        return resultMap;
    }

    public Path downloadFileById(String fileId) throws IOException {
        FileItem fileItem = fileRepository.findById(fileId).orElseThrow(()->new NoSuchFileException("해당 ID의 파일이 없습니다."));
        Path uploadPath = Paths.get(UPLOAD_PATH);
        // 디렉토리가 없으면 생성
        if (!Files.exists(uploadPath)) {
            throw new IOException("현재 파일 디렉토리가 존재하지 않습니다.");
        }
        try {
            Path filePath = uploadPath.resolve(fileItem.getId());
            Resource file = new UrlResource(filePath.toUri());
            if (!file.exists()) throw new NoSuchFileException("해당 ID의 파일이 없습니다.");
            return filePath;
        } catch (MalformedURLException e) {
            throw new MalformedURLException("파일 경로가 잘못되었습니다.");
        }
    }

    public Path downloadAllFile(String groupId) {
        Path zipPath = Paths.get(TEMP_UPLOAD_PATH, LocalDateTime.now().toLocalTime().toString());
        try {
            if (!Files.exists(zipPath.getParent())) {
                Files.createDirectories(zipPath.getParent());
            }
            FileItem fileGroup = fileRepository.findById(groupId).orElseThrow(()->new NoSuchElementException("해당 아이디의 파일 그룹이 없습니다."));
            List<FileItem> fileItemList = fileRepository.findAllByFileGroupEquals(fileGroup);
            ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zipPath.toFile()));
            for(FileItem item : fileItemList) {
                String fileName = item.getId();
                Path filePath = Paths.get(UPLOAD_PATH, fileName);
                ZipEntry zipEntry = new ZipEntry(item.getOriginFileNm());
                zipOutputStream.putNextEntry(zipEntry);
                byte[] bytes = Files.readAllBytes(filePath);
                zipOutputStream.write(bytes, 0, bytes.length);
                zipOutputStream.closeEntry();
            }
            return zipPath;
        } catch (IOException e) {
            log.error("파일 다운로드 과정에서 오류가 발생하였습니다. : "+e.getMessage());
            return null;
        }
    }
}
