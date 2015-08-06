package com.ceres.domain.repository.menu;

import com.ceres.domain.entity.human.Nutritionist;
import com.ceres.domain.entity.human.Patient;
import com.ceres.domain.entity.menu.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * Created by Leonardo on 19/01/2015.
 */
@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

    Set<Menu> findByNutritionist(Nutritionist n);

    Set<Menu> findByPatient(Patient p);

}
