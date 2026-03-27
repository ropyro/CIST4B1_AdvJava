package me.ronin;

public class Animal {
    private String name;
    private Species species;
    private int careLevel;

    public Animal(String name, Species species, int careLevel) {
        this.name = name;
        this.species = species;
        this.careLevel = careLevel;
    }

    public void deliverCare(){
        this.careLevel = 1;
    }

    public void delayCare(){
        if(careLevel < 10)
            this.careLevel += 1;
    }

    public String getName() {
        return name;
    }

    public Species getSpecies() {
        return species;
    }

    public int getCareLevel() {
        return careLevel;
    }

    public enum Species {
        LION, PENGUIN, ZEBRA, GORILLA, GIRAFFE
    }
}
