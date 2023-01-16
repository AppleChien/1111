package com.example.android.notepad;

class Counter {
    private int mCount;
    boolean open=true;
    int click=0;

    public Counter(){
        mCount = 0;
    }

    public void onclick(){
        click++;
    }

    public void lock(int num){
        if(num==1&&click<3){
            if(open)
                open=false;
            else
                open=true;
        }
        else if(num==2){
            if(open==false)
                open=true;
        }
    }

    public void addCount(){
        if(open){
            click=0;
            if(mCount==20)
                mCount=20;
            else
                mCount++;
        }
    }

    public void subCount(){
        if(open){
            click=0;
            if(mCount==0)
                mCount=0;
            else
                mCount--;
        }
    }

    public void same(int f){
        if(open) {
            click=0;
            mCount=f;
        }
    }

    public Integer getCount() {
        return mCount;
    }

    public  Integer sub(int f)
    {
        int m=mCount;
        m -= f;
        return m;
    }

    public  Integer getF(int f)
    {
        if(f==6)
        {
            return mCount=1;
        }
        if(open&&click<3) {
            if (f == 0) {
                mCount = 0;
                return mCount;
            }
            if (f == 1 || f == 2) {
                mCount = 1;
                return mCount;
            }
            mCount = (getF(f - 1) + getF(f - 2));
            return mCount;
        }
        else
            return mCount;
    }


    public  Integer getP(int p)
    {
        if(open&&click<3) {
            if (p == 0 || p == 1 || p == 2) {
                mCount = 1;
                return mCount;
            }
            mCount = (getP(p - 2) + getP(p - 3));
            return mCount;
        }
        else
            return mCount;
    }

}
