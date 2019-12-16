package com.ezgroceries.shoppinglist.db.entities;

import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "cocktail")
public class CocktailEntity {

    @Id
    private UUID id;

    @Column(name="id_drink")
    private String idDrink;

    private String name;

    private String glass;

    private String instructions;

    private String image;

    @Convert(converter = StringSetConverter.class)
    private Set<String> ingredients;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getIdDrink() {
        return idDrink;
    }

    public void setIdDrink(String idDrink) {
        this.idDrink = idDrink;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<String> ingredients) {
        this.ingredients = ingredients;
    }

    public String getGlass() {
        return glass;
    }

    public void setGlass(String glass) {
        this.glass = glass;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Converter
    private static class StringSetConverter implements AttributeConverter<Set<String>, String> {

        @Override
        public String convertToDatabaseColumn(Set<String> set) {
            if(!CollectionUtils.isEmpty(set)) {
                return "," + String.join(",", set) + ",";
            } else {
                return null;
            }
        }

        @Override
        public Set<String> convertToEntityAttribute(String joined) {
            if(joined != null) {
                String values = joined.substring(1, joined.length() - 1); //Removes leading and trailing commas
                return new HashSet<>(Arrays.asList(values.split(",")));
            }
            return new HashSet<>();
        }
    }
}
