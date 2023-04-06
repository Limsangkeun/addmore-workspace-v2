package com.addmore.workspace.entity.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class UserSearchParam {
    String name;
    int page = 0;
    int size = 50;
}
