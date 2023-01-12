package test;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class Test1 {
    public static void main(String[] args) {
        String[] strings = {"hello", "world"};
        List<String> stringList = Arrays.asList(strings);
        check(stringList);
    }

    public static void check(List<String> stringList) {
        System.out.println(stringList);
    }
}
