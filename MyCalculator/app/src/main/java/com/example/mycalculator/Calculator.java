package com.example.mycalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.math.BigDecimal;

public class Calculator extends AppCompatActivity {

    TextView resultField;
    EditText numberField;
    TextView operationField;
    Double operand = null;
    String lastOperation = "=";
    boolean commaExists = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        resultField =(TextView) findViewById(R.id.resultField);
        numberField = (EditText) findViewById(R.id.numberField);
        operationField = (TextView) findViewById(R.id.operationField);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("OPERATION", lastOperation);
        if(operand != null)
            outState.putDouble("OPERAND", operand.doubleValue());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        lastOperation = savedInstanceState.getString("OPERATION");
        operand= savedInstanceState.getDouble("OPERAND");
        resultField.setText(operand.toString());
        operationField.setText(lastOperation);
    }

    public void onNumberClick(View view) {
        Button button = (Button)view;
        if(button.getText().toString().equals(",") && commaExists)
        {
            return;
        }
        if(button.getText().toString().equals(","))
        {
            commaExists = true;
        }
        numberField.append(button.getText());
        if(lastOperation.equals("=") && operand!=null){
            operand = null;
        }
    }

    public void onOperationClick(View view) {
        if(view instanceof Button) {
            Button button = (Button)view;
            String op = button.getText().toString();
            String number = numberField.getText().toString();
            if(number.length()>0) {
                number = number.replace(',', '.');
                try{
                    performOperation(Double.valueOf(number), op);
                }catch (NumberFormatException ex){
                    numberField.setText("");
                }
            }
            else if(op.equals("-") && numberField.length() == 0) {
                numberField.setText("-");
            }
            lastOperation = op;
        }
        else if(view instanceof ImageView) {
            if(view.getId() == R.id.back) {
                operand = null;
                resultField.setText("");
                numberField.setText("");
            }

        }
    }

    private void performOperation(Double number, String operation){
        if (operation.equals("sin")){
            operand = Math.sin(number);
        }
        else if (operation.equals("cos")){
            operand = Math.cos(number);
        }
        if(lastOperation.equals("=")){
            lastOperation = operation;
        }
        switch(lastOperation) {
            case "=":
                operand = number;
                break;
            case "/":
                if (number == 0) {
                    operand = 0.0;
                } else {
                    operand /= number;
                }
                break;
            case "*":
                operand *= number;
                break;
            case "+":
                operand += number;
                break;
            case "-":
                operand -= number;
                break;
        }
        commaExists = false;
        resultField.setText(operand.toString().replace('.', ','));
        numberField.setText("");
    }
}