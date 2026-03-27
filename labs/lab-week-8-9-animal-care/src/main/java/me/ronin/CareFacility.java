package me.ronin;

public class CareFacility {

    private AnimalSearchTree basicCare;
    private AnimalSearchTree advancedCare;
    private AnimalSearchTree intensiveCare;
    private HashTable animalTable;
    private int timeStep;

    public CareFacility(Animal[] animals){
        basicCare = new AnimalSearchTree();
        advancedCare = new AnimalSearchTree();
        intensiveCare = new AnimalSearchTree();
        animalTable = new HashTable(animals.length + 5);
        for(Animal animal : animals){
            addAnimal(animal);
        }
    }

    public void startCare() throws InterruptedException {
        timeStep = 1;
        while(true){
            //Add urgency if it is not the first day
            if(timeStep != 1) addUrgency();
            System.out.println("\n------- Start of Day (" + timeStep + ") -------");
            printTrees();

            //Care for the animals
            careForMostUrgent();
            System.out.println("\n------- End of Day (" + timeStep + ") -------");
            printTrees();

            timeStep++;
            Thread.sleep(1000l);
        }
    }

    //Decided to use inorder traversal to show animals with growing urgency move down the list
    private void printTrees(){
        System.out.println("---- Basic Care ----");
        basicCare.inorder(basicCare.root);
        System.out.println("---- Advanced Care ----");
        advancedCare.inorder(advancedCare.root);
        System.out.println("---- Intensive Care ----");
        intensiveCare.inorder(intensiveCare.root);
    }

    private void careForMostUrgent(){
        careForMostUrgent(basicCare);
        careForMostUrgent(advancedCare);
        careForMostUrgent(intensiveCare);
    }

    private void careForMostUrgent(AnimalSearchTree ast){
        //Find the largest node (highest care level)
        AnimalSearchTree.Node largestNode = ast.findLargestNode(ast.root);
        //check if the node existed (if null the care unit is empty)
        //and that the animals list has any animals
        if(largestNode == null || largestNode.getAnimals().isEmpty()) return;
        //Get the animal at index 0 (first added to list)
        Animal animal = largestNode.getAnimals().get(0);
        //delete from the tree
        ast.deleteAnimal(animal);
        //update the care level
        animal.deliverCare();
        //redistribute to the proper tree node
        distributeAnimal(animal);
    }

    private void addUrgency(){
        //loop through all animal names
        for(String animalName : animalTable.getKeys()){
            Animal animal = (Animal) animalTable.get(animalName);
            //remove animal from current tree
            getTreeForLevel(animal.getCareLevel()).deleteAnimal(animal);
            //add urgency/update care level
            animal.delayCare();
            //redistribute the animal to a tree
            distributeAnimal(animal);
        }
    }

    private void addAnimal(Animal animal){
        animalTable.put(animal.getName(), animal);
        distributeAnimal(animal);
    }

    private void distributeAnimal(Animal animal){
        getTreeForLevel(animal.getCareLevel()).add(animal);
    }

    private AnimalSearchTree getTreeForLevel(int careLevel){
        switch (careLevel){
            case 1,2,3 -> {
                return basicCare;
            }
            case 4,5,6,7 -> {
                return advancedCare;
            }
            case 8,9,10 -> {
                return intensiveCare;
            }
        }
        return null;
    }
}
