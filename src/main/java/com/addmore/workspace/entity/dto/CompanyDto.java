package com.addmore.workspace.entity.dto;

import com.addmore.workspace.entity.enums.CompanyStatus;
import com.addmore.workspace.entity.enums.MapType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompanyDto {
    String id;
    String code;
    String tel;
    String addr;
    String roadAddr;
    String homePage;
    String detailUrl;
    String category;
    CompanyStatus status;
    MapType mapType;
    LocalDate regDate;
}
