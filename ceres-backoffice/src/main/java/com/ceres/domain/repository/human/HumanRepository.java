package com.ceres.domain.repository.human;

import com.ceres.domain.embedabble.Name;
import com.ceres.domain.entity.human.Human;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by leonardo on 12/03/15.
 */
public interface HumanRepository extends JpaRepository<Human, Long> {

    Human findByName(Name name);

}
