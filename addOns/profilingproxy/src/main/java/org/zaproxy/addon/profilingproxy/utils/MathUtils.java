package org.zaproxy.addon.profilingproxy.utils;

import java.util.List;
import java.util.stream.IntStream;

/**
 * This class contains methods to do mathematical operations on datasets
 */
public class MathUtils {

    private MathUtils(){
    }

    /**
     * This method returns the median value of a given dataset
     * For an empty dataset, it will return 0
     * @param input: the dataset to work with
     * @return the median value in the dataset
     */
    public static double median(int[] input) {
        double median;
        try {median = input[input.length/2]; }
        catch (IndexOutOfBoundsException e) {return 0;}
        if (input.length%2 == 0) {
            median = (median + input[input.length/2-1])/2;
        }
        return median;
    }

    /**
     * This method returns the average value of a given dataset
     * For an empty dataset, it will return 0
     * @param input: the dataset to work with
     * @return the average value in the dataset
     */
    public static double average(IntStream input) {
        return input.average().orElse(0);
    }

    /**
     * This method returns the minimum value of a given dataset
     * For an empty dataset, it will return 0
     * @param input: the dataset to work with
     * @return the minimum value in the dataset
     */
    public static int minimum(IntStream input) {
        return input.min().orElse(0);
    }

    /**
     * This method returns the maximum value of a given dataset
     * For an empty dataset, it will return 0
     * @param input: the dataset to work with
     * @return the maximum value in the dataset
     */
    public static int maximum(IntStream input) {
        return input.max().orElse(0);
    }

    /**
     * This method returns the standard deviation of a given dataset
     * For an empty dataset, it will return 0
     * @param input: the dataset to work with
     * @return the standard deviation of the dataset
     */
    public static double standardDeviation(int[] input) {
        if (input.length == 0) {return 0;}
        double sum = 0.0;
        double standardDeviation = 0.0;
        int length = input.length;
        for(int num : input) {
            sum += num;
        }
        double mean = sum/length;

        for(double num: input) {
            standardDeviation += Math.pow(num - mean, 2);
        }
        return Math.sqrt(standardDeviation/length);
    }

    /**
     * This method returns the amount of values of a given dataset
     * For an empty dataset, it will return 0
     * @param input: the dataset to work with
     * @return the amount of values in the dataset
     */
    public static int count(List<?> input) {return input.size();}

    /**
     * This method returns the nth quartile  of a given dataset
     * The first quartile (Q1) is defined as the middle number between the smallest number (minimum) and the median of the data set.
     * It is also known as the lower or 25th empirical quartile, as 25% of the data is below this point
     * The third quartile (Q3) is the middle value between the median and the highest value (maximum) of the data set.
     * It is known as the upper or 75th empirical quartile, as 75% of the data lies below this point.
     * For an empty dataset, it will return 0
     * @param input: the dataset to work with
     * @return the nth quartile in the dataset
     */
    public static double nthQuartile(int n,int[] input) {
        int index = (int) Math.ceil(n*25 / 100.0 * input.length);
        try {
            return input[index-1];}
        catch (IndexOutOfBoundsException e) {
            return 0;}
    }

}
