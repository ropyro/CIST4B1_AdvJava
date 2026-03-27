package me.ronin;

import java.util.ArrayList;
import java.util.List;

/**
 * Left side of node (<), right side of node (>=)
 */
public class AnimalSearchTree {

    public Node root;

    public AnimalSearchTree() {
        this.root = null;
    }

    // ---- ADD ----
    public void add(Animal animal) {
        root = add(animal, root);
    }

    private Node add(Animal animal, Node current) {
        //if node is null, create a new node for care level
        if (current == null) {
            return new Node(animal);
        }
        int careLevel = animal.getCareLevel();
        //if careLevel is less than current's go left
        //if careLevel is equal to current, add to current's animal list
        //else greater than current, go right
        if (careLevel < current.careLevel) {
            current.left = add(animal, current.left);
        } else if (careLevel == current.careLevel) {
            current.animals.add(animal);
        } else {
            current.right = add(animal, current.right);
        }
        return current;
    }

    // ---- DELETE ----

    public void deleteAnimal(Animal animal) {
        root = deleteAnimal(animal, root);
    }

    public Node deleteAnimal(Animal animal, Node current) {
        if (current == null) return null;
        int careLevel = animal.getCareLevel();
        if (current.careLevel == careLevel) {
            current.animals.remove(animal);

            if(current.animals.isEmpty()){
                //case 1: no children
                if (current.left == null && current.right == null)
                    return null;

                //case 2: one child
                if (current.right == null)
                    return current.left;

                if (current.left == null)
                    return current.right;

                //case 3: two children
                //1: find smallest value in right subtree
                Node smallest = findSmallestNode(current.right);
                //2: replace current with smallest value
                current.careLevel = smallest.careLevel;

                //3: recursively delete the old smallest value
                current.right = deleteCareLevel(smallest.careLevel, current.right);
            }
        } else if (careLevel < current.careLevel) {
            current.left = deleteAnimal(animal, current.left);
        } else {
            current.right = deleteAnimal(animal, current.right);
        }
        return current;
    }

    private Node deleteCareLevel(int careLevel, Node current){
        if (current == null) return null;
        if (current.careLevel == careLevel) {
            //case 1: no children
            if (current.left == null && current.right == null)
                return null;

            //case 2: one child
            if (current.right == null)
                return current.left;

            if (current.left == null)
                return current.right;

            //case 3: two children
            //1: find smallest value in right subtree
            Node smallest = findSmallestNode(current.right);
            //2: replace current with smallest value
            current.careLevel = smallest.careLevel;
            //3: recursively delete the old smallest value
            current.right = deleteCareLevel(smallest.careLevel, current.right);
        } else if (careLevel < current.careLevel) {
            current.left = deleteCareLevel(careLevel, current.left);
        } else {
            current.right = deleteCareLevel(careLevel, current.right);
        }
        return current;
    }


    // ---- HELPERS -----
    public Node findLargestNode(Node current){
        if(current == null){
            return null;
        }
        if(current.right == null){
            return current;
        }
        return findLargestNode(current.right);
    }

    public Node findSmallestNode(Node current) {
        if (current.left == null) {
            return current;
        }
        return findSmallestNode(current.left);
    }

    // ---- traversals -----
    public void inorder(Node current) {
        if (current != null) {
            inorder(current.left);
            System.out.println(current.careLevel);
            current.animals.forEach(animal -> System.out.println(animal.getName() + " the " + animal.getSpecies().name().toLowerCase()));
            inorder(current.right);
        }
    }

    public void preorder(Node current) {
        if (current != null) {
            System.out.println(current.careLevel);
            preorder(current.left);
            preorder(current.right);
        }
    }

    public void postorder(Node current) {
        if (current != null) {
            postorder(current.left);
            postorder(current.right);
            System.out.println(current.careLevel);
        }
    }

    // ---- GET -----

    public Node getIterative(int value) {
        Node curr = root;
        while (curr != null) {
            if (curr.careLevel == value) {
                return curr;
            }
            if (value < curr.careLevel) {
                curr = curr.left;
            } else {
                curr = curr.right;
            }
        }
        return null;
    }

    public Node getRecursive(int value) {
        return getRecursive(value, root);
    }

    private Node getRecursive(int value, Node current) {
        if (current == null) return null;
        if (current.careLevel == value) return current;
        if (value < current.careLevel)
            return getRecursive(value, current.left);
        else
            return getRecursive(value, current.right);
    }

    // ---- SEARCH ----
    public boolean searchIterative(int value) {
        Node curr = root;
        while (curr != null) {
            if (curr.careLevel == value) {
                return true;
            }
            if (value < curr.careLevel) {
                curr = curr.left;
            } else {
                curr = curr.right;
            }
        }
        return false;
    }

    public boolean searchRecursive(int value) {
        return searchRecursive(value, root);
    }

    private boolean searchRecursive(int value, Node current) {
        if (current == null) return false;
        if (current.careLevel == value) return true;
        if (value < current.careLevel)
            return searchRecursive(value, current.left);
        else
            return searchRecursive(value, current.right);
    }

    public class Node {
        int careLevel;
        List<Animal> animals;
        Node left;
        Node right;

        Node(Animal animal) {
            this.careLevel = animal.getCareLevel();
            animals = new ArrayList<>();
            animals.add(animal);
            this.left = null;
            this.right = null;
        }

        public int getCareLevel() {
            return careLevel;
        }

        public List<Animal> getAnimals() {
            return animals;
        }
    }
}
