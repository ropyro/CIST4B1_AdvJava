package me.ronin.kitchen.orders;

import me.ronin.menu.MenuItem;

public class TakeOutOrder extends Order {

    public static final double PACKAGING_FEE = 2.50;

    public TakeOutOrder(int orderNumb, MenuItem... items){
        super(orderNumb, items);
    }

    @Override
    public double getTotal() {
        return super.getTotal() + PACKAGING_FEE;
    }
}
