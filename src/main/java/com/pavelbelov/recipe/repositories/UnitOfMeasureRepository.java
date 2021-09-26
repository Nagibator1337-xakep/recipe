package com.pavelbelov.recipe.repositories;

import com.pavelbelov.recipe.domain.UnitOfMeasure;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Pavel Belov on 26.09.2021
 */
public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, Long> {
}
