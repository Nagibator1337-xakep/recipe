package com.pavelbelov.recipe.bootstrap;

import com.pavelbelov.recipe.domain.*;
import com.pavelbelov.recipe.repositories.CategoryRepository;
import com.pavelbelov.recipe.repositories.RecipeRepository;
import com.pavelbelov.recipe.repositories.UnitOfMeasureRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * Created by Pavel Belov on 27.09.2021
 */
@Component
public class RecipeBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final RecipeRepository recipeRepository;
    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public RecipeBootstrap(RecipeRepository recipeRepository,
                           CategoryRepository categoryRepository,
                           UnitOfMeasureRepository unitOfMeasureRepository) {
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        recipeRepository.saveAll(getRecipes());
    }

    private List<Recipe> getRecipes() {
        List<Recipe> recipes = new ArrayList<>(2);

        // Getting Units of Measure from data.sql
        Optional<UnitOfMeasure> tbspOpt = unitOfMeasureRepository.findByDescription("Tablespoon");
        if (tbspOpt.isEmpty()) throw new RuntimeException("Expected Unit of Measure not found in the DB");

        Optional<UnitOfMeasure> tspOpt = unitOfMeasureRepository.findByDescription("Teaspoon");
        if (tspOpt.isEmpty()) throw new RuntimeException("Expected Unit of Measure not found in the DB");

        Optional<UnitOfMeasure> cloveOpt = unitOfMeasureRepository.findByDescription("Clove");
        if (cloveOpt.isEmpty()) throw new RuntimeException("Expected Unit of Measure not found in the DB");

        Optional<UnitOfMeasure> lbsOpt = unitOfMeasureRepository.findByDescription("Pound");
        if (lbsOpt.isEmpty()) throw new RuntimeException("Expected Unit of Measure not found in the DB");

        Optional<UnitOfMeasure> pcsOpt = unitOfMeasureRepository.findByDescription("");
        if (pcsOpt.isEmpty()) throw new RuntimeException("Expected Unit of Measure not found in the DB");

        Optional<UnitOfMeasure> cupOpt = unitOfMeasureRepository.findByDescription("Cup");
        if (cupOpt.isEmpty()) throw new RuntimeException("Expected Unit of Measure not found in the DB");

        Optional<UnitOfMeasure> bunchOpt = unitOfMeasureRepository.findByDescription("Bunch");
        if (bunchOpt.isEmpty()) throw new RuntimeException("Expected Unit of Measure not found in the DB");

        Optional<UnitOfMeasure> pinchOpt = unitOfMeasureRepository.findByDescription("Pinch");
        if (pinchOpt.isEmpty()) throw new RuntimeException("Expected Unit of Measure not found in the DB");

        // Getting UOM values from Optionals
        UnitOfMeasure tbspUnit = tbspOpt.get();
        UnitOfMeasure tspUnit = tspOpt.get();
        UnitOfMeasure cloveUnit = cloveOpt.get();
        UnitOfMeasure lbsUnit = lbsOpt.get();
        UnitOfMeasure pcsUnit = pcsOpt.get();
        UnitOfMeasure cupUnit = cupOpt.get();
        UnitOfMeasure bunchUnit = bunchOpt.get();
        UnitOfMeasure pinchUnit = pinchOpt.get();

        // Getting Categories from data.sql
        Optional<Category> mexOpt = categoryRepository.findByDescription("Mexican");
        if (mexOpt.isEmpty()) throw new RuntimeException("Expected Category not found in the DB");

        Optional<Category> amOpt = categoryRepository.findByDescription("American");
        if (amOpt.isEmpty()) throw new RuntimeException("Expected Category not found in the DB");

        // Getting Category values from Optionals
        Category mexCat = mexOpt.get();
        Category amCat = amOpt.get();

        //
        // Tacos Recipe:
        //

        Recipe grilledChickenTacos = new Recipe();

        // Saving Recipe directions from static/tacos.txt and static/tacosNotes.txt files to String parameters
        String tacosDirectionsStr = null;
        String tacosNotesStr = null;
        try {
            tacosDirectionsStr = Files.readString(Path.of("src/main/resources/static/tacos.txt"), StandardCharsets.UTF_8);
            tacosNotesStr = Files.readString(Path.of("src/main/resources/static/tacosNotes.txt"), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Adding Notes
        Notes tacosNotes = new Notes();
        tacosNotes.setRecipe(grilledChickenTacos);
        tacosNotes.setRecipeNotes(tacosNotesStr);

        grilledChickenTacos.setDescription("Spicy Grilled Chicken Tacos");
        grilledChickenTacos.setPrepTime(20);
        grilledChickenTacos.setCookTime(15);
        grilledChickenTacos.setServings(5);
        grilledChickenTacos.setSource("Simply Recipes");
        grilledChickenTacos.setUrl("https://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos");

        grilledChickenTacos.setDirections(tacosDirectionsStr);
        grilledChickenTacos.setDifficulty(Difficulty.EASY);
        grilledChickenTacos.setNotes(tacosNotes);

        grilledChickenTacos.setCategories(new HashSet<>());
        grilledChickenTacos.getCategories().add(mexCat);
        grilledChickenTacos.getCategories().add(amCat);


        // Adding Ingredients to the Recipe
        grilledChickenTacos.setIngredients(new HashSet<>());
        grilledChickenTacos.getIngredients().add(
                new Ingredient("Ancho chili powder",
                        new BigDecimal(2),tbspUnit, grilledChickenTacos));
        grilledChickenTacos.getIngredients().add(
                new Ingredient("Dried oregano",
                        new BigDecimal(1),tspUnit, grilledChickenTacos));
        grilledChickenTacos.getIngredients().add(
                new Ingredient("Dried cumin",
                        new BigDecimal(1),tspUnit, grilledChickenTacos));
        grilledChickenTacos.getIngredients().add(
                new Ingredient("Sugar",
                        new BigDecimal(1),tspUnit, grilledChickenTacos));
        grilledChickenTacos.getIngredients().add(
                new Ingredient("Salt",
                        new BigDecimal("0.5"),tspUnit, grilledChickenTacos));
        grilledChickenTacos.getIngredients().add(
                new Ingredient("Garlic, finely chopped",
                        new BigDecimal(1),cloveUnit, grilledChickenTacos));
        grilledChickenTacos.getIngredients().add(
                new Ingredient("Orange zest, finely grated",
                        new BigDecimal(1),tbspUnit, grilledChickenTacos));
        grilledChickenTacos.getIngredients().add(
                new Ingredient("Fresh-squeezed orange juice",
                        new BigDecimal(3),tbspUnit, grilledChickenTacos));
        grilledChickenTacos.getIngredients().add(
                new Ingredient("Olive oil",
                        new BigDecimal(2),tbspUnit, grilledChickenTacos));
        grilledChickenTacos.getIngredients().add(
                new Ingredient("Boneless chicken thighs",
                        new BigDecimal("1.25"),lbsUnit, grilledChickenTacos));
        grilledChickenTacos.getIngredients().add(
                new Ingredient("Small corn tortillas",
                        new BigDecimal(8),pcsUnit, grilledChickenTacos));
        grilledChickenTacos.getIngredients().add(
                new Ingredient("Packed baby arugula (3 ounces)",
                        new BigDecimal(3),cupUnit, grilledChickenTacos));
        grilledChickenTacos.getIngredients().add(
                new Ingredient("Medium ripe avocados, sliced",
                        new BigDecimal(2),pcsUnit, grilledChickenTacos));
        grilledChickenTacos.getIngredients().add(
                new Ingredient("Radishes, thinly sliced",
                        new BigDecimal(2),pcsUnit, grilledChickenTacos));
        grilledChickenTacos.getIngredients().add(
                new Ingredient("Cherry tomatoes, halved",
                        new BigDecimal(1),cupUnit, grilledChickenTacos));
        grilledChickenTacos.getIngredients().add(
                new Ingredient("Red onion, thinly sliced",
                        new BigDecimal("0.25"),pcsUnit, grilledChickenTacos));
        grilledChickenTacos.getIngredients().add(
                new Ingredient("Roughly chopped cilantro",
                        new BigDecimal(1),bunchUnit, grilledChickenTacos));
        grilledChickenTacos.getIngredients().add(
                new Ingredient("Sour cream thinned with 1/4 cup milk",
                        new BigDecimal("0.5"),cupUnit, grilledChickenTacos));
        grilledChickenTacos.getIngredients().add(
                new Ingredient("Lime, cut into wedges",
                        new BigDecimal(1),pcsUnit, grilledChickenTacos));

        // Adding Tacos Recipe to the Array of Recipes to be returned
        recipes.add(grilledChickenTacos);

        //
        // Guacamole Recipe:
        //

        Recipe guacamole = new Recipe();

        // Saving Recipe directions from static/guac.txt and static/guacNotes.txt files to String parameters
        String guacDirectionsStr = null;
        String guacNotesStr = null;
        try {
            guacDirectionsStr = Files.readString(Path.of("src/main/resources/static/guac.txt"), StandardCharsets.UTF_8);
            guacNotesStr = Files.readString(Path.of("src/main/resources/static/guacNotes.txt"), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Adding Notes
        Notes guacNotes = new Notes();
        guacNotes.setRecipe(guacamole);
        guacNotes.setRecipeNotes(guacNotesStr);

        guacamole.setDescription("How to Make the Best Guacamole");
        guacamole.setPrepTime(10);
        guacamole.setCookTime(0);
        guacamole.setServings(3);
        guacamole.setSource("Simply Recipes");
        guacamole.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/");

        guacamole.setDirections(guacDirectionsStr);
        guacamole.setDifficulty(Difficulty.EASY);
        guacamole.setNotes(guacNotes);

        guacamole.setCategories(new HashSet<>());
        guacamole.getCategories().add(mexCat);
        guacamole.getCategories().add(amCat);


        // Adding Ingredients to the Recipe
        guacamole.setIngredients(new HashSet<>());
        guacamole.getIngredients().add(
                new Ingredient("Ripe avocados",
                        new BigDecimal(2),pcsUnit, guacamole));
        guacamole.getIngredients().add(
                new Ingredient("Salt, plus more to taste",
                        new BigDecimal("0.25"),tspUnit, guacamole));
        guacamole.getIngredients().add(
                new Ingredient("Fresh lime or lemon juice",
                        new BigDecimal(1),tbspUnit, guacamole));
        guacamole.getIngredients().add(
                new Ingredient("Minced red onion or thinly sliced green onion",
                        new BigDecimal(3),tbspUnit, guacamole));
        guacamole.getIngredients().add(
                new Ingredient("Salt",
                        new BigDecimal("0.5"),tspUnit, guacamole));
        guacamole.getIngredients().add(
                new Ingredient("Serrano (or jalapeño) chilis, stems and seeds removed, minced",
                        new BigDecimal(1),pcsUnit, guacamole));
        guacamole.getIngredients().add(
                new Ingredient("Cilantro (leaves and tender stems), finely chopped",
                        new BigDecimal(2),tbspUnit, guacamole));
        guacamole.getIngredients().add(
                new Ingredient("Freshly ground black pepper",
                        new BigDecimal(1),pinchUnit, guacamole));
        guacamole.getIngredients().add(
                new Ingredient("Ripe tomato, chopped (optional)",
                        new BigDecimal("0.5"),pcsUnit, guacamole));
        guacamole.getIngredients().add(
                new Ingredient("Red radish or jicama slices for garnish (optional)",
                        new BigDecimal(1),pcsUnit, guacamole));
        guacamole.getIngredients().add(
                new Ingredient("Tortilla chips, to serve",
                        new BigDecimal(1),pcsUnit, grilledChickenTacos));

        // Adding Guacamole Recipe to the Array of Recipes to be returned
        recipes.add(guacamole);

        return recipes;
    }
}

