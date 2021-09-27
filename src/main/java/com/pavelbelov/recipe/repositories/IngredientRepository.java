package com.pavelbelov.recipe.repositories;

import com.pavelbelov.recipe.domain.Ingredient;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Pavel Belov on 27.09.2021
 */
public interface IngredientRepository extends CrudRepository<Ingredient, Long> {
}
