package com.ceres.domain.repository.menu;

import com.ceres.domain.entity.menu.AbstractMenu;
import com.ceres.domain.entity.menu.MenuContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * Created by Leonardo on 19/01/2015.
 */
@Repository
public interface MenuContentRepository extends JpaRepository<MenuContent, Long> {

    Set<MenuContent> findByMenu(AbstractMenu menu);
}
