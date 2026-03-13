package me.ronin.kitchen;

import me.ronin.datastructures.Stack;
import me.ronin.menu.MenuItem;
import me.ronin.Customer;
import me.ronin.Restaurant;
import me.ronin.datastructures.SinglelyLinkedList;
import me.ronin.kitchen.orders.Order;

public class Kitchen {

    private final int concurrentOrderLimit = 3;

    private Restaurant restaurant;
    public SinglelyLinkedList<Order> activeOrders;

    public Kitchen(Restaurant restaurant){
        this.restaurant = restaurant;
        activeOrders = new SinglelyLinkedList<>();
    }

    public void processTimeStep(){
        if(restaurant.waitingCustomers.size() > 0){
            if(activeOrders.length < concurrentOrderLimit){
                Customer nextInLine = restaurant.waitingCustomers.dequeue();
                Order nextOrder = nextInLine.getOrder();
                fireOrder(nextOrder);
                System.out.println("Fired order #" + nextOrder.getOrderNumb());
                printOrderDetails(nextOrder);
            }else{
                System.out.println("Kitchen orders at capacity (" + activeOrders.length + "/"+ concurrentOrderLimit + ") waiting before firing more orders...");
            }
        }

        if(!activeOrders.isEmpty()){
            Order curr = activeOrders.head.data;
            System.out.println("Current Order: #" + curr.getOrderNumb());
            printOrderDetails(curr);
            Stack<PrepStep> prepSteps = curr.getPrepSteps();
            if(!prepSteps.isEmpty()){
               PrepStep currentStep = prepSteps.pop();
               System.out.println("#" + curr.getOrderNumb() + " completed: " + currentStep.getStepInfo());
            }
            if(prepSteps.isEmpty()){
                curr.markComplete();
                restaurant.completeOrder(curr);
                activeOrders.removeValue(curr);
            }
        }else{
            System.out.println("Kitchen is waiting for orders...");
        }
    }

    private void printOrderDetails(Order order){
        for(MenuItem item : order.getItems())
            System.out.println(" - " + item.getName());
    }

    public void fireOrder(Order order){
        activeOrders.append(order);
        restaurant.totalCustomersProcessed++;
    }
}
