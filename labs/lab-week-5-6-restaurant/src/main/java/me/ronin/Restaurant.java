package me.ronin;

import me.ronin.datastructures.Queue;
import me.ronin.kitchen.Kitchen;
import me.ronin.menu.Menu;
import me.ronin.menu.MenuItem;
import me.ronin.kitchen.orders.DineInOrder;
import me.ronin.kitchen.orders.Order;
import me.ronin.kitchen.orders.TakeOutOrder;

import java.util.ArrayList;

public class Restaurant {

    public ArrayList<Order> completedOrders;
    public Queue<Customer> waitingCustomers;
    private Kitchen kitchen;

    private boolean[] tables;

    public boolean open = false;

    public int currentTimeStep = 0;
    public int totalCustomersProcessed = 0;
    public int orderCounter = 1;

    public int desiredTimeSteps;

    public Restaurant(int desiredTimeSteps, int maxTables) {
        completedOrders = new ArrayList<>();
        waitingCustomers = new Queue<>(100);
        kitchen = new Kitchen(this);

        this.tables = new boolean[maxTables];
        this.desiredTimeSteps = desiredTimeSteps;
    }

    public void open() {
        System.out.println("\nThe Restaurant is open! \n");
        open = true;
        currentTimeStep = 1;
        while (open) {
            processTimeStep();
        }
    }

    public void close() {
        printEndOfShiftSummary();
        System.out.println("\nThe Restaurant is closed!");
        open = false;
        currentTimeStep = 0;
    }

    private void processTimeStep() {
        System.out.println("\nTime step " + currentTimeStep);

        if (Math.random() * 1 > 0.5){
            Customer customer = new Customer(getRandomOorder());
            waitingCustomers.enqueue(customer);
            if(customer.getOrder() instanceof DineInOrder dineInOrder)
                System.out.println("Customer #" + (orderCounter-1) + " joined the queue seated at table #" + dineInOrder.getTableNumber());
            else
                System.out.println("Customer #" + (orderCounter-1) + " joined the queue for take out");
        }

        kitchen.processTimeStep();

        if (++currentTimeStep > desiredTimeSteps) {
            close();
        }
    }

    private Order getRandomOorder() {
        int numItems = (int)(Math.random()*3) + 1;
        MenuItem[] menu = Menu.items();
        MenuItem[] items = new MenuItem[numItems];
        for(int i = 0; i < numItems; i++){
            MenuItem randomItem = menu[(int)(menu.length * Math.random())];
            items[i] = randomItem;
        }

        if (Math.random() * 1 > 0.5){
            int available = getAvailableTable();
            if(available != -1) {
                tables[available] = true;
                return new DineInOrder(orderCounter++, available, items);
            }
        }
        return new TakeOutOrder(orderCounter++, items);
    }

    private int getAvailableTable(){
        int available = -1;
        for(int i = 0; i < tables.length; i++){
            if(!tables[i]) available = i;
        }
        return available;
    }

    public void completeOrder(Order order) {
        this.completedOrders.add(order);
        if(order instanceof DineInOrder dineInOrder){
            tables[dineInOrder.getTableNumber()] = false;
            System.out.println("Order #" + order.getOrderNumb() + " left $" + String.format("%.2f", dineInOrder.getTip()) + " in tips");
        }
        System.out.println("Completed order #" + order.getOrderNumb() + " +$" + String.format("%.2f",order.getTotal()));
    }

    public void printEndOfShiftSummary() {
        System.out.println("\nEnd of shift summary:");
        System.out.println(waitingCustomers.size() + kitchen.activeOrders.length + " customers turned away");
        System.out.println(totalCustomersProcessed + " customers processed (fired orders)");
        System.out.println(completedOrders.size() + " orders completed");

        double totalRevenue = 0, totalTips = 0, packagingFees = 0;
        int dineInCount = 0, takeOutCount = 0;
        for (Order completed : completedOrders) {
            totalRevenue += completed.getTotal();
            if(completed instanceof DineInOrder dineInOrder){
                totalTips += dineInOrder.getTip();
                dineInCount++;
            }
            if(completed instanceof TakeOutOrder){
                packagingFees += TakeOutOrder.PACKAGING_FEE;
                takeOutCount++;
            }
        }
        System.out.println(dineInCount + " dine in orders");
        System.out.println(takeOutCount + " take out orders");
        System.out.println("$" + String.format("%.2f", totalRevenue) + " total revenue");
        System.out.println("$" + String.format("%.2f", totalTips) + " in tips (included in total revenue)");
        System.out.println("$" + String.format("%.2f", packagingFees) + " in packaging fees (included in total revenue)");
    }
}
