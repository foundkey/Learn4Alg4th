package com.example.foundkey.CH01_Fundamentals.Seg1_ProgrammingModel.Sec3;

import java.util.ArrayList;
import java.util.List;

public class Exercise_1_3_10 {

    /*
    * 一个栈保留运算符，一个List保存结果
    *   遇到‘（’忽略
    *   遇到数字进List
    *   遇到运算符进栈
    *   遇到‘）’弹出一个运算符到List
     */
    public static String infixToPostfix(String express) {
        String[] items = express.split(" ");

        List<String> postFix = new ArrayList<>();
        FStack<String> ops = new FStack<>();

        for (String str : items) {
            if (str.equals("(")) {
                continue;
            } else if (str.equals(")")) {
                postFix.add(ops.pop());
            } else if (Character.isDigit(str.charAt(0))){
                postFix.add(str);
            } else if (str.length() == 1) {
                // 运算符
                ops.push(str);
            }
        }

        return String.join(" ", postFix);
    }
}
