package com.example.kianboon.spotifystreamer;

import android.support.v4.app.Fragment;

/**
 * Created by KianBoon on 09-Jul-15.
 */
public class ResultActivity extends SingleFragmentActivity {
    public static final String EXTRA_ENTRY = "com.example.kianboon.spotifystreamer.ResultActivity";

    @Override
    public Fragment createFragment() {
        String result = getIntent().getStringExtra(EXTRA_ENTRY);
        Fragment frag = ResultFragment.newInstance(result);
        return frag;
    }
}
