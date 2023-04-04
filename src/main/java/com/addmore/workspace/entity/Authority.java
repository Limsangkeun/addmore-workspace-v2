package com.addmore.workspace.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;

@Entity(name = "authority")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Authority extends BaseEntity implements GrantedAuthority {
    @Column(nullable = false, unique = true, length = 30)
    String name;
    @Override
    public String getAuthority() {
        return this.name;
    }
}
