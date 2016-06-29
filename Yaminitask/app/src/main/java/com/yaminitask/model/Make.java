package com.yaminitask.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import lombok.Getter;

/**
 * Created by Yamini on 28/06/16.
 */
@Getter
public class Make implements Parcelable {
    private long id;
    private String name;
    private String niceName;
    private List<VehicleModel> models;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.name);
        dest.writeString(this.niceName);
        dest.writeTypedList(this.models);
    }

    public Make() {
    }

    protected Make(Parcel in) {
        this.id = in.readLong();
        this.name = in.readString();
        this.niceName = in.readString();
        this.models = in.createTypedArrayList(VehicleModel.CREATOR);
    }

    public static final Parcelable.Creator<Make> CREATOR = new Parcelable.Creator<Make>() {
        @Override
        public Make createFromParcel(Parcel source) {
            return new Make(source);
        }

        @Override
        public Make[] newArray(int size) {
            return new Make[size];
        }
    };
}
