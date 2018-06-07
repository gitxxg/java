package cn.ghl.tester.java8tester;

/**
 * @Author: Hailong Gong
 * @Description:
 * @Date: Created in 1/3/2018
 */
public class LambdaExpression {

    public static void main(String args[]) {
        LambdaExpression tester = new LambdaExpression();
        MathOperation operation1 = new MathOperation() {
            public int operation(int a, int b) {
                return a + b;
            }
        };

        MathOperation operation2 = (a, b) -> a - b;

        int ret1 = tester.operate(1, 2, operation1);
        int ret2 = tester.operate(1, 2, operation2);

        Message message1 = new Message() {
            @Override
            public void sayMessage(String message) {
                System.out.println("-----" + message);
            }
        };

        Message message2 = message -> System.out.println("=====" + message);

        message1.sayMessage("" + ret1);
        message2.sayMessage("" + ret2);
    }

    interface MathOperation {

        int operation(int a, int b);
    }

    interface Message {

        void sayMessage(String message);

    }

    private int operate(int a, int b, MathOperation mathOperation) {
        return mathOperation.operation(a, b);
    }

}
