package me.ronin;

public class Utils {
    public static int[] getRandomArray(int size) {
        int[] randomArr = new int[size];
        for (int i = 0; i < size; i++) {
            randomArr[i] = (int) (Math.random() * 100);
        }
        return randomArr;
    }

    public static void printArr(int[] arr) {
        for (int i : arr) {
            System.out.print(i + " ");
        }
        System.out.println("");
    }
}
