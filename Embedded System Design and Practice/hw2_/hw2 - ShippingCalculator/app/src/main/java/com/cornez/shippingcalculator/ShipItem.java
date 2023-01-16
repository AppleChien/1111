package com.cornez.shippingcalculator;


public class ShipItem {


    // DATA MEMBERS
    private boolean mGender;
    private double mBMR;

    public ShipItem() { mGender = true; }

    public void Compute(double H,double W, int A) {
        double ma=6.7550*A;

        if (mGender) {
            mBMR = 66.4730 + 13.7516 * W + 5.0033 * H;
            mBMR -= 6.7550 * A;
        }
        //66.4730 + 13.7516 x weight in kg + 5.0033 x height in cm – 6.7550 x age
        else {
            mBMR = 655.0955 + 9.5634 * W + 1.8496 * H;
            mBMR -= 4.6756 * A;
        }
        //655.0955 + 9.5634 x weight in kg + 1.8496 x height in cm – 4.6756 x age

    }

    public void Reset() { mGender = true; }

    public void ChangeGender() {
        if(mGender) mGender = false;
        else mGender = true;
    }

    public double getmBMR() { return mBMR; }
    public boolean getGender() { return mGender; }
}
