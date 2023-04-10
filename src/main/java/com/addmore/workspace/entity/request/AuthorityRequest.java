package com.addmore.workspace.entity.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class AuthorityRequest {
    String id;
    String name;
    LocalDate createdAt;
    String createdBy;
    String userId;
}
