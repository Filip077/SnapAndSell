package com.newproject.snapandsell;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
private Button button_upload_activity,button_all_products_activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button_upload_activity = findViewById(R.id.button_upload_activity);
        button_all_products_activity =findViewById(R.id.button_all_products);

        button_upload_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,activity_upload.class);
                finish();
                startActivity(intent);
            }
        });
        button_all_products_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,activity_buy.class);
                finish();
                startActivity(intent);
            }
        });
    }
}
