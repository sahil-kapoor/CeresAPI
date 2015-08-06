package com.ceres.service.user;

import com.ceres.domain.entity.human.Human;
import com.ceres.domain.entity.human.SystemUser;
import com.ceres.service.user.exception.AuthenticationException;

import java.util.List;

/**
 * Created by leonardo on 22/03/15.
 */
public interface UserService {

    SystemUser findUserByToken(String token);

    boolean existsByUsername(String username);

    String validateAndGetToken(String password, String username) throws AuthenticationException;

    void validate(String token) throws AuthenticationException;

    boolean exists(Long id);

    boolean humanExists(Long id);

    Human getHumanById(Long id);

    SystemUser getById(Long id);

    List<SystemUser> getAll();

    SystemUser update(SystemUser user);

    SystemUser create(SystemUser user);

    void remove(Long id);
}
