package me.ronin.menu;

public class Menu {

    private static MenuItem[] menu;

    public static MenuItem[] items() {
        return menu;
    }

    static {
        menu = new MenuItem[]{
                new MenuItem.FountainSoda(),
                new MenuItem.MilkShake(),
                new MenuItem.SmashBurger(),
                new MenuItem.FrenchFries(),
                new MenuItem.OnionRings()};
    }
}
