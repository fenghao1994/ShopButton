package com.example.fenghao.towbutton;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TowButton towButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        towButton = (TowButton) findViewById(R.id.ttt);

        Toast.makeText(this, towButton.getTextNum() + "", Toast.LENGTH_LONG).show();
    }
}
