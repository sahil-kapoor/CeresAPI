package com.ceres.domain.repository.human;

import com.ceres.domain.entity.human.SystemUser;
import com.ceres.domain.entity.human.UserCategory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Leonardo on 19/01/2015.
 */
@Repository
public interface UserRepository extends JpaRepository<SystemUser, Long> {

    @Cacheable("findByUsername")
    SystemUser findByUsername(String username);

    @Cacheable("existsByPassword")
    int countByPassword(String password);

    @Override
    @CacheEvict(value = "existsByPassword", allEntries = true)
    SystemUser save(SystemUser entity);

    List<SystemUser> findByRole(UserCategory role);

    int countByUsername(String username);
}
