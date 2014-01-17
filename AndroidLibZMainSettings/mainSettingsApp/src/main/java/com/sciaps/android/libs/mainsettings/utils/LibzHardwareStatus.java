package com.sciaps.android.libs.mainsettings.utils;

/**
 * Created by MonkeyFish on 1/17/14.
 */
public interface LibzHardwareStatus {

    int getBatteryLevel();

    int getArgonLevel();

    int[] getXYZStatus();

    int getTemp(boolean isFahrenheit);

}
