package com.rezdy.lunch.service;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "recipe")
public class Recipe {
    
    @Id
    private String title;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "recipe_ingredient",
            joinColumns = @JoinColumn(
                    name = "recipe",
                    referencedColumnName = "title"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "ingredient",
                    referencedColumnName = "title"
            )
            )
    private List<Ingredient> ingredients;

    
    public String getTitle() {
        return title;
    }

    public Recipe setTitle(String title) {
        this.title = title;
        return this;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public Recipe setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
        return this;
    }
}
