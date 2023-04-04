package com.addmore.workspace.auth;

import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    String id;
    String username;
    String password;
}
