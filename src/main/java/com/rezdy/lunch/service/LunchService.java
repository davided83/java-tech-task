package com.rezdy.lunch.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;
import java.util.HashSet;

@Service
public class LunchService {

    @Autowired
    private EntityManager entityManager;

    private List<Recipe> recipesSorted;

    public List<Recipe> getNonExpiredRecipesOnDate(LocalDate date) {
        List<Recipe> recipes = loadRecipes(date);

        sortRecipes(recipes);
        return recipes;
    }

    /**
     * Sort recipes list by ingredient useBy date
     * @param recipes
     */
    private void sortRecipes(List<Recipe> recipes) {

        // Sort ingredients in the list by useBy
        for (Recipe rcp : recipes)
        {
            sortIngredientUseBy(rcp.getIngredients());
        }

        // Sort recipes by first ingredient useBy and BestBefore
        Collections.sort(recipes, new Comparator<Recipe>()
        {
            public int compare(Recipe arg0, Recipe arg1)
            {
                int result = 0;
                for (int i=0; i < arg0.getIngredients().size() && i < arg1.getIngredients().size(); i++)
                {
                    // asc
                    result = (arg0.getIngredients().get(i).getUseBy()).compareTo(arg1.getIngredients().get(i).getUseBy());
                    if (result == 0)
                    {
                        // desc
                        result = (arg1.getIngredients().get(i).getBestBefore()).compareTo(arg0.getIngredients().get(i).getBestBefore());
                    }
                }
                return result;
            }
        });
        recipesSorted = recipes;
    }

    /**
     * Load recipes that are not expired based on date from database 
     * @param date
     * @return
     */
    private List<Recipe> loadRecipes(LocalDate date) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Recipe> criteriaQuery = cb.createQuery(Recipe.class);
        Root<Recipe> recipeRoot = criteriaQuery.from(Recipe.class);

        CriteriaQuery<Recipe> query = criteriaQuery.select(recipeRoot);
        query.orderBy(cb.asc(recipeRoot.get("title")));

        Subquery<Recipe> nonExpiredIngredientSubquery = query.subquery(Recipe.class);
        Root<Recipe> nonExpiredIngredient = nonExpiredIngredientSubquery.from(Recipe.class);
        nonExpiredIngredientSubquery.select(nonExpiredIngredient);

        Predicate matchingRecipe = cb.equal(nonExpiredIngredient.get("title"), recipeRoot.get("title"));
        Predicate expiredIngredient = cb.lessThan(nonExpiredIngredient.join("ingredients").get("useBy"), date);

        Predicate allNonExpiredIngredients = cb.not(cb.exists(nonExpiredIngredientSubquery.where(matchingRecipe, expiredIngredient)));

        return entityManager.createQuery(query.where(allNonExpiredIngredients)).getResultList();        
    }

    /**
     * Sort ingrList by useBy date and Title
     * @param ingrList
     */
    private void sortIngredientUseBy (List<Ingredient> ingrList)
    {
        Collections.sort(ingrList, new Comparator<Ingredient>()
        {
            public int compare(Ingredient arg0, Ingredient arg1)
            {
                int result = 0;
                result = arg0.getUseBy().compareTo(arg1.getUseBy());
                if (result == 0)
                {
                    // desc
                    result = arg1.getBestBefore().compareTo(arg0.getBestBefore());
                }
                if (result == 0)
                {
                    result = arg0.getTitle().compareTo(arg1.getTitle());
                }
                return result;
            }
        });
    }
}
