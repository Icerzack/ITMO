package algorithmTest;

import algorithm.CountSort;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class CountSortTest {

    @Test
    void testCountSort() {
        //Given
        int[] arr = {4, 2, 10, 5, 8};
        int[] expectedOutput = {2, 4, 5, 8, 10};
        List<String> expectedLog = new ArrayList<String>();
        expectedLog.add("NULL_CHECK: NOT_NULL");
        expectedLog.add("MIN_MAX: MIN=2 MAX=10");
        expectedLog.add("SHIFTED_ARRAY: [2, 0, 8, 3, 6]");
        expectedLog.add("COUNT_ARRAY: [1, 0, 1, 1, 0, 0, 1, 0, 1]");
        expectedLog.add("MODIFIED_COUNT_ARRAY: [1, 1, 2, 3, 3, 3, 4, 4, 5]");
        expectedLog.add("SORTED_ARRAY: [0, 2, 3, 6, 8]");
        expectedLog.add("SHIFTED_BACK_SORTED_ARRAY: [2, 4, 5, 8, 10]");
        expectedLog.add("SORTED_ARRAY_COPIED_TO_INPUT_ARRAY: SORTED=[2, 4, 5, 8, 10] INPUT=[2, 4, 5, 8, 10]");
                
        //When
        CountSort countSort = new CountSort(arr);
        int[] output = countSort.countSort();
        List<String> logOutput = countSort.getOperationLog();

        //Then
        assertArrayEquals(expectedOutput, output);
        assertLogEquals(expectedLog, logOutput);
    }

    @Test
    void testCountSortWithNegativeValues() {
        //Given
        int[] arr = {-3, 2, 10, -5, 8};
        int[] expectedOutput = {-5, -3, 2, 8, 10};
        List<String> expectedLog = new ArrayList<String>();
        expectedLog.add("NULL_CHECK: NOT_NULL");
        expectedLog.add("MIN_MAX: MIN=-5 MAX=10");
        expectedLog.add("SHIFTED_ARRAY: [2, 7, 15, 0, 13]");
        expectedLog.add("COUNT_ARRAY: [1, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1]");
        expectedLog.add("MODIFIED_COUNT_ARRAY: [1, 1, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 4, 4, 5]");
        expectedLog.add("SORTED_ARRAY: [0, 2, 7, 13, 15]");
        expectedLog.add("SHIFTED_BACK_SORTED_ARRAY: [-5, -3, 2, 8, 10]");
        expectedLog.add("SORTED_ARRAY_COPIED_TO_INPUT_ARRAY: SORTED=[-5, -3, 2, 8, 10] INPUT=[-5, -3, 2, 8, 10]");

        //When
        CountSort countSort = new CountSort(arr);
        int[] output = countSort.countSort();
        List<String> logOutput = countSort.getOperationLog();

        //Then
        assertArrayEquals(expectedOutput, output);
        assertLogEquals(expectedLog, logOutput);
    }

    @Test
    void testCountSortWithDuplicateValues() {
        int[] arr = {3, 1, 2, 3, 1, 2, 3};
        int[] expectedOutput = {1, 1, 2, 2, 3, 3, 3};
        List<String> expectedLog = new ArrayList<String>();
        expectedLog.add("NULL_CHECK: NOT_NULL");
        expectedLog.add("MIN_MAX: MIN=1 MAX=3");
        expectedLog.add("SHIFTED_ARRAY: [2, 0, 1, 2, 0, 1, 2]");
        expectedLog.add("COUNT_ARRAY: [2, 2, 3]");
        expectedLog.add("MODIFIED_COUNT_ARRAY: [2, 4, 7]");
        expectedLog.add("SORTED_ARRAY: [0, 0, 1, 1, 2, 2, 2]");
        expectedLog.add("SHIFTED_BACK_SORTED_ARRAY: [1, 1, 2, 2, 3, 3, 3]");
        expectedLog.add("SORTED_ARRAY_COPIED_TO_INPUT_ARRAY: SORTED=[1, 1, 2, 2, 3, 3, 3] INPUT=[1, 1, 2, 2, 3, 3, 3]");

        //When
        CountSort countSort = new CountSort(arr);
        int[] output = countSort.countSort();
        List<String> logOutput = countSort.getOperationLog();

        //Then
        assertArrayEquals(expectedOutput, output);
        assertLogEquals(expectedLog, logOutput);
    }

    @Test
    void testCountSortWithEmptyArray() {
        int[] arr = {};
        int[] expectedOutput = {};
        List<String> expectedLog = new ArrayList<String>();
        expectedLog.add("NULL_CHECK: NULL");
        expectedLog.add("MIN_MAX: MIN=0 MAX=0");
        expectedLog.add("SHIFTED_ARRAY: []");
        expectedLog.add("COUNT_ARRAY: []");
        expectedLog.add("MODIFIED_COUNT_ARRAY: []");
        expectedLog.add("SORTED_ARRAY: []");
        expectedLog.add("SHIFTED_BACK_SORTED_ARRAY: []");
        expectedLog.add("SORTED_ARRAY_COPIED_TO_INPUT_ARRAY: SORTED=[] INPUT=[]");

        //When
        CountSort countSort = new CountSort(arr);
        int[] output = countSort.countSort();
        List<String> logOutput = countSort.getOperationLog();

        //Then
        assertArrayEquals(expectedOutput, output);
        assertLogEquals(expectedLog, logOutput);
    }

    @Test
    void testCountSortWithSingleValueArray() {
        int[] arr = {5};
        int[] expectedOutput = {5};
        List<String> expectedLog = new ArrayList<String>();
        expectedLog.add("NULL_CHECK: NOT_NULL");
        expectedLog.add("MIN_MAX: MIN=5 MAX=5");
        expectedLog.add("SHIFTED_ARRAY: [0]");
        expectedLog.add("COUNT_ARRAY: [1]");
        expectedLog.add("MODIFIED_COUNT_ARRAY: [1]");
        expectedLog.add("SORTED_ARRAY: [0]");
        expectedLog.add("SHIFTED_BACK_SORTED_ARRAY: [5]");  
        expectedLog.add("SORTED_ARRAY_COPIED_TO_INPUT_ARRAY: SORTED=[5] INPUT=[5]");

        //When
        CountSort countSort = new CountSort(arr);
        int[] output = countSort.countSort();
        List<String> logOutput = countSort.getOperationLog();

        //Then
        assertArrayEquals(expectedOutput, output);
        assertLogEquals(expectedLog, logOutput);
    }

    // Вспомогательный метод для сравнения логов
    private void assertLogEquals(List<String> expectedLog, List<String> logOutput) {
        if (expectedLog.size() != logOutput.size()) {
            return;
        }
        for (int i = 0; i < expectedLog.size(); i++) {
            String expected = expectedLog.get(i);
            String actual = logOutput.get(i);
            assertEquals(expected, actual);
        }
    }
}
