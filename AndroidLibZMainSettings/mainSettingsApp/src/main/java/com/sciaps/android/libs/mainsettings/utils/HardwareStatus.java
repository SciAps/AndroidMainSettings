package com.sciaps.android.libs.mainsettings.utils;

/**
 * Created by MonkeyFish on 1/17/14.
 */
public class HardwareStatus implements LibzHardwareStatus {



    @Override
    public int getBatteryLevel() {
        return 22;
    }

    @Override
    public String getBatteryStatus() {
        return "Charging";
    }

    @Override
    public int getArgonLevel() {
        return 4;
    }

    @Override
    public int[] getXYZStatus() {
        int[] xyz = {33,13,66};
        return xyz;
    }

    @Override
    public int getTempF() {
        return 79;
    }

    public int getTempC(){

        return (getTempF()-32)*  5/9;

    };
}

