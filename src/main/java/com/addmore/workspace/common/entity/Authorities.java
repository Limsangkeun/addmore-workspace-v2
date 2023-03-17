package com.addmore.workspace.common.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity(name = "Authorities")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Authorities extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String name;

}
