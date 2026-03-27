package me.ronin;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Animal[] animals = {
                new Animal("Jerry", Animal.Species.GIRAFFE, 5),
                new Animal("Rebeca", Animal.Species.GORILLA, 6),
                new Animal("Montegue", Animal.Species.LION, 3),
                new Animal("Pinguino", Animal.Species.PENGUIN, 10),
                new Animal("Miranda", Animal.Species.ZEBRA, 1),
                new Animal("Cassidy", Animal.Species.GIRAFFE, 4),
                new Animal("Tommy", Animal.Species.GORILLA, 9),
                new Animal("Jerome", Animal.Species.LION, 2),
                new Animal("Kiki", Animal.Species.PENGUIN, 7),
                new Animal("Gloria", Animal.Species.ZEBRA, 1),
                new Animal("Jessica", Animal.Species.GIRAFFE, 2),
                new Animal("Bob", Animal.Species.GORILLA, 3),
                new Animal("Adam", Animal.Species.LION, 6),
                new Animal("Helen", Animal.Species.PENGUIN, 3),
                new Animal("Tina", Animal.Species.ZEBRA, 9),
                new Animal("Cheeto", Animal.Species.GIRAFFE, 2),
                new Animal("Baba", Animal.Species.GORILLA, 3),
                new Animal("Leo", Animal.Species.LION, 6),
                new Animal("Pipsqueak", Animal.Species.PENGUIN, 3),
                new Animal("Stripey", Animal.Species.ZEBRA, 9),
                new Animal("Tallboy", Animal.Species.GIRAFFE, 2),
                new Animal("Nick", Animal.Species.GORILLA, 3),
                new Animal("Jared", Animal.Species.LION, 6),
                new Animal("Andrea", Animal.Species.PENGUIN, 3),
                new Animal("Liz", Animal.Species.ZEBRA, 9)
        };

        CareFacility careFacility = new CareFacility(animals);
        careFacility.startCare();
    }
}
