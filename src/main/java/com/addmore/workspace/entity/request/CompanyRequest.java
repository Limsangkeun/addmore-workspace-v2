package com.addmore.workspace.entity.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompanyRequest {
    String id;
    String name;

    int page = 0;

    int size = 50;
}
