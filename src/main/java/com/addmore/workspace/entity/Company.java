package com.addmore.workspace.entity;

import com.addmore.workspace.entity.enums.CompanyStatus;
import com.addmore.workspace.entity.enums.MapType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Company extends BaseEntity {
    @Column(length = 30)
    String code;
    @Column(length = 50)
    String name;
    @Column(length = 50)
    String tel;
    String addr;
    String roadAddr;
    @Column(length = 500)
    String homePage;
    @Column(length = 100)
    String category;
    boolean is114;

    @Enumerated(EnumType.STRING)
    CompanyStatus status;

    @Enumerated(EnumType.STRING)
    MapType mapType;

    LocalDate regDate;
}
