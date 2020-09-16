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
    Button calcButton;
    TextView calcResultView;
    int uPrice = 10000;
    boolean kospi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        uPriceEdit = findViewById(R.id.uPrice);
        calcButton = findViewById(R.id.calcurate);
        calcResultView = findViewById(R.id.calcResult);

        show_UpPrice();
        calcButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uPrice = Integer.parseInt(uPriceEdit.getText().toString());
                show_UpPrice();
            }
        });
    }

    void show_UpPrice() {
        kospi = uPrice > 0;
        uPriceEdit.setText(String.valueOf(uPrice));
        uPriceEdit.setTextColor((kospi) ? Color.BLACK: Color.GREEN);
        uPriceEdit.setFocusable(true);

        StringBuilder sb = new StringBuilder();
        for (float inc = 5.0f; inc < 10f; inc +=1f)
            sb = makeOneLine (inc, sb);
        sb = makeOneLine(10f, sb);
        sb = makeOneLine(20f, sb);
        sb = makeOneLine(25f, sb);
        calcResultView.setText(sb);
    }

    StringBuilder makeOneLine (float inc, StringBuilder sb ) {
        String s = String.format(Locale.getDefault(),"%04.1f", inc);
        sb.append(s); sb.append("% > ");
        sb.append(roundedPrice(uPrice,inc, kospi));
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