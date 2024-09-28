package cn.ksmcbrigade.etcw.utils;

public class ExpressionEvaluator {
    private String expression;
    private int pos;

    public double evaluate(String expression) {
        this.expression = expression;
        this.pos = 0;
        return expr();
    }

    private double expr() {
        double value = term();
        while (pos < expression.length() && (expression.charAt(pos) == '+' || expression.charAt(pos) == '-')) {
            char op = expression.charAt(pos);
            pos++;
            double right = term();
            if (op == '+') {
                value += right;
            } else {
                value -= right;
            }
        }
        return value;
    }

    private double term() {
        double value = factor();
        while (pos < expression.length() && (expression.charAt(pos) == '*' || expression.charAt(pos) == '/' || expression.charAt(pos) == '^')) {
            char op = expression.charAt(pos);
            pos++;
            double right = factor();
            if (op == '*') {
                value *= right;
            } else if (op == '/') {
                value /= right;
            } else {
                value = Math.pow(value, right);
            }
        }
        return value;
    }

    private double factor() {
        if(expression.isEmpty()) return 0;
        char c = expression.charAt(pos);
        if (expression.charAt(pos) == '(') {
            pos++;
            double value = expr();
            pos++; // ')'
            return value;
        } else if (c == '-') { // Handle unary minus
            pos++;
            return -factor(); // Recursively apply unary minus
        } else if (Character.isDigit(c) || c == '.') {
            int start = pos;
            while (pos < expression.length() && (Character.isDigit(expression.charAt(pos)) || expression.charAt(pos)=='e' || expression.charAt(pos)=='E')) {
                pos++;
            }
            if(start==pos) pos = expression.length();
            return Double.parseDouble(expression.substring(start, pos));
        }
        return 0;
    }
}

