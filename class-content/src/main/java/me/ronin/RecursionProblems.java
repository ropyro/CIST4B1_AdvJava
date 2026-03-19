package me.ronin;

public class RecursionProblems {

    public static int countOccurances(String input, char toFind) {
        //base case if string is empty there is chars to compare
        if (input.length() == 0) return 0;
        //Check if the first char is the one we are looking at
        if (input.charAt(0) == toFind) {
            //recursive case: Return 1 for a found char + check for next occurance in the substring after the first char
            return 1 + countOccurances(input.substring(1), toFind);
        }
        //recursive case: If first char wasnt a match just check next substring
        return countOccurances(input.substring(1), toFind);
    }

    //hey -> yeh
    // 0h 1e 2y
    // length() = 3
    // currentIndex = 0;
    // 0y 1h 2e
    // length() = 3
    // currentIndex = 1;
    // 0y 1e 2h
    // length() = 3
    // currentIndex = 2;
    public static String reverseString(String input, int currentIndex) {
        if (currentIndex == input.length() - 1 || input.length() == 0) return input;
        String lastChar = input.substring(input.length() - 1);
        String pre = input.substring(0, currentIndex);
        String after = input.substring(currentIndex, input.length() - 1);
        return reverseString(pre + lastChar + after, ++currentIndex);
    }

    public static String reverseString(String input) {
        if (input.length() == 0) return input;
        String lastChar = input.substring(input.length() - 1);
        return lastChar + reverseString(input.substring(0, input.length() - 1));
    }

    public static void writeVertical(int number) {
        if (number < 0) {
            System.out.print("-");
            number *= -1;
        }

        if (number < 10) {
            System.out.println(number);
            return;
        }

        writeVertical(number / 10);
        System.out.println(number % 10);
    }
}
