package com.ceres.service.menu;

import com.ceres.domain.entity.menu.AbstractMenu;
import com.ceres.domain.entity.menu.MenuContent;

import java.util.Set;

/**
 * Created by leonardo on 13/04/15.
 */
public interface MenuContentService {

    boolean exists(Long id);

    MenuContent createOrUpdate(MenuContent menuContent);

    void createOrUpdate(Set<MenuContent> menuContent);

    Set<MenuContent> getByMenu(AbstractMenu menu);

    MenuContent getById(Long id);

    void remove(Long contentId);
}
