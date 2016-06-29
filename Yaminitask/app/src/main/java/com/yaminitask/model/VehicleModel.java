package com.yaminitask.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import lombok.Getter;

/**
 * Created by Yamini on 28/06/16.
 */
@Getter
public class VehicleModel implements Parcelable {
    private String id;
    private String name;
    private String niceName;
    private List<Year> years;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.niceName);
        dest.writeTypedList(this.years);
    }

    public VehicleModel() {
    }

    protected VehicleModel(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.niceName = in.readString();
        this.years = in.createTypedArrayList(Year.CREATOR);
    }

    public static final Parcelable.Creator<VehicleModel> CREATOR = new Parcelable.Creator<VehicleModel>() {
        @Override
        public VehicleModel createFromParcel(Parcel source) {
            return new VehicleModel(source);
        }

        @Override
        public VehicleModel[] newArray(int size) {
            return new VehicleModel[size];
        }
    };
}
