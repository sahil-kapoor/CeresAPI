package com.ceres.service.menu;

import com.ceres.domain.entity.human.Nutritionist;
import com.ceres.domain.entity.human.Patient;
import com.ceres.domain.entity.menu.AbstractMenu;
import com.ceres.domain.entity.menu.Menu;
import com.ceres.domain.entity.menu.MenuTemplate;
import com.ceres.service.menu.exception.MenuException;

import java.util.Set;

/**
 * Created by leonardo on 18/03/15.
 */
public interface MenuService {

    Menu getMenuById(Long id);

    MenuTemplate getTemplateById(Long id);

    MenuTemplate convertToTemplate(Menu menu);

    Menu createOrUpdate(AbstractMenu menu) throws MenuException;

    Set<Menu> getAllByNutritionist(Nutritionist n);

    Set<Menu> getAllByPatient(Patient p);

    void remove(Long id);

    boolean exists(Long id);

}
