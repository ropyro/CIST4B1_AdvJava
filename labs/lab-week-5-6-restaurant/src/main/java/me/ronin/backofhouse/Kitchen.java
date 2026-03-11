package me.ronin.backofhouse;

import me.ronin.datastructures.Stack;
import me.ronin.frontofhouse.menu.MenuItem;
import me.ronin.frontofhouse.Customer;
import me.ronin.frontofhouse.Restaurant;
import me.ronin.datastructures.SinglelyLinkedList;
import me.ronin.backofhouse.orders.Order;

public class Kitchen {

    private static final int CONCURRENT_ORDER_LIMIT = 3;

    private Restaurant restaurant;
    public SinglelyLinkedList<Order> activeOrders;

    public Kitchen(Restaurant restaurant){
        this.restaurant = restaurant;
        activeOrders = new SinglelyLinkedList<>();
    }

    public void processTimeStep(){
        //Move the next waiting order from the queue into the active kitchen linked list
        if(restaurant.waitingCustomers.size() > 0){
            if(activeOrders.length < CONCURRENT_ORDER_LIMIT){
                Customer nextInLine = restaurant.waitingCustomers.dequeue();
                Order nextOrder = nextInLine.getOrder();
                fireOrder(nextOrder);
                System.out.println("Fired order #" + nextOrder.getOrderNumb());
                printOrderDetails(nextOrder);
            }else{
                System.out.println("Kitchen orders at capacity waiting before firing more orders...");
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
                System.out.println("Completed order #" + curr.getOrderNumb() + " Total completed orders now: " + restaurant.getCompletedCount());
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
