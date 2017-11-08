package com.example.connorli.calculator;

import java.util.ArrayList;

/**
 * Created by Connor Li on 10/6/2017.
 */

class formulaFunc implements calcFunc {
    formulaFunc() {
        iniFunc();
    }

    @Override
    public void iniFunc() {
        formulaVar.operand = new ArrayList();
        formulaVar.operator = new ArrayList();
    }

    @Override
    public void displayError() {
        MainActivity.ViwTxtDisplay = "0";
        MainActivity.ViwTxtDisplay2 = "";
        this.displayFullFunctions();
    }

    @Override
    public void displayFullFunctions() {
        MainActivity.ViwTxtDisplay2 = "";
        for (int operand_i = 0; operand_i < formulaVar.operand.size(); operand_i++) {
            MainActivity.ViwTxtDisplay2 = MainActivity.ViwTxtDisplay2 + MainActivity.io_func.formatNum(formulaVar.operand.get(operand_i));
            if (formulaVar.operator.size() > operand_i)
                MainActivity.ViwTxtDisplay2 = MainActivity.ViwTxtDisplay2 + (formulaVar.operator.get(operand_i) == 0 ? "+" : (formulaVar.operator.get(operand_i) == 1 ? "-" : (formulaVar.operator.get(operand_i) == 2 ? "*" : "/")));
        }
    }

    @Override
    public void allClear() {
        MainActivity.ViwTxtDisplay = "0";
        MainActivity.ViwTxtDisplay2 = "";
        formulaVar.operand.clear();
        formulaVar.operator.clear();
        this.displayFullFunctions();
    }


    @Override
    /*for text that are operators, this function will add text to the textView*/
    public boolean setOperator(int in) {
        if (formulaVar.operator.size() <= formulaVar.operand.size()) {//only use the first operator :) bahaha
            formulaVar.operand.add(MainActivity.io_func.formatNum(MainActivity.ViwTxtDisplay));
            formulaVar.operator.add(in);
            //add operand to array
            MainActivity.ViwTxtDisplay = ("0");
            this.displayFullFunctions();
        } else return false;
        return true;
    }

    @Override
    public boolean calculate() {
        formulaVar.operand.add(MainActivity.io_func.formatNum(MainActivity.ViwTxtDisplay));
        this.displayFullFunctions();
        //we definitely have an operand, but do we have an operator?
        if (formulaVar.operator.size() == 0)
            formulaVar.operand.set(0, formulaVar.operand.get(formulaVar.operand.size() - 1));
        else {
            //result will stores at the first operand
            while (!formulaVar.operator.isEmpty()) {
                int operand_index = index_of_next_operator();
                //remove the operator that we are using
                int operator = formulaVar.operator.remove(operand_index);
                double operand1 = Double.parseDouble(formulaVar.operand.get(operand_index));
                double operand2 = Double.parseDouble(formulaVar.operand.remove(operand_index + 1));
                switch (operator) {
                    //case of no operator selected
                    //set the operand one to receive the (operand - operator - operant result)
                    case 0:
                        formulaVar.operand.set(operand_index, Double.toString(operand1 + operand2));
                        break;
                    case 1:
                        formulaVar.operand.set(operand_index, Double.toString(operand1 - operand2));
                        break;
                    case 2:
                        formulaVar.operand.set(operand_index, Double.toString(operand1 * operand2));
                        break;
                    case 3:
                        if (operand2 == 0) {
                            displayError();
                            return false;
                        } else
                            formulaVar.operand.set(operand_index, Double.toString(operand1 / operand2));
                        break;
                }
            }
        }
        MainActivity.ViwTxtDisplay = MainActivity.io_func.formatNum(formulaVar.operand.get(0));
        formulaVar.operator.clear();
        formulaVar.operand.clear();
        return true;
    }


    private int index_of_next_operator() {
        //first calculate multiplication & division in the array
        //compare who have a lower index

        int add_index = formulaVar.operator.indexOf(0);
        int sub_index = formulaVar.operator.indexOf(1);
        int multi_index = formulaVar.operator.indexOf(2);
        int div_index = formulaVar.operator.indexOf(3);
        int result = -1;

        if (multi_index > -1 && div_index > -1) result = Math.min(multi_index, div_index);
        else if (div_index > -1 && multi_index == -1) result = div_index;
        else if (multi_index > -1 && div_index == -1) result = multi_index;

        else if (add_index > -1 && sub_index > -1) result = Math.min(add_index, sub_index);
        else if (add_index > -1 && sub_index == -1) result = add_index;
        else if (sub_index > -1 && add_index == -1) result = sub_index;

        //then calculate addition and subtraction in the array

        //compare who have a lower index
        return result;
    }

    @Override
    public boolean correction() {
        if (!MainActivity.last_is_equal) {
            if (!formulaVar.operand.isEmpty() || MainActivity.ViwTxtDisplay.length() >= 1) {
                if (MainActivity.ViwTxtDisplay.length() >= 2) //there are enough stuff to delete
                    MainActivity.ViwTxtDisplay = MainActivity.ViwTxtDisplay.subSequence(0, MainActivity.ViwTxtDisplay.length() - 1).toString();
                else if ((!MainActivity.ViwTxtDisplay.equals("0")) && MainActivity.ViwTxtDisplay.length() == 1) //only one digit left
                    MainActivity.ViwTxtDisplay = "0";
                else if (MainActivity.ViwTxtDisplay.equals("0")) {//no digits left at all
                    if (!formulaVar.operator.isEmpty()&&!formulaVar.operand.isEmpty()) {
                        //if there is more operand than operator, remove operand
                        if (formulaVar.operator.size() < formulaVar.operand.size()) {
                            MainActivity.ViwTxtDisplay = formulaVar.operand.remove(formulaVar.operand.size()-1);
                        }
                        //if operator and operand has a same amount, remove the operator
                        else if (formulaVar.operator.size() == formulaVar.operand.size()) {
                            formulaVar.operator.remove(formulaVar.operator.size()-1);
                        }
                    }else MainActivity.ViwTxtDisplay = formulaVar.operand.remove(formulaVar.operand.size()-1);
                    displayFullFunctions();//ps. sir you have nothing to delete anymore
                }
            } else return false;
        } else return false;

        return true;
    }


}
