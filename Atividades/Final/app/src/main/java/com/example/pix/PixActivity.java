package com.example.pix;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class PixActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pix);
        setTitle(R.string.pix_title);
    }
}