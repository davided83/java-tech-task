package com.rezdy.lunch.service;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "ingredient")
public class Ingredient {
    
    @Id
    public String title;  

    private LocalDate bestBefore;    

    private LocalDate useBy;
    
    public String getTitle() {
        return title;
    }

    public Ingredient setTitle(String title) {
        this.title = title;
        return this;
    }

    
    public LocalDate getBestBefore() {
        return bestBefore;
    }

    public Ingredient setBestBefore(LocalDate bestBefore) {
        this.bestBefore = bestBefore;
        return this;
    }

    
    public LocalDate getUseBy() {
        return useBy;
    }

    public Ingredient setUseBy(LocalDate useBy) {
        this.useBy = useBy;
        return this;
    }
}
