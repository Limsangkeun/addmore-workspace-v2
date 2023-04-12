package com.addmore.workspace.entity.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {
    String id;
    String username;
    String name;
    String email;
    String deptId;
    LocalDate birth;
    LocalDate joinDate;
}
