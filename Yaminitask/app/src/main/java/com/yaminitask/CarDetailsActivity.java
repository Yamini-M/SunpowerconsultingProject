package com.yaminitask;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yaminitask.model.AllCarMakes;
import com.yaminitask.ui.VehicleListItem;

public class CarDetailsActivity extends AppCompatActivity {
//    private ImageView supplier_Image;
//    private TextView carName_TextView;
//    private TextView manufactured_Year;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_details);
        getSupportActionBar().setTitle("Car Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        supplier_Image = (ImageView) findViewById(R.id.img_supplier_logo);
//        carName_TextView = (TextView) findViewById(R.id.vehicle_name);
//        manufactured_Year = (TextView) findViewById(R.id.year_manufactured);
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
