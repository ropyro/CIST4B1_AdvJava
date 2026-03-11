package me.ronin.backofhouse.orders;

import me.ronin.frontofhouse.menu.MenuItem;

public class DineInOrder extends Order{

    private int tableNumber;

    public DineInOrder(int orderNumb, int tableNumber, MenuItem... items){
        super(orderNumb, items);
        this.tableNumber = tableNumber;
    }

    @Override
    public double getTotal(){
        return super.getTotal() + getEstimatedTip();
    }

    private double getEstimatedTip(){
        return super.getTotal() * 1.2;
    }
}
