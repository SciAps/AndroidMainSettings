package com.sciaps.android.libs.mainsettings.utils;

import android.app.Service;
import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.sciaps.android.libs.mainsettings.R;

/**
 * Created by MonkeyFish on 1/17/14.
 */
public class ButtonPref extends Preference implements View.OnClickListener{

    private static final String TAG = "CalibratePref";

    public ButtonPref(Context context) {
        super(context);
    }
    public ButtonPref(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ButtonPref(Context context, AttributeSet attrs,
                           int defStyle) {
        super(context, attrs, defStyle);
    }

    private static Button mBtn;
    private TextView mSummary;

    private String lastSummary="Start Cal";

    @Override
    protected View onCreateView(ViewGroup parent) {

        LayoutInflater li = (LayoutInflater) getContext().getSystemService(Service.LAYOUT_INFLATER_SERVICE);
        View myLayout=li.inflate(R.layout.button_preference, null, false);

        ((ViewGroup)myLayout.findViewById(R.id.preference_super_container)).addView(super.onCreateView(parent));

        mBtn=(Button) myLayout.findViewById(R.id.btn_pref);
        mBtn.setOnClickListener(this);

        mSummary=(TextView) myLayout.findViewById(R.id.summary);

        if (lastSummary!=null){
            mSummary.setText(lastSummary);
        }

        return myLayout;
    }



    public void setSummery(String text){
        if (mSummary!=null){

            mSummary.setText(text);
        } else {
            lastSummary=text;
        }
    }
//
//    @Override
//    protected void onClick() {
//        super.onClick();
//        Log.w(TAG,"Not Calibrating");
//
//    }

    @Override
    public void onClick(View view) {
        //CALIBRATE
        Log.w(TAG,"Calibrating");
    }
}