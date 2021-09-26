package com.pavelbelov.recipe.controllers;

import com.pavelbelov.recipe.domain.Category;
import com.pavelbelov.recipe.domain.UnitOfMeasure;
import com.pavelbelov.recipe.repositories.CategoryRepository;
import com.pavelbelov.recipe.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

/**
 * Created by Pavel Belov on 10.09.2021
 */
@Controller
public class IndexController {

    private CategoryRepository categoryRepository;
    private UnitOfMeasureRepository unitOfMeasureRepository;

    public IndexController(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @RequestMapping({"","/","index","index.html"})
    public String getIndexPage() {

        Optional<Category> categoryOptional = categoryRepository.findByDescription("Fast Food");
        Optional<UnitOfMeasure> unitOfMeasureOptional = unitOfMeasureRepository.findByDescription("Cup");

        System.out.println("Fast Food cat ID: " + categoryOptional.get().getId());
        System.out.println("Cup uom ID: " + unitOfMeasureOptional.get().getId());

        return "index";
    }
}
