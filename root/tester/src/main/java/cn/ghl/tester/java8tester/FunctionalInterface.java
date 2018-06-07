package cn.ghl.tester.java8tester;

import java.util.Arrays;
import java.util.List;
import java.util.function.IntToDoubleFunction;
import java.util.function.Predicate;

/**
 * @Author: Hailong Gong
 * @Description:
 * @Date: Created in 1/3/2018
 */
public class FunctionalInterface {

    public static void main(String args[]) {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

        // Predicate<Integer> predicate = n -> true
        // n 是一个参数传递到 Predicate 接口的 test 方法
        // n 如果存在则 test 方法返回 true

        System.out.println("输出所有数据:");

        // 传递参数 n
        eval(list, n -> true);

        // Predicate<Integer> predicate1 = n -> n%2 == 0
        // n 是一个参数传递到 Predicate 接口的 test 方法
        // 如果 n%2 为 0 test 方法返回 true

        System.out.println("输出所有偶数:");
        eval(list, n -> n % 2 == 0);

        // Predicate<Integer> predicate2 = n -> n > 3
        // n 是一个参数传递到 Predicate 接口的 test 方法
        // 如果 n 大于 3 test 方法返回 true

        System.out.println("输出大于 3 的所有数字:");
        eval(list, n -> n > 3);

        System.out.println("----------------------");

        final IntToDoubleFunction i2d = value -> value + 0.0D;

        list.forEach(e -> System.out.println(i2d.applyAsDouble(e)));

        list.forEach(i2d::applyAsDouble);

        list.forEach(FunctionalInterface::show);
    }

    public static void show(final Object object) {
        System.out.println("show: " + object);
    }

    public static void eval(final List<Integer> list, final Predicate<Integer> predicate) {
        for (Integer n : list) {
            if (predicate.test(n)) {
                System.out.println(n + " ");
            }
        }
    }
}
