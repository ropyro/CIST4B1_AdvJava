package me.ronin;

import java.io.*;
import java.util.HashSet;
import java.util.Set;


//Sources:
//Brushed up on how the resource folder is processed by maven packager: https://stackoverflow.com/questions/74359224/read-a-file-from-resources-folder-java
//AVLTree implementation help to make it generic: https://www.youtube.com/watch?v=Jj9Mit24CWk
// ^ I did not copy it verbatim, but I did use the logic for specifying a comparable generic
public class Main {
    public static void main(String[] args) throws IOException {
        SpellChecker spellChecker = new SpellChecker();

        System.out.println("Dictionary Tree: ");
        spellChecker.printDictionaryTree();

        Set<String> misspelledWords = spellChecker.spellCheck(Main.class.getResourceAsStream("/message.txt"));

        System.out.print("Misspelled words: ");
        for(String word : misspelledWords){
            System.out.print(word + " ");
        }
    }
}