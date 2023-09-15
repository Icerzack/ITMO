package algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CountSort {
    private int[] array;
    private List<String> logOutput;

    public CountSort(int[] values) {
        array = values.clone();
        logOutput = new ArrayList<String>();
    }

    public int[] countSort() {
        if (array.length == 0) {
            return new int[0];
        }
        log("NULL_CHECK: NOT_NULL");
        // Find min and max values in the array
        int minVal = array[0];
        int maxVal = array[0];
        for (int num : array) {
            if (num < minVal) {
                minVal = num;
            } else if (num > maxVal) {
                maxVal = num;
            }
        }
        log("MIN_MAX: MIN="+minVal+" MAX="+maxVal);
        // Shift all the values so that the minimum value becomes zero
        for (int i = 0; i < array.length; i++) {
            array[i] = array[i] - minVal;
        }
        log("SHIFTED_ARRAY: "+Arrays.toString(array));
        // Count the occurrences of each value
        int[] countArray = new int[maxVal - minVal + 1];
        for (int num : array) {
            countArray[num]++;
        }
        log("COUNT_ARRAY: "+Arrays.toString(countArray));
        // Modify the count array to store the running sum
        for (int i = 1; i < countArray.length; i++) {
            countArray[i] += countArray[i - 1];
        }
        log("MODIFIED_COUNT_ARRAY: "+Arrays.toString(countArray));
        // Build the sorted array
        int[] sortedArray = new int[array.length];
        for (int i = array.length - 1; i >= 0; i--) {
            int num = array[i];
            int index = countArray[num] - 1;
            sortedArray[index] = num;
            countArray[num]--;
        }
        log("SORTED_ARRAY: "+Arrays.toString(sortedArray));
        // Shift all the values back to their original range
        for (int i = 0; i < sortedArray.length; i++) {
            sortedArray[i] += minVal;
        }
        log("SHIFTED_BACK_SORTED_ARRAY: "+Arrays.toString(sortedArray));
        // Copy the sorted array back to the input array
        System.arraycopy(sortedArray, 0, array, 0, array.length);
        log("SORTED_ARRAY_COPIED_TO_INPUT_ARRAY: SORTED="+Arrays.toString(sortedArray) + " INPUT=" + Arrays.toString(array));
        return array;
    }

    public List<String> getOperationLog() {
        return logOutput;
    }

    private void log(String message) {
        logOutput.add(message);
    }
}
