package com.example.connorli.calculator;


/**
 * Created by Connor Li on 10/6/2017.
 */

class basicFunc implements calcFunc {

    basicFunc() {
        iniFunc();
    }

    @Override
    public void iniFunc() {
        basicVar.operand_1 = "E";
        basicVar.operand_2 = "E";
        basicVar.operator = -1;
    }

    @Override
    public void displayError() {
        MainActivity.ViwTxtDisplay = "0";
        MainActivity.ViwTxtDisplay2 = (null);
        this.displayFullFunctions();

    }

    @Override
    public void displayFullFunctions() {
        MainActivity.ViwTxtDisplay2 = "";
        MainActivity.ViwTxtDisplay2 = MainActivity.ViwTxtDisplay2 + (basicVar.operand_1.equals("E") ? "" : basicVar.operand_1);
        MainActivity.ViwTxtDisplay2 = MainActivity.ViwTxtDisplay2 + (basicVar.operator == -1 ? "" : (basicVar.operator == 0 ? "+" : (basicVar.operator == 1 ? "-" : (basicVar.operator == 2 ? "*" : "/"))));
        MainActivity.ViwTxtDisplay2 = MainActivity.ViwTxtDisplay2 + (basicVar.operand_2.equals("E") ? "" : basicVar.operand_2);

    }

    @Override
    public void allClear() {
        MainActivity.ViwTxtDisplay = "0";
        MainActivity.ViwTxtDisplay2 = "";
        basicVar.operand_1 = "E";
        basicVar.operand_2 = "E";
        basicVar.operator = -1;
        displayFullFunctions();
    }

    @Override
    /*for text that are operators, this function will add text to the textView*/
    public boolean setOperator(int in) {
        if (basicVar.operator == -1){
            if (!basicVar.operand_1.equals("E")){
                basicVar.operator = in;
                displayFullFunctions();}
            else  {//only use the first operator :) bahaha
                    basicVar.operator = in;
                    basicVar.operand_1 = MainActivity.ViwTxtDisplay;
                    MainActivity.ViwTxtDisplay = "0";
                    displayFullFunctions();
                }
        } else return false;
        return true;
    }

    @Override
    public boolean calculate() {
        //avoid situation below for safety
        //ViwTxtDisplay == ""
        //Operand1 is "E"
        //Operand1 is ""
        if (basicVar.operand_1.equals("E")) return false;
        Double operand2 = 0.0;
        Double operand1 = Double.parseDouble(basicVar.operand_1);
        if(basicVar.operator!=-1){
            basicVar.operand_2 = MainActivity.ViwTxtDisplay;
            operand2 = Double.parseDouble(basicVar.operand_2);
        }
        switch (basicVar.operator) {
            //case of no operator selected
            case -1:
                if (basicVar.operand_1.equals("E"))
                    MainActivity.ViwTxtDisplay = (basicVar.operand_2);
                break;
            case 0:
                MainActivity.ViwTxtDisplay = MainActivity.io_func.formatNum(Double.toString(operand1 + operand2));
                break;
            case 1:
                MainActivity.ViwTxtDisplay = MainActivity.io_func.formatNum(Double.toString(operand1 - operand2));
                break;
            case 2:
                MainActivity.ViwTxtDisplay = MainActivity.io_func.formatNum(Double.toString(operand1 * operand2));
                break;
            case 3:
                if (operand2 == 0) {
                    displayError();
                    return false;
                } else {
                    MainActivity.ViwTxtDisplay = MainActivity.io_func.formatNum(Double.toString(operand1 / operand2));
                }
                break;
        }

        basicVar.operator = -1;
        basicVar.operand_1 = "E";
        basicVar.operand_2 = "E";
        displayFullFunctions();
        return true;
    }

    @Override
    public boolean correction() {
        if(!MainActivity.last_is_equal){
            if (!basicVar.operand_1.equals("E")||MainActivity.ViwTxtDisplay.length() >= 1 ) {
                if (MainActivity.ViwTxtDisplay.length() >= 2) //there are enough stuff to delete
                    MainActivity.ViwTxtDisplay = MainActivity.ViwTxtDisplay.subSequence(0, MainActivity.ViwTxtDisplay.length() - 1).toString();
                else if ((!MainActivity.ViwTxtDisplay.equals("0"))&&MainActivity.ViwTxtDisplay.length() == 1) //only one digit left
                    MainActivity.ViwTxtDisplay = "0";
                else if (MainActivity.ViwTxtDisplay.equals("0")) {//no digits left at all
                    if (basicVar.operator != -1) //if there is an operator left, we delete the operator
                        basicVar.operator = -1;
                        //when I still have an operand left, this will act on the whole operand
                    else if (!basicVar.operand_1.equals("E")){
                        MainActivity.ViwTxtDisplay = basicVar.operand_1;
                        basicVar.operand_1 = "E";
                    }
                    //ps. sir you have nothing to delete anymore
                    displayFullFunctions();
                }
            } else return false;
        }else return false;

        return true;
    }

}
