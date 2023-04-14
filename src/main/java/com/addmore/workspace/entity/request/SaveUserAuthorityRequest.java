package com.addmore.workspace.entity.request;

import com.addmore.workspace.entity.UserAuthority;
import com.addmore.workspace.entity.dto.UserAuthDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class SaveUserAuthorityRequest {
    List<UserAuthDto> userAuthDtoList;
}
