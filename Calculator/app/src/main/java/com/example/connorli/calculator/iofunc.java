package com.example.connorli.calculator;

/**
 * Created by Connor Li on 10/8/2017.
 */

public class iofunc {
    protected String formatNum(String in) {
        //return (in.contains(".0") ? in.replaceAll(".?0*$", "") : in);
        return (in.endsWith(".0") ? in.replaceAll(".?0*$", "") : in);

    }

}
