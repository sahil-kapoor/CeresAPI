package com.ceres.service.menu;

import com.ceres.domain.entity.human.Nutritionist;
import com.ceres.domain.entity.human.Patient;
import com.ceres.domain.entity.menu.AbstractMenu;
import com.ceres.domain.entity.menu.Menu;
import com.ceres.domain.entity.menu.MenuTemplate;
import com.ceres.domain.repository.menu.AbstractMenuRepository;
import com.ceres.domain.repository.menu.MenuRepository;
import com.ceres.domain.repository.menu.MenuTemplateRepository;
import com.ceres.service.human.NutritionistService;
import com.ceres.service.human.PatientService;
import com.ceres.service.menu.exception.MenuException;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Set;

/**
 * Created by leonardo on 18/03/15.
 */
@Service
public final class MenuServiceImpl implements MenuService {

    private MenuRepository menuRepository;

    private MenuTemplateRepository menuTemplateRepository;

    private AbstractMenuRepository abstractMenuRepository;

    private PatientService patientService;

    private NutritionistService nutritionistService;

    @Inject
    public MenuServiceImpl(MenuRepository menuRepository, MenuTemplateRepository menuTemplateRepository,
                           AbstractMenuRepository abstractMenuRepository, PatientService patientService,
                           NutritionistService nutritionistService) {
        this.menuRepository = menuRepository;
        this.menuTemplateRepository = menuTemplateRepository;
        this.abstractMenuRepository = abstractMenuRepository;
        this.patientService = patientService;
        this.nutritionistService = nutritionistService;
    }

    @Override
    public Menu getMenuById(Long id) {
        return menuRepository.findOne(id);
    }

    @Override
    public MenuTemplate getTemplateById(Long id) {
        return menuTemplateRepository.findOne(id);
    }

    @Override
    public MenuTemplate convertToTemplate(Menu menu) {
        return null;
    }

    @Override
    public Menu createOrUpdate(AbstractMenu menu) throws MenuException {
        validateMenu(menu);
        return (Menu) abstractMenuRepository.save(menu);
    }

    @Override
    public Set<Menu> getAllByNutritionist(Nutritionist n) {
        return menuRepository.findByNutritionist(n);
    }

    @Override
    public Set<Menu> getAllByPatient(Patient p) {
        return menuRepository.findByPatient(p);
    }

    @Override
    public void remove(Long id) {
        menuRepository.delete(id);
    }

    @Override
    public boolean exists(Long id) {
        return abstractMenuRepository.exists(id);
    }

    private void validateMenu(AbstractMenu menu) throws MenuException {
        // TODO
    }

}
