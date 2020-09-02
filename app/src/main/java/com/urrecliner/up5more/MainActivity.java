package com.urrecliner.up5more;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    EditText uPriceEdit;
    Button calcButton;
    TextView calcResultView;
    int uPrice = 1234;
    boolean kospi, updating = false;
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
        String nextLine = "\n\n";
        kospi = uPrice > 0;
        uPriceEdit.setText(""+uPrice);
        uPriceEdit.setTextColor((kospi) ? Color.BLACK: Color.GREEN);
        uPriceEdit.setFocusable(true);
//        uPriceEdit.setSelectAllOnFocus(true);
//        uPriceEdit.requestFocus();

        StringBuilder sb = new StringBuilder();
        sb.append(nextLine+"4.0% > "); sb.append(roundedPrice(uPrice*1040/1000,kospi));
        sb.append(nextLine+"4.5% > "); sb.append(roundedPrice(uPrice*1045/1000,kospi));
        sb.append(nextLine+"5.0% > "); sb.append(roundedPrice(uPrice*1050/1000,kospi));
        sb.append(nextLine+"5.5% > "); sb.append(roundedPrice(uPrice*1055/1000,kospi));
        sb.append(nextLine+"6.0% > "); sb.append(roundedPrice(uPrice*1060/1000,kospi));
        sb.append(nextLine+"6.5% > "); sb.append(roundedPrice(uPrice*1065/1000,kospi));
        calcResultView.setText(sb);
    }

    int roundedPrice(int uPrice, boolean kospi) {
        if (!kospi)
            uPrice = -uPrice;
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