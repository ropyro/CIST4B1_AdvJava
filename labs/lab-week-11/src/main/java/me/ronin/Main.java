package me.ronin;

import java.util.Set;


//Sources:
//Brushed up on how the resource folder is processed by maven packager: https://stackoverflow.com/questions/74359224/read-a-file-from-resources-folder-java
//AVLTree implementation help to make it generic: https://www.youtube.com/watch?v=Jj9Mit24CWk
// ^ I did not copy it verbatim, but I did use the logic for specifying a comparable generic
public class Main {
    public static void main(String[] args) {
        //Initialize the spellchecker
        SpellChecker spellChecker = new SpellChecker();

        //Print the dictionary tree in order with balances
        System.out.println("Dictionary Tree: ");
        spellChecker.printDictionaryTree();

        //Spell check the message.txt file
        Set<String> misspelledWords = spellChecker.spellCheck(Main.class.getResourceAsStream("/message.txt"));

        //Print the misspelled words
        System.out.print("Misspelled words: ");
        misspelledWords.forEach(word -> System.out.print(word + " "));
    }
}