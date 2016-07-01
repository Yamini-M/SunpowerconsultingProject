package com.yaminitask;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.yaminitask.model.Make;
import com.yaminitask.model.VehicleModel;
import com.yaminitask.ui.TitleAndTextView;

public class CarDetailsActivity extends AppCompatActivity {
    private ListView mModelList;
    private Make carMake;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_models);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent in = getIntent();
        Bundle bundle = in.getExtras();
        carMake = bundle.getParcelable("carMake");
        getSupportActionBar().setTitle(carMake.getName());
        mModelList = (ListView) findViewById(R.id.model_list);
        mModelList.setAdapter(new MakeListAdapter(carMake, this));
        mModelList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Toast.makeText(CarDetailsActivity.this, "TODO - Needs Implementation as per the guidelines", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    public class MakeListAdapter extends BaseAdapter {

        private Make make;
        private Context context;

        public MakeListAdapter(Make make, Context context) {
            this.make = make;
            this.context = context;
        }

        @Override
        public int getCount() {
            return make.getModels().size();
        }

        @Override
        public Object getItem(int i) {
            return make.getModels().get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            LayoutInflater inflater = LayoutInflater.from(context);
            ViewHolder viewHolder;
            if (view == null) {
                view = inflater.inflate(R.layout.model_details_item, null);
                viewHolder = new ViewHolder();
                viewHolder.vehicleName = (TitleAndTextView) view.findViewById(R.id.vehicle_name);
                viewHolder.vehicleNiceName = (TitleAndTextView) view.findViewById(R.id.vehicle_nice_name);
                viewHolder.vehicleManufacturedYear = (TitleAndTextView) view.findViewById(R.id.year_manufactured);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            VehicleModel model = make.getModels().get(i);
            viewHolder.vehicleName.populateWith("Name: ", model.getName());
            viewHolder.vehicleNiceName.populateWith("Model: ", model.getId());
            viewHolder.vehicleManufacturedYear.populateWith("Year: ", "" + model.getYears().get(0).getYear());
            return view;
        }

        public class ViewHolder {
            TitleAndTextView vehicleName;
            TitleAndTextView vehicleNiceName;
            TitleAndTextView vehicleManufacturedYear;
        }

    }
}
