package me.ronin.backofhouse.orders;

import me.ronin.frontofhouse.menu.MenuItem;

import java.util.Date;

public class TakeOutOrder extends Order {

    private Date pickupTime;

    public TakeOutOrder(int orderNumb, Date pickupTime, MenuItem... items){
        super(orderNumb, items);
        this.pickupTime = pickupTime;
    }
}
