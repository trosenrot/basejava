package com.basejava.webapp;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MainStream {
    public static void main(String[] args) {
        int[] values = {1, 2, 2, 3, 3, 4, 5, 1, 6};
        System.out.println(minValue(values));
        List<Integer> values2 = Arrays.asList(1, 2, 5, 9, 11, 3);
        System.out.println(oddOrEven(values2));
    }

    public static int minValue(int[] values) {
        return IntStream.of(values).distinct().sorted().reduce(0, (x, y) -> 10 * x + y);
    }

    public static List<Integer> oddOrEven(List<Integer> integers) {
        Map<Boolean, List<Integer>> map = integers.stream().collect(Collectors.partitioningBy(x -> x % 2 == 0, Collectors.toList()));
        return map.get(map.get(false).size() % 2 != 0);
    }
}
