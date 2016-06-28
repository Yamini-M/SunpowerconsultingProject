package com.yaminitask;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.yaminitask.model.AllCarMakes;

/**
 * Created by manasal on 28/06/16.
 */
public class CarsListActivity extends AppCompatActivity {

    private AllCarMakes allCarMakes;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_cars);
        unpackIntent();
    }

    public static Intent packIntent(Context context, AllCarMakes allCarMakes) {
        Intent intent = new Intent(context, CarsListActivity.class);
        intent.putExtra("allCars", allCarMakes);
        return intent;
    }

    private void unpackIntent() {
        allCarMakes = getIntent().getParcelableExtra("allCars");
        CarsListFragment carsListFragment = CarsListFragment.getInstance(allCarMakes);
        getSupportFragmentManager().beginTransaction().add(R.id.root_layout, carsListFragment).commit();
    }


}
