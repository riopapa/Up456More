package com.urrecliner.calcuppercent;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText ePrice, eInc, eFromPct, eToPct;
    Button clearButton;
    TextView calcResultView;
    int uPrice;
    float vInc, vFromPct, vToPct;
    StringBuilder sb;
    InputMethodManager imm;

    //    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ePrice = findViewById(R.id.ePrice);
        clearButton = findViewById(R.id.clear);
        eInc = findViewById(R.id.eInc);
        eFromPct = findViewById(R.id.eFromPct);
        eToPct = findViewById(R.id.eToPct);
        calcResultView = findViewById(R.id.calcResult);
        ePrice.requestFocus();

        clearButton.setOnClickListener(view -> {
            ePrice.setText("");
            ePrice.setSelection(ePrice.getText().length());
            ePrice.requestFocus();
            imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        });

        ePrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().length() > 2)
                    show_UpPrice();
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
        });

        eFromPct.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                    show_UpPrice();
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
        });

        eToPct.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                show_UpPrice();
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
        });
    }

    void show_UpPrice() {
        try {
            uPrice = Integer.parseInt(ePrice.getText().toString());
            vInc = Float.parseFloat(eInc.getText().toString());
            vFromPct = Float.parseFloat(eFromPct.getText().toString());
            vToPct = Float.parseFloat(eToPct.getText().toString());
        } catch (Exception e) {
            return;
        }
        if (vInc == 0)
            return;

        sb = new StringBuilder();
        for (float inc = vFromPct; inc >= vToPct; inc -= (vInc + vInc)) {
            sb.append(genOneValue(inc));
            sb.append("       ");
            sb.append(genOneValue(inc - vInc));
            sb.append("\n");
        }
        sb.append("\n");
        calcResultView.setText(sb);
    }

    String genOneValue(float inc) {
        String s = String.format(Locale.getDefault(),"%5.1f", inc);
        s = s + "%" + String.format(Locale.getDefault(),"%,8d", roundedPrice(uPrice,inc));
        return s;
    }

    int roundedPrice(int uPrice, float inc) {
        uPrice += (int) ((float) uPrice * inc / 100f);
        if (uPrice < 1000)
            return uPrice;
        else if (uPrice < 5000)
            return  (uPrice+4) / 5 * 5;
        else if (uPrice < 10000)
            return (uPrice+9) / 10 * 10;
        return (uPrice+49) / 50 * 50;
    }
}