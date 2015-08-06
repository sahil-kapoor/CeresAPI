package com.ceres.domain.repository.script;

import com.ceres.domain.entity.script.ScriptCategory;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by leonardo on 21/03/15.
 */
public interface ScriptCategoryRepository extends JpaRepository<ScriptCategory, Long> {
    ScriptCategory findByName(String name);

    int countByName(String name);
}
