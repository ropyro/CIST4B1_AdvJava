package me.ronin.kitchen.orders;

import me.ronin.menu.MenuItem;

public class DineInOrder extends Order{

    private int tableNumber;

    public DineInOrder(int orderNumb, int tableNumber, MenuItem... items){
        super(orderNumb, items);
        this.tableNumber = tableNumber;
    }

    @Override
    public double getTotal(){
        return super.getTotal() + getTip();
    }

    public double getTip(){
        return super.getTotal() * (0.05 + Math.random() * 0.25);
    }

    public int getTableNumber() {
        return tableNumber;
    }
}
