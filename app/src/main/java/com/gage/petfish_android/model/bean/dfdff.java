package com.gage.petfish_android.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/10/25.
 */

public class dfdff implements Parcelable {
    String useName;
    String usePwd;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.useName);
        dest.writeString(this.usePwd);
    }

    public dfdff() {
    }

    protected dfdff(Parcel in) {
        this.useName = in.readString();
        this.usePwd = in.readString();
    }

    public static final Parcelable.Creator<dfdff> CREATOR = new Parcelable.Creator<dfdff>() {
        @Override
        public dfdff createFromParcel(Parcel source) {
            return new dfdff(source);
        }

        @Override
        public dfdff[] newArray(int size) {
            return new dfdff[size];
        }
    };
}
