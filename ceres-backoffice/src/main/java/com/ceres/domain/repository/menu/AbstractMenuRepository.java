package com.ceres.domain.repository.menu;

import com.ceres.domain.entity.menu.AbstractMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Leonardo on 19/01/2015.
 */
@Repository
public interface AbstractMenuRepository extends JpaRepository<AbstractMenu, Long> {
}
