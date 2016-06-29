package com.yaminitask.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import lombok.Getter;

/**
 * Created by Yamini on 28/06/16.
 */
@Getter
public class AllCarMakes implements Parcelable {
    private List<Make> makes;
    private int makesCount;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.makes);
        dest.writeInt(this.makesCount);
    }

    public AllCarMakes() {
    }

    protected AllCarMakes(Parcel in) {
        this.makes = in.createTypedArrayList(Make.CREATOR);
        this.makesCount = in.readInt();
    }

    public static final Creator<AllCarMakes> CREATOR = new Creator<AllCarMakes>() {
        @Override
        public AllCarMakes createFromParcel(Parcel source) {
            return new AllCarMakes(source);
        }

        @Override
        public AllCarMakes[] newArray(int size) {
            return new AllCarMakes[size];
        }
    };
}
