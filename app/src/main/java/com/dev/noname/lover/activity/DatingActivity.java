package com.dev.noname.lover.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.dev.noname.lover.R;

public class DatingActivity extends AppCompatActivity {
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dating);
    toolbar=findViewById(R.id.tool_bar_newDating);
    setSupportActionBar(toolbar);
    getSupportActionBar().setTitle("New Dating");
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
