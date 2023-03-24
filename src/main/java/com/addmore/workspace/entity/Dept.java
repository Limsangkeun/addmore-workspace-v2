package com.addmore.workspace.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity(name = "dept")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class Dept extends BaseEntity {
    String name;
}
