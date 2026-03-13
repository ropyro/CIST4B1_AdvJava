package me.ronin.kitchen.orders;

import me.ronin.datastructures.Stack;
import me.ronin.kitchen.PrepStep;
import me.ronin.menu.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private List<MenuItem> items;
    private Stack<PrepStep> prepStepStack;
    private int orderNumb;

    private boolean completed = false;

    public Order(int orderNumb, MenuItem... items) {
        this.items = new ArrayList<>();
        this.orderNumb = orderNumb;
        for (MenuItem item : items) {
            this.items.add(item);
        }

        prepStepStack = generatePrepSteps();
    }

    private Stack<PrepStep> generatePrepSteps() {
        Stack<PrepStep> steps = new Stack<>(100);
        for (int i = items.size()-1; i >= 0; i--) {
            List<PrepStep> itemPrepSteps = items.get(i).getPrepSteps();
            for (int j = itemPrepSteps.size()-1; j >= 0; j--) {
                steps.push(itemPrepSteps.get(j));
            }
        }
        return steps;
    }

    public Stack<PrepStep> getPrepSteps() {
        return prepStepStack;
    }

    public List<MenuItem> getItems() {
        return items;
    }

    public double getTotal() {
        double total = 0;
        for (MenuItem item : items) {
            total += item.getPrice();
        }
        return total;
    }

    public void markComplete() {
        this.completed = true;
    }

    public boolean isCompleted() {
        return this.completed;
    }

    public int getOrderNumb() {
        return orderNumb;
    }
}
