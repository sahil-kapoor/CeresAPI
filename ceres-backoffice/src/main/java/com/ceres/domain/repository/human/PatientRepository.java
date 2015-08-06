package com.ceres.domain.repository.human;

import com.ceres.domain.entity.human.Patient;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by leonardo on 17/03/15.
 */
@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    @Override
    @Cacheable("patient-findAll")
    List<Patient> findAll();

    @Override
    @Caching(evict = {@CacheEvict(value = "user-findAll", allEntries = true), @CacheEvict(value = "patient-findAll", allEntries = true)})
    Patient save(Patient entity);

    @Override
    @Caching(evict = {@CacheEvict(value = "user-findAll", allEntries = true), @CacheEvict(value = "patient-findAll", allEntries = true)})
    void delete(Long aLong);
}

