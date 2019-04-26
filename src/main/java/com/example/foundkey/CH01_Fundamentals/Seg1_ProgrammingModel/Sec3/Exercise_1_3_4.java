package com.example.foundkey.CH01_Fundamentals.Seg1_ProgrammingModel.Sec3;

import java.util.Iterator;

public class Exercise_1_3_4 {
    public static boolean parentheses(String express) {
        FStack<Character> stack = new FStack<>();

        for (char ch : express.toCharArray()) {
            if (ch == '(' || ch == '[' || ch == '{') {
                stack.push(ch);
            } else {
                if (stack.isEmpty()) {
                    return false;
                }
                int check = stack.peek() + ch;
                if (check == '(' + ')' || check == '[' + ']' || check == '{' + '}') {
                    stack.pop();
                } else {
                    return false;
                }
            }
        }

        return stack.isEmpty();
    }
}
