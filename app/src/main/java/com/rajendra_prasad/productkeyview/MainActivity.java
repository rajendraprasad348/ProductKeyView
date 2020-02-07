package com.rajendra_prasad.productkeyview;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.rajendra_prasad.productkeylibrary.ProductKeyView;

public class MainActivity extends AppCompatActivity {

    private ProductKeyView pkv;
    private TextView tv_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_main);

        pkv = findViewById(R.id.pkv);
        tv_result = findViewById(R.id.tv_result);

        setUpViewReady();
    }


    private void setUpViewReady() {
        pkv.setPkvItemHeight(6);
        pkv.setPkvItemBackground(getResources().getDrawable(R.drawable.rectangle_box));
        pkv.setPkvMaxLength(4);
        pkv.setPkvEnabled(true);
        pkv.setPkvHint("- - - -");
        pkv.setPkvHintColor(getResources().getColor(R.color.text_default_color));
        pkv.setPkvTextColor(getResources().getColor(R.color.colorPrimaryDark));
        pkv.setPkvTextSize(50.00f);
        pkv.setPkvTextStyle(3);
        pkv.setPkvViewBackground(getResources().getColor(R.color.pkv_default_color));
    }


    public void clickHandle(View view) {
        if (pkv.isValidated()) {
            tv_result.setText("Result :\n" + pkv.getProductKeyText());
            pkv.clearProductKeyText();
        } else {
            Toast.makeText(this, "Enter required format of length: ( " + pkv.getPkvMaxLength() + " )\n" + "XXXX-XXXX-XXXX-XXXX", Toast.LENGTH_SHORT).show();
        }
    }
}
