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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.yaminitask.model.AllCarMakes;
import com.yaminitask.model.Make;
import com.yaminitask.ui.VehicleListItem;

public class CarDetailsActivity extends AppCompatActivity {
    private ImageView supplier_Image;
    private TextView carName_TextView;
    private TextView manufactured_Year;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_car_details);
            getSupportActionBar().setTitle("Car Details");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        supplier_Image = (ImageView) findViewById(R.id.img_logo);
        carName_TextView = (TextView) findViewById(R.id.vehicle_name1);
        manufactured_Year = (TextView) findViewById(R.id.year_manufactured1);
        try {
              Intent in = getIntent();
             Bundle bundle = in.getExtras();
             Make carMake = bundle.getParcelable("carMake");
            String supplierLogoUrl = "https://logo.clearbit.com/" + carMake.getName().toLowerCase() + ".com";
            Picasso.with(context).load(supplierLogoUrl).into(supplier_Image);
            carName_TextView.setText(carMake.getModels().get(0).getId().replaceAll("_", " "));
            manufactured_Year.setText("Manufactured year: "+carMake.getModels().get(0).getYears().get(0).getYear());
        }
        catch(NullPointerException e)
        {
            Toast.makeText(getApplicationContext(), "No data found", Toast.LENGTH_SHORT).show();
        }
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}
