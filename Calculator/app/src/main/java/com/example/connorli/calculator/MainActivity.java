package com.example.connorli.calculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

class MainActivity extends AppCompatActivity implements View.OnClickListener {

    protected static String ViwTxtDisplay, ViwTxtDisplay2;
    protected static calcFunc calc_func;
    protected static basicFunc basic_func = new basicFunc();
    protected static formulaFunc formula_func = new formulaFunc();
    protected static String memory_func = "";
    protected static boolean is_formula_entry = false;
    protected static iofunc io_func = new iofunc();
    protected static boolean last_is_equal = false;
    RadioButton radio_fe;
    TextView viwTxt;
    TextView viwTxt2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViwTxtDisplay = "0";
        ViwTxtDisplay2 = "0";
        (findViewById(R.id.button0)).setOnClickListener(this);
        (findViewById(R.id.button1)).setOnClickListener(this);
        (findViewById(R.id.button2)).setOnClickListener(this);
        (findViewById(R.id.button3)).setOnClickListener(this);
        (findViewById(R.id.button4)).setOnClickListener(this);
        (findViewById(R.id.button5)).setOnClickListener(this);
        (findViewById(R.id.button6)).setOnClickListener(this);
        (findViewById(R.id.button7)).setOnClickListener(this);
        (findViewById(R.id.button8)).setOnClickListener(this);
        (findViewById(R.id.button9)).setOnClickListener(this);
        (findViewById(R.id.button_plus)).setOnClickListener(this);
        (findViewById(R.id.button_minus)).setOnClickListener(this);
        (findViewById(R.id.button_multi)).setOnClickListener(this);
        (findViewById(R.id.button_div)).setOnClickListener(this);
        (findViewById(R.id.button_equal)).setOnClickListener(this);
        (findViewById(R.id.button_correct)).setOnClickListener(this);
        (findViewById(R.id.button_clear)).setOnClickListener(this);
        (findViewById(R.id.button_store)).setOnClickListener(this);
        (findViewById(R.id.button_recall)).setOnClickListener(this);
        (findViewById(R.id.button_decimal)).setOnClickListener(this);
        radio_fe = (RadioButton) findViewById(R.id.radio_formula_entry);
        radio_fe.setOnClickListener(this);
        viwTxt = (TextView) findViewById(R.id.viwTxt);
        viwTxt2 = (TextView) findViewById(R.id.viwTxt2);
        //by default, we use basic func
        calc_func = basic_func;

    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.radio_formula_entry) formula_entry();
        else if (view.getId() == R.id.button0) edtAddInteger(0);
        else if (view.getId() == R.id.button1) edtAddInteger(1);
        else if (view.getId() == R.id.button2) edtAddInteger(2);
        else if (view.getId() == R.id.button3) edtAddInteger(3);
        else if (view.getId() == R.id.button4) edtAddInteger(4);
        else if (view.getId() == R.id.button5) edtAddInteger(5);
        else if (view.getId() == R.id.button6) edtAddInteger(6);
        else if (view.getId() == R.id.button7) edtAddInteger(7);
        else if (view.getId() == R.id.button8) edtAddInteger(8);
        else if (view.getId() == R.id.button9) edtAddInteger(9);
        else if (view.getId() == R.id.button0) edtAddInteger(10);
        else if (view.getId() == R.id.button_plus) {

            setOperator(0);
            setAllDisplay();
        } else if (view.getId() == R.id.button_minus) {
            setOperator(1);
            setAllDisplay();
        } else if (view.getId() == R.id.button_multi) {
            setOperator(2);
            setAllDisplay();
        } else if (view.getId() == R.id.button_div) {
            setOperator(3);
            setAllDisplay();
        } else if (view.getId() == R.id.button_equal) {
            if (!calc_func.calculate())
                Toast.makeText(MainActivity.this, "Void Operation", Toast.LENGTH_SHORT).show();
            last_is_equal = true;
            setAllDisplay();
        } else if (view.getId() == R.id.button_clear) {
            calc_func.allClear();
            setAllDisplay();
        } else if (view.getId() == R.id.button_correct) {
            if (!calc_func.correction())
                Toast.makeText(MainActivity.this, "Void Operation", Toast.LENGTH_SHORT).show();
            setAllDisplay();
        } else if (view.getId() == R.id.button_recall) recall();
        else if (view.getId() == R.id.button_store) store();
        else if (view.getId() == R.id.button_decimal) decimalKey();

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString("memory_func", memory_func);
        savedInstanceState.putBoolean("is_formula_entry", is_formula_entry);
        savedInstanceState.putString("ViwTxtDisplay", ViwTxtDisplay);
        savedInstanceState.putString("viwTxt2", viwTxt2.getText().toString());
        if (is_formula_entry) {
            savedInstanceState.putIntegerArrayList("operator", formulaVar.operator);
            savedInstanceState.putStringArrayList("operand", formulaVar.operand);
        } else {
            savedInstanceState.putString("operand_1", basicVar.operand_1);
            savedInstanceState.putString("operand_2", basicVar.operand_2);
            savedInstanceState.putInt("operator", basicVar.operator);

        }
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        is_formula_entry = savedInstanceState.getBoolean("is_formula_entry");
        if (is_formula_entry) calc_func = formula_func;
        memory_func = savedInstanceState.getString("memory_func");
        ViwTxtDisplay = (savedInstanceState.getString("ViwTxtDisplay"));
        if (is_formula_entry) {
            formulaVar.operator = savedInstanceState.getIntegerArrayList("operator");
            formulaVar.operand = savedInstanceState.getStringArrayList("operand");

        } else {
            basicVar.operand_1 = savedInstanceState.getString("operand_1");
            basicVar.operand_2 = savedInstanceState.getString("operand_2");
            basicVar.operator = savedInstanceState.getInt("operator");
        }

        radio_fe.setChecked(is_formula_entry);
        calc_func.displayFullFunctions();
        viwTxt.setText(ViwTxtDisplay);
        viwTxt2.setText(savedInstanceState.getString("viwTxt2"));
    }

    private void setOperator(int in) {
        if (!calc_func.setOperator(in))
            Toast.makeText(MainActivity.this, "Void Operation", Toast.LENGTH_SHORT).show();
        else last_is_equal = false;

    }

    /*this function will switch between basic func and formula func*/
    protected void formula_entry() {
        if (is_formula_entry) {//set back to basic entry
            radio_fe.setChecked(false);
            is_formula_entry = false;
            calc_func = basic_func;
            calc_func.allClear();
            Toast.makeText(MainActivity.this, "Basic Entry", Toast.LENGTH_SHORT).show();
        } else {
            radio_fe.setChecked(true);
            is_formula_entry = true;
            calc_func = formula_func;
            calc_func.allClear();
            Toast.makeText(MainActivity.this, "formula Entry", Toast.LENGTH_SHORT).show();
        }

        setAllDisplay();
    }

    protected void store() {
        memory_func = ViwTxtDisplay;
        Toast.makeText(MainActivity.this, "Value " + memory_func + " Stored!", Toast.LENGTH_SHORT).show();


    }

    protected void recall() {
        ViwTxtDisplay = memory_func;
        setAllDisplay();
    }

    public void decimalKey() {
        if (last_is_equal) {
            ViwTxtDisplay = "0";
            last_is_equal = false;
        }
        if (!ViwTxtDisplay.contains(".")) {//if there is already a decimal there, it would be null operation
            ViwTxtDisplay = ViwTxtDisplay + ".";
            calc_func.displayFullFunctions();
            setAllDisplay();

        } else
            Toast.makeText(MainActivity.this, "There is already a decimal", Toast.LENGTH_SHORT).show();
    }

    /*for text that are numbers, this function will add text to the textView*/
    public void edtAddInteger(int in) {
        if (last_is_equal) {
            ViwTxtDisplay = "0";
            last_is_equal = false;
        }
        if (ViwTxtDisplay.equals("0")) ViwTxtDisplay = "";
        ViwTxtDisplay = ViwTxtDisplay + Integer.toString(in);
        calc_func.displayFullFunctions();
        setAllDisplay();
    }

    private void setAllDisplay() {
        viwTxt.setText(ViwTxtDisplay);
        viwTxt2.setText(ViwTxtDisplay2);
    }
}
