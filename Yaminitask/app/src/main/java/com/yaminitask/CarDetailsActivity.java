package com.yaminitask;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class CarDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_details);
        getSupportActionBar().setTitle("Car Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public static Intent packIntent(Context context){
        Intent intent = new Intent(context, CarDetailsActivity.class);
        return intent;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}
