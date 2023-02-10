package org.zaproxy.addon.profilingproxy.utils;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class contains methods te test the mathematical operations in MathUtils.java
 */
class MathUtilsUnitTest {

    /**
     * This method test if the median for a dataset with an odd amount of values is calculated correctly.
     */
    @Test
    void medianOddTest() {
        int[] data = {10,6,4,4,6,4,1};
        assertEquals(4, MathUtils.median(data));
    }

    /**
     * This method test if the median for a dataset with an even amount of values is calculated correctly.
     */
    @Test
    void medianEvenTest() {
        int[] data = {10,6,4,4,6,4,1, 6};
        assertEquals(5, MathUtils.median(data));
    }

    /**
     * This method test if the average of a dataset is calculated correctly.
     */
    @Test
    void averageTest() {
        int[] data = {17,6,4,4,6,4,1};
        assertEquals(6, MathUtils.average(Arrays.stream(data)));
    }

    /**
     * This method test if the minimum value of a dataset is calculated correctly.
     */
    @Test
    void minimumTest() {
        int[] data = {17,6,4,4,6,4,1};
        assertEquals(1, MathUtils.minimum(Arrays.stream(data)));
    }

    /**
     * This method test if the maximum value of a dataset is calculated correctly.
     */
    @Test
    void maximumTest() {
        int[] data = {17,6,4,4,6,4,1};
        assertEquals(17, MathUtils.maximum(Arrays.stream(data)));
    }

    /**
     * This method test if the standard deviation of a dataset is calculated correctly.
     */
    @Test
    void standardDeviationTest() {
        int[] data = {17,6,4,4,6,4,1};
        assertEquals(5, Math.round(MathUtils.standardDeviation(data)));
    }

    /**
     * This method tests if the amount of values in a dataset is calculated correctly.
     */
    @Test
    void countTest() {
        List<Integer> data = new ArrayList<>();
        data.add(1);
        data.add(2);
        assertEquals(2, MathUtils.count(data));
    }

    /**
     * This method test if the first quartile of a dataset is calculated correctly.
     */
    @Test
    void firstQuartileUnitTest() {
        int[] data  = {1, 2, 4, 5, 7, 8, 9, 10, 12, 15, 16, 17, 20};
        assertEquals(5, MathUtils.nthQuartile(1, data));
    }

    /**
     * This method test if the nth quartile of a dataset is calculated correctly for an empty dataset.
     */
    @Test
    void quartileEmptyListUnitTest() {
        int[] data  = {};
        assertEquals(0, MathUtils.nthQuartile(1, data));
    }

    /**
     * This method test if the third quartile of a dataset is calculated correctly.
     */
    @Test
    void thirdQuartileUnitTest() {
        int[] data  = {1, 2, 4, 5, 7, 8, 9, 10, 12, 15, 16, 17, 20};
        assertEquals(15, MathUtils.nthQuartile(3, data));
    }

}
