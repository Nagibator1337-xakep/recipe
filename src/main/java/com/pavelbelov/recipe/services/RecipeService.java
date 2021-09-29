package com.pavelbelov.recipe.services;

import com.pavelbelov.recipe.domain.Recipe;

import java.util.Set;

/**
 * Created by Pavel Belov on 28.09.2021
 */
public interface RecipeService {

    Set<Recipe> getRecipes();

}
