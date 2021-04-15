package com.example.mycalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class Calculator extends AppCompatActivity {

    TextView resultField;
    EditText numberField;
    TextView operationField;
    BigDecimal operand = BigDecimal.ZERO;
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
        operand= BigDecimal.valueOf(savedInstanceState.getDouble("OPERAND"));
        resultField.setText(operand.toString());
        operationField.setText(lastOperation);
    }

    public void onNumberClick(View view) {
        Button button = (Button)view;
        if(button.getText().toString().equals(","))
        {
            if (commaExists)
                return;
            else
                commaExists = true;
        }
        numberField.append(button.getText());
        if(lastOperation.equals("=") && operand!=null){
            operand = null;
        }
    }

    public void onOperationClick(View view) {
        if(view instanceof ImageView)
        {
            if(view.getId() == R.id.back)
            {
                operand = null;
                resultField.setText("");
                numberField.setText("");
                operationField.setText("");
            }
        }
        else {
            Button button = (Button) view;
            String op = button.getText().toString();
            String number = numberField.getText().toString();
            if (number.length() > 0) {
                number = number.replace(',', '.');
                try {
                    BigDecimal num = new BigDecimal(number);
                    performOperation(num, op);
                } catch (NumberFormatException ex) {
                    numberField.setText("");
                }
            }
            lastOperation = op;
            operationField.setText(lastOperation);
        }
    }

    private void performOperation(BigDecimal number, String operation){
        if (operation.equals("sin")){
            operand = BigDecimal.valueOf(Math.sin(number.doubleValue()));
        }
        else if (operation.equals("cos")){
            operand = BigDecimal.valueOf(Math.cos(number.doubleValue()));
        }
        else {
            if (operand == null) {
                operand = number;
            }
            else if (lastOperation.equals("=")) {
                lastOperation = operation;
            }
            switch (lastOperation) {
                case "=":
                    operand = number;
                    break;
                case "/":
                    if (number == BigDecimal.ZERO) {
                        operand = BigDecimal.valueOf(0.0);
                    } else {
                        operand = operand.divide(number, 10, RoundingMode.HALF_UP);
                    }
                    break;
                case "*":
                    operand = operand.multiply(number);
                    break;
                case "+":
                    operand = operand.add(number);
                    break;
                case "-":
                    operand = operand.subtract(number);
                    break;
            }
        }
        commaExists = false;
        operand = operand.round(new MathContext(10, RoundingMode.HALF_UP));
        operand = operand.stripTrailingZeros();
        resultField.setText(operand.toPlainString().replace('.', ','));
        numberField.setText("");
    }
}