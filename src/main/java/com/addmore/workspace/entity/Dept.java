package com.addmore.workspace.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity(name = "dept")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Dept extends BaseEntity {
    String name;
}
