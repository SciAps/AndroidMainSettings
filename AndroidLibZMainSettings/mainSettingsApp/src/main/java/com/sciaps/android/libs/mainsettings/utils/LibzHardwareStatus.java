package com.sciaps.android.libs.mainsettings.utils;

/**
 * Created by MonkeyFish on 1/17/14.
 */
public interface LibzHardwareStatus {

    int getBatteryLevel();

    String getBatteryStatus(); //charging not connected

    int getArgonLevel();

    int[] getXYZStatus();

    int getTempF();

}
