package com.pavelbelov.recipe.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Pavel Belov on 10.09.2021
 */
@Controller
public class IndexController {

    @RequestMapping({"","/","index","index.html"})
    public String getIndexPage() {
        return "index";
    }
}
