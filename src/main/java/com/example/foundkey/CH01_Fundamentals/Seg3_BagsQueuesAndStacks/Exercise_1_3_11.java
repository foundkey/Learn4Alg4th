package com.example.foundkey.CH01_Fundamentals.Seg3_BagsQueuesAndStacks;

public class Exercise_1_3_11 {

    /*
    * 一个栈
    *   遇到数字进栈
    *   遇到运算符弹出合适数目的数字运算，结果入栈
     */
    public static int evaluatePostfix(String postfix) {
        String[] items = postfix.split(" ");

        FStack<Integer> stack = new FStack<>();

        for (String item : items) {
            if (Character.isDigit(item.charAt(0))) {
                stack.push(Integer.parseInt(item));
            } else if (item.equals("+")) {
                int a = stack.pop();
                int b = stack.pop();
                stack.push(a + b);
            } else if (item.equals("*")) {
                int a = stack.pop();
                int b = stack.pop();
                stack.push(a * b);
            }
        }

        return stack.pop();
    }
}
