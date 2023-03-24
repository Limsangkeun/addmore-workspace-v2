package com.addmore.workspace.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
class UserAuthorities extends BaseEntity{

    @ManyToOne
    User user;

    @ManyToOne
    Authority authority;
}
