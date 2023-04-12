package com.addmore.workspace.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity(name = "user_authority")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserAuthority extends BaseEntity{

    @ManyToOne
    User user;

    @ManyToOne
    Authority authority;
}
