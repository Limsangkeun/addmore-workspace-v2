package com.addmore.workspace.entity.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequest {
    String id;
    String username;
    String password;
    String name;
    String email;
    LocalDate birth;
    LocalDate joinDate;
}
