package com.urrecliner.up5more;

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

    EditText uPriceEdit;
    Button clearButton;
    TextView calcResultView;
    int uPrice;
    InputMethodManager imm;

    //    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        uPriceEdit = findViewById(R.id.uPrice);
        clearButton = findViewById(R.id.clear);
        calcResultView = findViewById(R.id.calcResult);
        uPriceEdit.setText("");
        uPriceEdit.requestFocus();

        clearButton.setOnClickListener(view -> {
            uPriceEdit.setText("");
            uPriceEdit.setSelection(uPriceEdit.getText().length());
            uPriceEdit.requestFocus();
            imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
//                InputMethodManager ih = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
//                ih.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        });

        uPriceEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().length() != 0)
                    show_UpPrice();
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
        });
    }

    void show_UpPrice() {
        uPrice = Integer.parseInt(uPriceEdit.getText().toString());
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
        sb.append(roundedPrice(uPrice,inc));
        sb.append("\n");
        return sb;
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