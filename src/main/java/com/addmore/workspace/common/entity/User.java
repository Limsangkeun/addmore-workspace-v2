package com.addmore.workspace.common.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity(name = "USERS")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String username;
    String password;
    String name;
    LocalDate birth;
    LocalDateTime joinDate;
    boolean enabled;

    @ManyToOne
    @JoinColumn()
    Dept dept;
}
