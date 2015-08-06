package com.ceres.domain.repository.parameter;

import com.ceres.domain.entity.parameter.Parameter;
import com.ceres.domain.repository.NamedEntity;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by leonardo on 21/03/15.
 */
@Repository
public interface ParameterRepository extends JpaRepository<Parameter, Long>, NamedEntity<Parameter> {

    @Override
    @CacheEvict(value = "parameter-findAll", allEntries = true)
    Parameter save(Parameter entity);

    @Override
    @Cacheable("parameter-findAll")
    List<Parameter> findAll();

    Parameter findByMnemonic(String mnemonic);

    int countByMnemonic(String mnemonic);
}
