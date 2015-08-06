package com.ceres.domain.repository.script;

import com.ceres.domain.entity.human.Gender;
import com.ceres.domain.entity.script.Script;
import com.ceres.domain.entity.script.ScriptCategory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * Created by leonardo on 21/03/15.
 */
@Repository
public interface ScriptRepository extends JpaRepository<Script, Long> {

    Set<Script> findByMnemonic(String mnemonic);

    Script findOneByMnemonic(String mnemonic);

    Set<Script> findByMnemonicAndTargetGender(String mnemonic, Gender targetGender);

    Set<Script> findByDependenciesIn(Script script);

    @Cacheable("script-findByCategoryAndTargetGenderOrTargetGenderIsNull")
    Set<Script> findByCategoryAndTargetGenderOrTargetGenderIsNull(ScriptCategory category, Gender targetGender);

    @Override
    @CacheEvict(value = "script-findByCategoryAndTargetGenderOrTargetGenderIsNull", allEntries = true)
    Script save(Script entity);

}
