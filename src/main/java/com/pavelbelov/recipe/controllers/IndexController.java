package com.pavelbelov.recipe.controllers;

import com.pavelbelov.recipe.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Pavel Belov on 10.09.2021
 */
@Slf4j
@Controller
public class IndexController {

    private RecipeService recipeService;

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"","/","index","index.html"})
    public String getIndexPage(Model model) {
        log.debug("Getting Index page...");

        model.addAttribute("recipes",recipeService.getRecipes());
        return "index";
    }
}