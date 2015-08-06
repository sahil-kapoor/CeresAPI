package com.ceres.service.menu;

import com.ceres.domain.entity.menu.AbstractMenu;
import com.ceres.domain.entity.menu.MenuContent;
import com.ceres.domain.repository.menu.MenuContentRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Set;

/**
 * Created by leonardo on 13/04/15.
 */
@Service
public class MenuContentServiceImpl implements MenuContentService {

    private MenuContentRepository menuContentRepository;

    private MenuService menuService;

    @Inject
    public MenuContentServiceImpl(MenuContentRepository menuContentRepository, MenuService menuService) {
        this.menuContentRepository = menuContentRepository;
        this.menuService = menuService;
    }

    @Override
    public boolean exists(Long id) {
        return menuContentRepository.exists(id);
    }

    @Override
    public MenuContent createOrUpdate(MenuContent menuContent) {
        validateMenuContent(menuContent);
        return menuContentRepository.save(menuContent);
    }

    @Override
    public void createOrUpdate(Set<MenuContent> menuContents) {
        menuContents.forEach(k -> {
            validateMenuContent(k);
            menuContentRepository.save(k);
        });
    }

    @Override
    public Set<MenuContent> getByMenu(AbstractMenu menu) {
        return menuContentRepository.findByMenu(menu);
    }

    @Override
    public MenuContent getById(Long id) {
        return menuContentRepository.findOne(id);
    }

    @Override
    public void remove(Long contentId) {
        menuContentRepository.delete(contentId);
    }

    private void validateMenuContent(MenuContent menuContent) {
        // TODO
    }

}
