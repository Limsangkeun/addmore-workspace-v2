package com.addmore.workspace.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileItem extends BaseEntity {

    String originFileNm;
    String attFileNm;
    String attFilePath;
    long attFileSize = 0;

    @ManyToOne
    @JoinColumn(name = "file_group_id")
    FileItem fileGroup;
}
