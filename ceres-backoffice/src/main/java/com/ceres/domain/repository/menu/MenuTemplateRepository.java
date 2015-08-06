package com.ceres.domain.repository.menu;

import com.ceres.domain.entity.menu.MenuTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Leonardo on 19/01/2015.
 */
@Repository
public interface MenuTemplateRepository extends JpaRepository<MenuTemplate, Long> {

}
