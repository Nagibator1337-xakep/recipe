package com.pavelbelov.recipe.repositories;

import com.pavelbelov.recipe.domain.Category;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Pavel Belov on 26.09.2021
 */
public interface CategoryRepository extends CrudRepository<Category, Long> {
        }
