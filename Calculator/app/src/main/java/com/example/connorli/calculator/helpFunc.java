package com.example.connorli.calculator;

/**
 * Created by Connor Li on 10/6/2017.
 */

public class helpFunc {
    protected String formatNum(String in) {
        return (in.contains(".")? in.replaceAll(".?0*$", ""):in);
    }
}
