package me.ronin.menu;

import me.ronin.kitchen.PrepStep;

import java.util.List;

public class MenuItem {

    private List<PrepStep> prepSteps;
    private String name;
    private float price;

    public MenuItem(String name, float price, PrepStep... prepSteps) {
        this.name = name;
        this.price = price;
        this.prepSteps = List.of(prepSteps);
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

    public List<PrepStep> getPrepSteps() {
        return this.prepSteps;
    }


    static class FountainSoda extends MenuItem {
        public FountainSoda() {
            super("Fountain Soda", 3.50f, new PrepStep("Pour the soda"));
        }
    }

    static class MilkShake extends MenuItem {
        public MilkShake(){
            super("Milk Shake", 6.99f,
                    new PrepStep("Blend ingredients"),
                    new PrepStep("Pour milk shake"));
        }
    }

    static class SmashBurger extends MenuItem {
        public SmashBurger() {
            super("Smash Burger", 18.99f,
                    new PrepStep("Smash the patty"),
                    new PrepStep("Assemble the burger"),
                    new PrepStep("Wrap burger in paper"));
        }
    }

    static class FrenchFries extends MenuItem {
        public FrenchFries(){
            super("French Fries", 4.50f,
                    new PrepStep("Cut potatoes"),
                    new PrepStep("Deep fry"));
        }
    }

    static class OnionRings extends MenuItem {
        public OnionRings(){
            super("Onion Rings", 4.50f,
                    new PrepStep("Cut onions"),
                    new PrepStep("Deep fry"));
        }
    }
}
