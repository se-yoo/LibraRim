package kr.hs.emirim.yoosieun.librarim;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;

/**
 * Created by Student on 2017-11-22.
 */

public class ServiceInfofragment extends PreferenceFragment {
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.service_info);
    }
}