package cn.ghl.tester.java8tester;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import org.junit.jupiter.api.Test;

/**
 * @Author: Hailong Gong
 * @Description:
 * @Date: Created in 1/3/2018
 */
public class OptionalTester {

    public static void show(int number) {
        System.out.println("Phone mumber: " + number);
    }

    public static void say(int number) {
        System.out.println("Phone say: " + number);
    }

    @Test
    public void test() {

        Consumer<Object> println = str -> System.out.println(str);

        Optional<Phone> phone = Optional.ofNullable(null);
        String model = phone.map(Phone::getSmartphone).map(Smartphone::getApplePhone)
            .map(ApplePhone::getModle).orElse("--");
        System.out.println(model);

        Predicate<Object> notNull = obj -> obj != null;
        Predicate<Object> isNull = obj -> obj == null;
        Phone myPhone = new Phone();
        System.out.println(notNull.test(myPhone));
        System.out.println(isNull.test(myPhone));

        println.accept(isNull.test(myPhone));

        Consumer<Integer> consumer1 = OptionalTester::show;
        Consumer<Integer> consumer2 = OptionalTester::say;
        consumer1.andThen(consumer2).accept(123);
    }

    private class Phone {

        public void show(int number) {
            System.out.println("Phone mumber: " + number);
        }

        private Smartphone smartphone;

        public Smartphone getSmartphone() {
            return smartphone;
        }

        public void setSmartphone(Smartphone smartphone) {
            this.smartphone = smartphone;
        }
    }

    private class Smartphone {

        public void show(int number) {
            System.out.println("Smartphone mumber: " + number);
        }

        private ApplePhone applePhone;

        public ApplePhone getApplePhone() {
            return applePhone;
        }

        public void setApplePhone(ApplePhone applePhone) {
            this.applePhone = applePhone;
        }
    }

    private class ApplePhone {

        public void show(int number) {
            System.out.println("ApplePhone mumber: " + number);
        }

        private String modle;

        public String getModle() {
            return modle;
        }

        public void setModle(String modle) {
            this.modle = modle;
        }
    }
}
