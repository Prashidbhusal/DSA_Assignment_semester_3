package questions;


import java.util.*;

public class Week1 {

    // Utility recursive method to generate all possible expressions
    static void getExprUtil(List<String> res, String curExp,
                            String input, int target, int pos,
                            int curVal, int last)
    {

        // true if whole input is processed with some
        // operators
        if (pos == input.length())
        {
            // if current value is equal to target
            //then only add to final solution
            // if question is : all possible o/p then just
            //push_back without condition
            if (curVal == target)
                res.add(curExp);
            return;
        }


        // take part of input from pos to i
        String part = input.substring(pos, pos + 1);

        // take numeric value of part
        int cur = Integer.parseInt(part);
        // if pos is 0 then just send numeric value
        // for next recursion
        if (pos == 0){
            getExprUtil(res, curExp + part, input,
                    target, pos + 1, cur, cur);


        }
        // try all given binary operator for evaluation
        else
        {
            getExprUtil(res, curExp + "+" + part, input,
                    target, pos + 1, curVal + cur, cur);
            getExprUtil(res, curExp + "-" + part, input,
                    target, pos + 1, curVal - cur, -cur);
            getExprUtil(res, curExp + "*" + part, input,
                    target, pos + 1, curVal - last + last * cur,
                    last * cur);
        }
    }

    // Below method returns all possible expression
    // evaluating to target
    static List<String> getExprs(String input, int target)
    {
        List<String> res = new ArrayList<String>();
        getExprUtil(res, "", input, target, 0, 0, 0);
        return res;
    }

    // method to print result
    static void printResult(List<String> res)
    {
        for (int i = 0; i < res.size(); i++)
            System.out.println(res.get(i) + " ");
        System.out.println();
    }

    public static void main(String[] args)
    {
        String input = "123";
        int target = 6;
        List<String> res = getExprs(input, target);
        printResult(res);

        input = "343";
        target = 15;
        res = getExprs(input, target);
        printResult(res);

        input = "3334";
        target = 18;
        res = getExprs(input, target);
        printResult(res);
    }
}