package com.randywu.fancyfactory.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

import com.randywu.fancyfactory.R;


public class CustomizeView extends LinearLayout {
    private static final String TAG = CustomizeView.class.getSimpleName();
    public CustomizeView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public CustomizeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public CustomizeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray types = context.obtainStyledAttributes(attrs, R.styleable.CustomizeView);

        String result = types.getString(R.styleable.CustomizeView_test1);
        boolean generation = types.getBoolean(R.styleable.CustomizeView_happy, true);

        Log.d(TAG, "init result="+result + ", generation="+generation);
        types.recycle();
    }


    public void setTest1(int value) {
        Log.d(TAG, "setTest1");


    }
}
