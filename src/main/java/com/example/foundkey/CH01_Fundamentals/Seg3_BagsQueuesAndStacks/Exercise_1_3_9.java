package com.example.foundkey.CH01_Fundamentals.Seg3_BagsQueuesAndStacks;

public class Exercise_1_3_9 {

    /*
    * 一个栈保存运算符，一个栈保存表达式
    *   如果是符号，压入运算符栈
    *   如果是数字，压入表达式栈
    *   如果是‘）’，弹出两个表达式与一个运算符，补全括号后，压入表达式栈
     */
    public static String fixParenthesis(String express) {
        String[] items = express.split(" ");

        FStack<String> exS = new FStack<>();
        FStack<String> opS = new FStack<>();

        for (String item : items) {
            if (item.equals(")")) {
                String a = exS.pop();
                String b = exS.pop();
                String op = opS.pop();
                exS.push(String.join(" ", "(", b, op, a, ")"));
            } else if (Character.isDigit(item.charAt(0))) {
                exS.push(item);
            } else {
                opS.push(item);
            }
        }

        return exS.pop();
    }
}
