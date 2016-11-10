package com.fzuclover.putmedown;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }

    protected void toastShort(String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

}
