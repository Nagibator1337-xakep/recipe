package com.pavelbelov.recipe.bootstrap;

import com.pavelbelov.recipe.domain.*;
import com.pavelbelov.recipe.repositories.CategoryRepository;
import com.pavelbelov.recipe.repositories.RecipeRepository;
import com.pavelbelov.recipe.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * Created by Pavel Belov on 27.09.2021
 */
@Slf4j
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
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        log.debug("Strapping Boots...");
        recipeRepository.saveAll(getRecipes());
    }

    // Getting Units of Measure with a 'description' value from data.sql
    private UnitOfMeasure getUnit(String description) {
        return unitOfMeasureRepository
                .findByDescription(description)
                .orElseThrow(() -> new RuntimeException("Unit of measure '" + description + "' not found"));
    }

    private List<Recipe> getRecipes() {
        List<Recipe> recipes = new ArrayList<>(2);

        log.debug("Getting Units of Measure values from Optionals");

        UnitOfMeasure tbspUnit = getUnit("Tablespoon");
        UnitOfMeasure tspUnit = getUnit("Teaspoon");
        UnitOfMeasure cloveUnit = getUnit("Clove");
        UnitOfMeasure lbsUnit = getUnit("Pound");
        UnitOfMeasure pcsUnit = getUnit("");
        UnitOfMeasure cupUnit = getUnit("Cup");
        UnitOfMeasure bunchUnit = getUnit("Bunch");
        UnitOfMeasure pinchUnit = getUnit("Pinch");

        log.debug("Units of Measure initialized");
        log.debug("Getting Categories from data.sql");

        Optional<Category> mexOpt = categoryRepository.findByDescription("Mexican");
        if (mexOpt.isEmpty()) throw new RuntimeException("Expected Category not found in the DB");

        Optional<Category> amOpt = categoryRepository.findByDescription("American");
        if (amOpt.isEmpty()) throw new RuntimeException("Expected Category not found in the DB");

        log.debug("Getting Category values from Optionals");
        Category mexCat = mexOpt.get();
        Category amCat = amOpt.get();

        //
        // Tacos Recipe:
        //
        Recipe grilledChickenTacos = new Recipe();

        log.debug("Saving Recipe directions from static/tacos.txt and static/tacosNotes.txt files to String parameters");
        String tacosDirectionsStr = null;
        String tacosNotesStr = null;
        try {
            log.debug("Reading recipe files - Tacos");
            tacosDirectionsStr = Files.readString(Path.of("src/main/resources/static/tacos.txt"), StandardCharsets.UTF_8);
            tacosNotesStr = Files.readString(Path.of("src/main/resources/static/tacosNotes.txt"), StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.debug("IOException on reading recipe directions files - Tacos");
            e.printStackTrace();
        }

        // Adding Notes
        Notes tacosNotes = new Notes();
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

        log.debug("Adding Ingredients to the Tacos Recipe");
        grilledChickenTacos.setIngredients(new HashSet<>());

        grilledChickenTacos.addIngredient(new Ingredient("Ancho chili powder", new BigDecimal(2),tbspUnit));
        grilledChickenTacos.addIngredient(new Ingredient("Dried oregano", new BigDecimal(1),tspUnit));
        grilledChickenTacos.addIngredient(new Ingredient("Dried cumin", new BigDecimal(1),tspUnit));
        grilledChickenTacos.addIngredient(new Ingredient("Sugar",  new BigDecimal(1),tspUnit));
        grilledChickenTacos.addIngredient(new Ingredient("Salt", new BigDecimal("0.5"),tspUnit));
        grilledChickenTacos.addIngredient(new Ingredient("Garlic, finely chopped", new BigDecimal(1),cloveUnit));
        grilledChickenTacos.addIngredient(new Ingredient("Orange zest, finely grated", new BigDecimal(1),tbspUnit));
        grilledChickenTacos.addIngredient(new Ingredient("Fresh-squeezed orange juice", new BigDecimal(3),tbspUnit));
        grilledChickenTacos.addIngredient(new Ingredient("Olive oil", new BigDecimal(2),tbspUnit));
        grilledChickenTacos.addIngredient(new Ingredient("Boneless chicken thighs", new BigDecimal("1.25"),lbsUnit));
        grilledChickenTacos.addIngredient(new Ingredient("Small corn tortillas", new BigDecimal(8),pcsUnit));
        grilledChickenTacos.addIngredient(new Ingredient("Packed baby arugula (3 ounces)", new BigDecimal(3),cupUnit));
        grilledChickenTacos.addIngredient(new Ingredient("Medium ripe avocados, sliced", new BigDecimal(2),pcsUnit));
        grilledChickenTacos.addIngredient(new Ingredient("Radishes, thinly sliced", new BigDecimal(2),pcsUnit));
        grilledChickenTacos.addIngredient(new Ingredient("Cherry tomatoes, halved", new BigDecimal(1),cupUnit));
        grilledChickenTacos.addIngredient(new Ingredient("Red onion, thinly sliced", new BigDecimal("0.25"),pcsUnit));
        grilledChickenTacos.addIngredient(new Ingredient("Roughly chopped cilantro", new BigDecimal(1),bunchUnit));
        grilledChickenTacos.addIngredient(new Ingredient("Sour cream thinned with 1/4 cup milk", new BigDecimal("0.5"),cupUnit));
        grilledChickenTacos.addIngredient(new Ingredient("Lime, cut into wedges", new BigDecimal(1),pcsUnit));

        log.debug("Adding Tacos Recipe to the Array of Recipes to be returned");
        recipes.add(grilledChickenTacos);

        //
        // Guacamole Recipe:
        //

        Recipe guacamole = new Recipe();

        log.debug("Saving Recipe directions from static/guac.txt and static/guacNotes.txt files to String parameters");
        String guacDirectionsStr = null;
        String guacNotesStr = null;
        try {
            log.debug("Reading recipe files - Guacamole");
            guacDirectionsStr = Files.readString(Path.of("src/main/resources/static/guac.txt"), StandardCharsets.UTF_8);
            guacNotesStr = Files.readString(Path.of("src/main/resources/static/guacNotes.txt"), StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.debug("IOException on reading recipe directions files - Guacamole");
            e.printStackTrace();
        }

        log.debug("Adding Notes");
        Notes guacNotes = new Notes();
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

        log.debug("Adding Ingredients to the Guac Recipe");
        guacamole.setIngredients(new HashSet<>());

        guacamole.addIngredient(new Ingredient("Ripe avocados", new BigDecimal(2),pcsUnit));
        guacamole.addIngredient(new Ingredient("Salt, plus more to taste", new BigDecimal("0.25"),tspUnit));
        guacamole.addIngredient(new Ingredient("Fresh lime or lemon juice", new BigDecimal(1),tbspUnit));
        guacamole.addIngredient(new Ingredient("Minced red onion or thinly sliced green onion", new BigDecimal(3),tbspUnit));
        guacamole.addIngredient(new Ingredient("Salt", new BigDecimal("0.5"),tspUnit));
        guacamole.addIngredient(new Ingredient("Serrano (or jalapeño) chilis, stems and seeds removed, minced", new BigDecimal(1),pcsUnit));
        guacamole.addIngredient(new Ingredient("Cilantro (leaves and tender stems), finely chopped", new BigDecimal(2),tbspUnit));
        guacamole.addIngredient(new Ingredient("Freshly ground black pepper", new BigDecimal(1),pinchUnit));
        guacamole.addIngredient(new Ingredient("Ripe tomato, chopped (optional)", new BigDecimal("0.5"),pcsUnit));
        guacamole.addIngredient(new Ingredient("Red radish or jicama slices for garnish (optional)", new BigDecimal(1),pcsUnit));
        guacamole.addIngredient(new Ingredient("Tortilla chips, to serve", new BigDecimal(1),pcsUnit));

        log.debug("Adding Guacamole Recipe to the Array of Recipes to be returned");
        recipes.add(guacamole);

        return recipes;
    }
}


