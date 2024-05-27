package com.example.ddaveragecalculator;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Damage implements Parcelable {
    protected double mDmgMod;
    protected int mD4;
    protected int mD6;
    protected int mD8;
    protected int mD10;
    protected int mD12;
    protected double mExtent;

    protected Damage() {
        mDmgMod = 0;
        mD4 = 0;
        mD6 = 0;
        mD8 = 0;
        mD10 = 0;
        mD12 = 0;
        mExtent = 1;
    }

    protected Damage(int dmgMod, int d4, int d6, int d8, int d10, int d12, double extent) {
        mDmgMod = dmgMod;
        mD4 = d4;
        mD6 = d6;
        mD8 = d8;
        mD10 = d10;
        mD12 = d12;
        mExtent = extent;
    }

    protected Damage(Parcel in) {
        mDmgMod = in.readDouble();
        mD4 = in.readInt();
        mD6 = in.readInt();
        mD8 = in.readInt();
        mD10 = in.readInt();
        mD12 = in.readInt();
        mExtent = in.readDouble();
    }

    public static final Creator<Damage> CREATOR = new Creator<Damage>() {
        @Override
        public Damage createFromParcel(Parcel in) {
            return new Damage(in);
        }

        @Override
        public Damage[] newArray(int size) {
            return new Damage[size];
        }
    };

    public double CalcAvgDmg(boolean onlyDice) {
        return onlyDice ?
                (mD4 * 2.5) +
                (mD6 * 3.5) +
                (mD8 * 4.5) +
                (mD10 * 5.5) +
                (mD12 * 6.5) :
                ((mD4 * 2.5) +
                (mD6 * 3.5) +
                (mD8 * 4.5) +
                (mD10 * 5.5) +
                (mD12 * 6.5) +
                mDmgMod) * mExtent;
    }

    @NonNull
    public String toString() {
        return (mD12 > 0 ? mD12 + "d12 + " : "") +
                (mD10 > 0 ? mD10 + "d10 + " : "") +
                (mD8 > 0 ? mD8 + "d8 + " : "") +
                (mD6 > 0 ? mD6 + "d6 + " : "") +
                (mD4 > 0 ? mD4 + "d4 + " : "") +
                mDmgMod +
                (mExtent == 1 ? "" : " * " + mExtent);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(mDmgMod);
        parcel.writeInt(mD4);
        parcel.writeInt(mD6);
        parcel.writeInt(mD8);
        parcel.writeInt(mD10);
        parcel.writeInt(mD12);
        parcel.writeDouble(mExtent);
    }
}
