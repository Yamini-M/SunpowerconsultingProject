package com.yaminitask;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.yaminitask.model.AllCarMakes;
import com.yaminitask.ui.VehicleListItem;

/**
 * Created by manasal on 28/06/16.
 */
public class CarsListFragment extends Fragment {

    private ListView mVehiclesList;
    private AllCarMakes mAllCarMakes;
    private Button logoutButton;

    public static CarsListFragment getInstance(AllCarMakes allCarMakes){
        CarsListFragment fragment = new CarsListFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("allCars", allCarMakes);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cars_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mVehiclesList = (ListView) view.findViewById(android.R.id.list);
        mAllCarMakes = (AllCarMakes) getArguments().get("allCars");
        mVehiclesList.setAdapter(new VehicleAdapter(mAllCarMakes, getContext()));
        mVehiclesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//               LayoutInflater l = LayoutInflater.from(getContext());
//                v= View.inflate(getContext(),R.layout.activity_login,null);
            }
        });
    }

    public class VehicleAdapter extends BaseAdapter {

        private AllCarMakes allCarMakes;
        private Context context;

        public VehicleAdapter(AllCarMakes allCarMakes, Context context) {
            this.allCarMakes = allCarMakes;
            this.context = context;
        }

        @Override
        public int getCount() {
            return allCarMakes.getMakes().size();
        }

        @Override
        public Object getItem(int i) {
            return allCarMakes.getMakes().get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            LayoutInflater inflater = LayoutInflater.from(context);
            if(view == null){
                view = inflater.inflate(R.layout.vehicle_list_item, null);
            }
            VehicleListItem vehicleListItem = (VehicleListItem) view;
            vehicleListItem.populateViewWith(context, allCarMakes.getMakes().get(i));
            return view;
        }

    }
}
