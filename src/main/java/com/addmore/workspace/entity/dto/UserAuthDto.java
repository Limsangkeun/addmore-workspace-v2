package com.addmore.workspace.entity.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserAuthDto {
    // authority id
    String id;
    // authority name
    String name;

    boolean isChecked;

    String userId;
}
