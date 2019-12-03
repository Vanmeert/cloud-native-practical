package com.ezgroceries.shoppinglist.cocktails.external;

import java.util.List;

public class CocktailDBResponse {

    private List<DrinkResource> drinks;

    public List<DrinkResource> getDrinks() {
        return drinks;
    }

    public void setDrinks(List<DrinkResource> drinks) {
        this.drinks = drinks;
    }

    public static class DrinkResource {
        private String idDrink;
        private String strDrink;
        private String strGlass;
        private String strInstructions;
        private String strDrinkThumb;
        private String strIngredient1;
        private String strIngredient2;
        private String strIngredient3;
        private String strIngredient4;
        private String strIngredient5;
        private String strIngredient6;
        private String strIngredient7;
        private String strIngredient8;
        private String strIngredient9;
        private String strIngredient10;
        private String strIngredient11;
        private String strIngredient12;
        private String strIngredient13;
        private String strIngredient14;
        private String strIngredient15;

        public DrinkResource(
                String idDrink,
                String strDrink,
                String strGlass,
                String strInstructions,
                String strDrinkThumb,
                String strIngredient1,
                String strIngredient2,
                String strIngredient3,
                String strIngredient4,
                String strIngredient5,
                String strIngredient6,
                String strIngredient7,
                String strIngredient8,
                String strIngredient9,
                String strIngredient10,
                String strIngredient11,
                String strIngredient12,
                String strIngredient13,
                String strIngredient14,
                String strIngredient15
        ) {
            this.idDrink = idDrink;
            this.strDrink = strDrink;
            this.strGlass = strGlass;
            this.strInstructions = strInstructions;
            this.strDrinkThumb = strDrinkThumb;
            this.strIngredient1 = strIngredient1;
            this.strIngredient2 = strIngredient2;
            this.strIngredient3 = strIngredient3;
            this.strIngredient4 = strIngredient4;
            this.strIngredient5 = strIngredient5;
            this.strIngredient6 = strIngredient6;
            this.strIngredient7 = strIngredient7;
            this.strIngredient8 = strIngredient8;
            this.strIngredient9 = strIngredient9;
            this.strIngredient10 = strIngredient10;
            this.strIngredient11 = strIngredient11;
            this.strIngredient12 = strIngredient12;
            this.strIngredient13 = strIngredient13;
            this.strIngredient14 = strIngredient14;
            this.strIngredient15 = strIngredient15;
        }

        public String getIdDrink() {
            return idDrink;
        }

        public String getStrDrink() {
            return strDrink;
        }

        public String getStrGlass() {
            return strGlass;
        }

        public String getStrInstructions() {
            return strInstructions;
        }

        public String getStrDrinkThumb() {
            return strDrinkThumb;
        }

        public String getStrIngredient1() {
            return strIngredient1;
        }

        public String getStrIngredient2() {
            return strIngredient2;
        }

        public String getStrIngredient3() {
            return strIngredient3;
        }

        public String getStrIngredient4() {
            return strIngredient4;
        }

        public String getStrIngredient5() {
            return strIngredient5;
        }

        public String getStrIngredient6() {
            return strIngredient6;
        }

        public String getStrIngredient7() {
            return strIngredient7;
        }

        public String getStrIngredient8() {
            return strIngredient8;
        }

        public String getStrIngredient9() {
            return strIngredient9;
        }

        public String getStrIngredient10() {
            return strIngredient10;
        }

        public String getStrIngredient11() {
            return strIngredient11;
        }

        public String getStrIngredient12() {
            return strIngredient12;
        }

        public String getStrIngredient13() {
            return strIngredient13;
        }

        public String getStrIngredient14() {
            return strIngredient14;
        }

        public String getStrIngredient15() {
            return strIngredient15;
        }
    }
}
