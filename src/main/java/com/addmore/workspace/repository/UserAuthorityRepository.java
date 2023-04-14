package com.addmore.workspace.repository;

import com.addmore.workspace.entity.Authority;
import com.addmore.workspace.entity.User;
import com.addmore.workspace.entity.UserAuthority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserAuthorityRepository extends JpaRepository<UserAuthority, String> {
    List<UserAuthority> findUserAuthoritiesByUserEquals(User user);
    Optional<UserAuthority> findUserAuthorityByUserEqualsAndAuthorityEquals(User user, Authority auth);
    List<UserAuthority> findAllByAuthorityEquals(Authority auth);
}
