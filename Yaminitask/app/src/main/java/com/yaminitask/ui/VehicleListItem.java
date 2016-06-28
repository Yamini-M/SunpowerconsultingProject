package com.yaminitask.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yaminitask.R;
import com.yaminitask.model.Inventory;

/**
 * Created by manasal on 28/06/16.
 */
public class VehicleListItem extends LinearLayout {

    private ImageView supplierImage;
    private TextView carNameTextView;
    private TextView manufacturedYear;

    public VehicleListItem(Context context) {
        super(context);
    }

    public VehicleListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VehicleListItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //TODO initialize
        supplierImage = (ImageView) findViewById(R.id.img_supplier_logo);
        carNameTextView = (TextView) findViewById(R.id.vehicle_name);
        manufacturedYear = (TextView) findViewById(R.id.year_manufactured);
    }

    public void populateViewWith(Context context, Inventory inventory){
        String supplierLogoUrl = inventory.getMedia().getPhotos().getThumbnails().getLinks().get(0).getHref();
        Picasso.with(context).load(supplierLogoUrl).into(supplierImage);

    }
}
