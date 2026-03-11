package me.ronin.frontofhouse;

import me.ronin.datastructures.Queue;
import me.ronin.backofhouse.Kitchen;
import me.ronin.frontofhouse.menu.Menu;
import me.ronin.frontofhouse.menu.MenuItem;
import me.ronin.backofhouse.orders.DineInOrder;
import me.ronin.backofhouse.orders.Order;
import me.ronin.backofhouse.orders.TakeOutOrder;

import java.util.ArrayList;
import java.util.Date;

public class Restaurant {

    public ArrayList<Order> completedOrders;
    public Queue<Customer> waitingCustomers;
    private Kitchen kitchen;

    public boolean open = false;
    public int currentTimeStep = 0;
    public int desiredTimeSteps;

    public int totalCustomersProcessed = 0;

    public int orderCounter = 1;

    public Restaurant(int desiredTimeSteps) {
        completedOrders = new ArrayList<>();
        waitingCustomers = new Queue<>(100);
        kitchen = new Kitchen(this);

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
        open = false;
        printEndOfShiftSummary();
        System.out.println("\nThe Restaurant is closed! \n");
    }

    private void processTimeStep() {
        System.out.println("Time step " + currentTimeStep);
        // Add arriving customers/orders to the queue
        Customer customer = new Customer(getRandomOorder());
        waitingCustomers.enqueue(customer);
        System.out.println("One customer #" + (orderCounter-1) + " joined the queue");

        //Process kitchen activities
        kitchen.processTimeStep();

        System.out.println(" ");

        //update simulation loop
        currentTimeStep++;
        if (currentTimeStep > desiredTimeSteps) {
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

        Order order;
        if (Math.random() * 1 > 0.5)
            order = new DineInOrder(orderCounter++, (int)(Math.random()*10), items);
        else
            order = new TakeOutOrder(orderCounter++, new Date(), items);
        return order;
    }

    public void completeOrder(Order order) {
        this.completedOrders.add(order);
    }

    public int getCompletedCount(){
        return this.completedOrders.size();
    }

    public void printEndOfShiftSummary() {
        System.out.println("End of shift summary:");
        System.out.println(waitingCustomers.size() + kitchen.activeOrders.length + " customers turned away");
        System.out.println(totalCustomersProcessed + " customers processed");
        System.out.println(completedOrders.size() + " orders completed");

        int dineInCount = completedOrders.stream().filter(o -> o instanceof DineInOrder).toList().size();
        int takeOutCount = completedOrders.stream().filter(o -> o instanceof TakeOutOrder).toList().size();
        System.out.println(dineInCount + " dine in orders");
        System.out.println(takeOutCount + " take out orders");

        double totalRevenue = 0;
        for (Order completed : completedOrders) {
            totalRevenue += completed.getTotal();
        }
        System.out.println("$" + String.format("%.2f", totalRevenue) + " total revenue");
    }
}
