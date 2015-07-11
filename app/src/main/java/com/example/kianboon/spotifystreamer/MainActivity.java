package com.example.kianboon.spotifystreamer;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by KianBoon on 09-Jul-15.
 */
public class MainActivity extends SingleFragmentActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    public Fragment createFragment() {
        return new MainFragment();
    }
}
