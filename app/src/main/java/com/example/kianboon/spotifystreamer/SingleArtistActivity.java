package com.example.kianboon.spotifystreamer;

import android.support.v4.app.Fragment;

/**
 * Created by KianBoon on 10-Jul-15.
 */
public class SingleArtistActivity extends SingleFragmentActivity {
    private static final String TAG = SingleArtistActivity.class.getSimpleName();

    public static final String EXTRA_ARTIST_ID = "com.example.kianboon.spotifystreamer.artistid";
    public static final String EXTRA_ARTIST_NAME = "com.example.kianboon.spotifystreamer.artistname";
    public static final String EXTRA_ARTIST_IMAGE_URL = "com.example.kianboon.spotifystreamer.imageurl";

    @Override
    public Fragment createFragment() {
        String artistName = getIntent().getStringExtra(EXTRA_ARTIST_NAME);
        String artistID = getIntent().getStringExtra(EXTRA_ARTIST_ID);
        String imageUri = getIntent().getStringExtra(EXTRA_ARTIST_IMAGE_URL);

        return SingleArtistFragment.newInstance(artistID,artistName,imageUri);
    }
}
