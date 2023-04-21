package com.addmore.workspace.repository;

import com.addmore.workspace.entity.FileItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<FileItem, String> {
    public List<FileItem> findAllByFileGroupEquals(FileItem fileGroup);
}
