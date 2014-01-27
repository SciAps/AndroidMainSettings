package com.sciaps.android.libs.mainsettings.utils;

import android.app.Service;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;

import com.sciaps.android.libs.mainsettings.R;

/**
 * Created by MonkeyFish on 1/20/14.
 */
public class XYZPref extends Preference implements View.OnClickListener, SeekBar.OnSeekBarChangeListener{

    private static final String TAG = "CalibratePref";
    private static final int MAX = 100;
    private SeekBar mSeekbar;

    public XYZPref(Context context) {
        super(context);
    }
    public XYZPref(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public XYZPref(Context context, AttributeSet attrs,
                      int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected View onCreateView(ViewGroup parent) {

        LayoutInflater li = (LayoutInflater) getContext().getSystemService(Service.LAYOUT_INFLATER_SERVICE);
        View myLayout=li.inflate(R.layout.xyzpreference, null, false);

        ((ViewGroup)myLayout.findViewById(R.id.preference_super_container)).addView(super.onCreateView(parent));

        Button mPBtn=(Button) myLayout.findViewById(R.id.btn_plus);
        Button mMBtn = (Button) myLayout.findViewById(R.id.btn_minus);

        mPBtn.setOnClickListener(this);
        mMBtn.setOnClickListener(this);

        mSeekbar  =(SeekBar)myLayout.findViewById(R.id.seekBar);

        mSeekbar.setOnSeekBarChangeListener(this);
        mSeekbar.setMax(MAX);

      //   mProgresslbl = (TextView)myLayout.findViewById(R.id.txt_progress);

        //SharedPreferences prefs = getContext().getSharedPreferences("libz_main", PreferenceActivity.MODE_PRIVATE);

        //mSeekbar.setProgress(prefs.getInt(this.getKey(),0));




        return myLayout;
    }



    @Override
    public void onClick(View view) {
        if (mSeekbar!=null){
            int val = mSeekbar.getProgress();

            if ("+".equals(view.getTag())){
                val++;
            }else {
                val--;
            }
            val = Math.min(100, Math.max(0, val));
            mSeekbar.setProgress(val);
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {}


    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }



}
