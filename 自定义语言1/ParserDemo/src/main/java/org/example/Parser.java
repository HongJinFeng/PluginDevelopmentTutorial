package org.example;

public class Parser {

    // 当前解析器扫描的字符位置
    private int index;
    // 即将进行解析的表达式
    private final String expression;

    public Parser(String expression) {
        this.expression = expression;
    }

    public void checkExpr() {
        // 上文中我们已经定义出了表达式的语法，语法规定，表达式由“数字 + 表达式“ 或
        // ”数字 - 表达式“ 组成。因此，我们先进行匹配数字。
        digit();
        // 匹配完数字后，我们再匹配表达式，expr 方法中也会匹配 +/- 符号，因为上面一句代码
        // 匹配了一个数字，如果改表达式是合法的，那么接下来的字符串内容一定是：
        // “+” “另外一段表达式” 或 “-” “另外一段表达式”，例如：9+4，expr 方法会校验
        // 字符串是否包含“+4”这一段内容。
        expr();
    }

    private void expr() {
        // 超过程度，表示完成了匹配，退出方法。
        if (index >= expression.length()) {
            return;
        }
        // 匹配到 + 号
        if (expression.charAt(index) == '+') {
            // match 方法将 index 后移
            match('+');
            // 加号后面一定是另外一个数字
            digit();
            // 递归匹配剩余的内容
            expr();
        } else if (expression.charAt(index) == '-') {
            // match 方法将 index 后移
            match('-');
            // 减号后面一定是另外一个数字
            digit();
            // 递归匹配剩余的内容
            expr();
        }else {
            // 数字后面跟随的不是+/-，那么表达式一定有问题，例如 93+2，因为我们的语法
            // 解析的是个位数整数加减法，9后面跟随的不是+/-却是数字。
            throw new Error("Syntax error");
        }
    }

    private void digit() {
        // 匹配数字时，如果发现 index 超过字符串长度，没办法进行匹配一个数字字符
        // 那么该表达式一定不符合语法。例如 9-3+，这个匹配到最后一个加号时，我们
        // 期望再匹配一个数字，确发现 index 超出了字符串长度。
        if (index >= expression.length()) {
            throw new Error("Syntax error");
        }
        // 匹配数字时，如果匹配成功，match 方法会将 index 后移
        if (Character.isDigit(expression.charAt(index))) {
            match(expression.charAt(index));
        } else {
            // 匹配数字失败，语法存在问题
            throw new Error("Syntax error");
        }
    }

    void match(char t) {
        // 判断当前的 index 是否与要匹配的字符一致，一致则 index 后移
        if (expression.charAt(index) == t) {
            index++;
        } else {
            throw new Error("Syntax error");
        }
    }

    public static void main(String[] args) {
        // 不会抛出异常
        new Parser("9-3+1").checkExpr();
        // 抛出异常
        new Parser("9-3+").checkExpr();
    }
}