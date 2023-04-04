package com.addmore.workspace.entity.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeptDto {
    String id;
    String name;
    LocalDate createdAt;
    String createdBy;
}
