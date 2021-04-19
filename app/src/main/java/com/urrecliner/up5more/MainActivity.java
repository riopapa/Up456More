package com.urrecliner.up5more;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText uPriceEdit;
    Button calcButton, clearButton;
    TextView calcResultView;
    int uPrice = 10000;
    boolean isKospi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        uPriceEdit = findViewById(R.id.uPrice);
        calcButton = findViewById(R.id.calculate);
        clearButton = findViewById(R.id.clear);
        calcResultView = findViewById(R.id.calcResult);
        uPriceEdit.requestFocus();

        show_UpPrice();
        calcButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uPrice = Integer.parseInt(uPriceEdit.getText().toString());
                show_UpPrice();
                uPriceEdit.requestFocus();
                uPriceEdit.setSelection(uPriceEdit.getText().length());
            }
        });
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uPriceEdit.setText("");
                uPriceEdit.requestFocus();
                uPriceEdit.setSelection(uPriceEdit.getText().length());
            }
        });
    }

    void show_UpPrice() {
        isKospi = uPrice > 0;
        uPriceEdit.setText(String.valueOf(uPrice));
        uPriceEdit.setTextColor((isKospi) ? Color.BLACK: Color.GREEN);
        uPriceEdit.setFocusable(true);

        StringBuilder sb = new StringBuilder();
        for (float inc = 7f; inc >= 5f; inc -=0.5f)
            sb = addOneLine(inc, sb);
        sb.append("\n");
        sb = addOneLine(8f, sb);
        sb = addOneLine(10f, sb);
        sb = addOneLine(12f, sb);
        calcResultView.setText(sb);
    }

    StringBuilder addOneLine(float inc, StringBuilder sb ) {
        String s = String.format(Locale.getDefault(),"%4.1f", inc);
        sb.append(s); sb.append("%   ");
        sb.append(roundedPrice(uPrice,inc, isKospi));
        sb.append("\n");
        return sb;
    }

    int roundedPrice(int uPrice, float inc, boolean kospi) {
        if (!kospi)
            uPrice = -uPrice;
        uPrice += (int) ((float) uPrice * inc / 100f);
        if (uPrice < 1000)
            return uPrice;
        else if (uPrice < 5000)
            return (uPrice/5) * 5;
        else if (uPrice < 10000)
            return (uPrice/10) * 10;
        else if (uPrice < 50000)
            return (uPrice/50) * 50;
        if (kospi) {
            if (uPrice < 100000)
                return (uPrice / 100) * 100;
            else if (uPrice < 500000)
                return (uPrice / 500) * 500;
            else
                return (uPrice / 1000) * 1000;
        }
        else
            return (uPrice / 100) * 100;
    }

}