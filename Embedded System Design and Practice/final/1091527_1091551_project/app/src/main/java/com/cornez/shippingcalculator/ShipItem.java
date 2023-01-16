package com.cornez.shippingcalculator;

public class ShipItem {

    private Integer mAge;//年齡
    private Double mHeight;//身高
    private Double mWeight;//體重
    private Double mBMR;//BMR
    private Double mTDEE;//TDEE
    private Integer mTDEE_0;//TDEE整數結果
    private boolean  bg = true;//男女
    private boolean  mHeight_check = false;
    private boolean  mWeight_check = false;
    private boolean  mAge_check = false;
    private boolean  user1_save = false;
    private boolean  user2_save = false;
    private Integer state=1;//狀態->1,2,3,4,5

    public ShipItem() {
        mAge = 0;
        mHeight = 0.0;
        mWeight = 0.0;
        mBMR = 0.0;
        mTDEE = 0.0;
        state=1;
    }


    public void setReset(){
        bg=true;
        user1_save =false;
        user2_save =false;
        mAge = 0;
        mHeight = 0.0;
        mWeight = 0.0;
        mBMR = 0.0;
        mTDEE = 0.0;
        state=1;
    }


    private void comp () {
        if(bg == true)//男
        {
            mBMR = 66.4730+ ( 13.7516 * mWeight ) + ( 5.0033 * mHeight ) - ( 6.7550 * mAge );
            switch( state ){
                case 1:
                    mTDEE = 1.2 * mBMR;
                    break;
                case 2:
                    mTDEE = 1.375 * mBMR;
                    break;
                case 3:
                    mTDEE = 1.55 * mBMR;
                    break;
                case 4:
                    mTDEE = 1.725 * mBMR;
                    break;
                case 5:
                    mTDEE = 1.9 * mBMR;
                    break;
                default:
                    break;
            }

        }
        else//女
        {
            mBMR = 655.0955+ ( 9.5634 * mWeight ) + ( 1.8496 * mHeight ) - ( 4.6756 * mAge );
            switch( state ){
                case 1:
                    mTDEE = 1.2 * mBMR;
                    break;
                case 2:
                    mTDEE = 1.375 * mBMR;
                    break;
                case 3:
                    mTDEE = 1.55 * mBMR;
                    break;
                case 4:
                    mTDEE = 1.725 * mBMR;
                    break;
                case 5:
                    mTDEE = 1.9 * mBMR;
                    break;
                default:
                    break;
            }
        }

    }

    public void to1(){

        mBMR = Math.round(mBMR * 10.0) / 10.0;//四捨五入取小數一位
        mTDEE_0 = Math.toIntExact(Math.round(mTDEE));//四捨五入取小數一位
    }


    public void set (double h,double w,boolean bbg,int sta){
        mWeight = w;
        mHeight = h;
        //mAge = age;
        bg=bbg;
        state=sta;

        comp();
    }

    public void mode (int str){
        state=str+1;
    }

    public boolean str() {
        if(bg){
            bg=false;
            return false;
        }
        else{
            bg=true;
            return true;
        }
    }

    public Integer getmode() {
        return state;
    }

    public boolean getbg() {
        return bg;
    }

    public void setbg(boolean tf) {
        bg=tf;
    }

    /*public boolean getUser1_save() {
        return user1_save;
    }

    public boolean getUser2_save() {
        return user2_save;
    }*/

    public Integer getAge() {
        return mAge;
    }

    public Double getWeight() {
        return mWeight;
    }

    public Double getHeight() {
        return mHeight;
    }


    public Double getBMR() {
        to1();
        return mBMR;
    }

    public Integer getTDEE() {
        to1();
        return mTDEE_0;
    }

}
