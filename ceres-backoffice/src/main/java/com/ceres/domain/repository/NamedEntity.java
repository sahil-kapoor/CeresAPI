package com.ceres.domain.repository;

import java.util.List;

/**
 * Created by leonardo on 21/03/15.
 */
public interface NamedEntity<T> {
    T findByName(String name);

    List<T> findByNameContaining(String name);
}
