package me.ronin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class SpellChecker {
    private AVLTree<String> dictionary;

    public SpellChecker() {
        this.dictionary = new AVLTree<>();
        loadDictionary();
    }

    private void loadDictionary() {
        InputStream dictionaryStream = SpellChecker.class.getResourceAsStream("/dictionary.txt");
        if(dictionaryStream == null){
            System.err.println("Error: dictionary.text not found");
        }
        try{
            InputStreamReader isr = new InputStreamReader(dictionaryStream);
            BufferedReader br = new BufferedReader(isr);
            br.lines().forEach(line -> dictionary.add(line.toLowerCase()));
            br.close();
            isr.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printDictionaryTree(){
        dictionary.inorder();
    }

    public Set<String> spellCheck(InputStream inputStream) throws IOException {
        if(inputStream == null) {
            System.err.println("Error: input text not found");
            return null;
        }
        Set<String> misspelledWords = new HashSet<>();

        InputStreamReader messageStreamReader = new InputStreamReader(inputStream);
        BufferedReader br = new BufferedReader(messageStreamReader);
        br.lines().forEach(line -> {
            String[] words = line.toLowerCase().split(" ");
            for(String word : words){
                if(!dictionary.searchRecursive(word)){
                    misspelledWords.add(word);
                }
            }
        });
        br.close();
        messageStreamReader.close();

        return misspelledWords;
    }
}
