package com.yaminitask.model;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.Getter;

/**
 * Created by jampalar on 28/06/16.
 */
@Getter
public class Year implements Parcelable {
    private int id;
    private int year;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.year);
    }

    public Year() {
    }

    protected Year(Parcel in) {
        this.id = in.readInt();
        this.year = in.readInt();
    }

    public static final Parcelable.Creator<Year> CREATOR = new Parcelable.Creator<Year>() {
        @Override
        public Year createFromParcel(Parcel source) {
            return new Year(source);
        }

        @Override
        public Year[] newArray(int size) {
            return new Year[size];
        }
    };
}
