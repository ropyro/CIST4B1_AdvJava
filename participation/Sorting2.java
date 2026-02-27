


public static void main(String[] args) {

}

public static int[arr] quickSort(int[] arr, int low, int high){
    if(arr.length == 1 || arr.length == 0) return arr;
    quickSort(arr, low+1, high);
    quickSort(arr, low, high-1);
}