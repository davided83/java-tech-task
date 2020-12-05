package com.rezdy.lunch.controller;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.ArrayList;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Unit test class
 */
@SpringBootTest
public class LunchControllerTest {

    @Autowired
    LunchService service;

    List<Recipe> result;

    @Test
    public void getTestNonExpiredRecipesPastDate() {
        var currentDate = LocalDate.of(1998, 01, 01);
        recipesList = service.getNonExpiredRecipesOnDate(currentDate);
        
        // result set should have 5 recipes
        Assert.isTrue(5, recipesList.size(), "result set is not correct size");
        Assert.isTrue("Omelette", ((Recipe)recipesList.get(0)).getTitle(), "result set order is not correct");
    }

    @Test
    public void getTestNonExpiredRecipesFutureDate() {
        var currentDate = LocalDate.of(2019, 01, 01);
        recipesList = service.getNonExpiredRecipesOnDate(currentDate);
        
        // result set should have 5 recipes
        Assert.isTrue(3, recipesList.size(), "result set is not correct size");
        Assert.isTrue("Fry-up", ((Recipe)recipesList.get(0)).getTitle(), "result set order is not correct");
    }
}