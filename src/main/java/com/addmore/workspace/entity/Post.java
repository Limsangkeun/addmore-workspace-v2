package com.addmore.workspace.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Post extends BaseEntity {

    @Column(length = 150, nullable = false)
    String title;

    @Lob
    String content;

    @OneToOne
    @JoinColumn(name = "file_group_id")
    FileItem fileGroup;

    @OneToOne
    @JoinColumn(name = "writer_id")
    User writer;
}
