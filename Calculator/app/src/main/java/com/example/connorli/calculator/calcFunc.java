package com.example.connorli.calculator;

import android.os.Bundle;

/**
 * Created by Connor Li on 10/6/2017.
 */

public interface calcFunc {
    void iniFunc();

    void displayError();

    void displayFullFunctions();

    void allClear();

    /*for text that are operators, this function will add text to the textView*/
    boolean setOperator(int in);

    boolean calculate();

    boolean correction();


}
