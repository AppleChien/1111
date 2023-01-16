package com.cornez.tapbuttoncounter;

class Counter {
    private int mCount;

    public Counter(){
        mCount = 0;
    }


    public void addCount(){
        if(mCount<20)
            mCount++;
    }

    public Integer getCount() {
        return mCount;
    }

    public void subCount() {
        if(mCount>0)
            mCount--;
    }

    public void setCount(int i) {
        mCount = i;
    }
}
