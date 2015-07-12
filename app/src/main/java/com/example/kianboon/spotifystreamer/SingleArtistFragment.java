package com.example.kianboon.spotifystreamer;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Tracks;

/**
 * Created by KianBoon on 10-Jul-15.
 */
public class SingleArtistFragment extends ListFragment {
    private static final String TAG = SingleArtistFragment.class.getSimpleName();

    public static final String EXTRA_ARTIST_ID = "com.example.kianboon.spotifystreamer.artistid";
    public static final String EXTRA_ARTIST_NAME = "com.example.kianboon.spotifystreamer.artistname";
    public static final String EXTRA_ARTIST_IMAGE_URL = "com.example.kianboon.spotifystreamer.imageurl";

    private ImageView mImageView;
    private String mArtistId;
    private String mArtistName;
    private String mImageUri;

    public static SingleArtistFragment newInstance(String artistId,
                                                   String artistName,
                                                   String imageUrl) {
        Bundle args = new Bundle();

        // Put data
        args.putString(EXTRA_ARTIST_ID, artistId);
        args.putString(EXTRA_ARTIST_NAME, artistName);
        args.putString(EXTRA_ARTIST_IMAGE_URL, imageUrl);

        SingleArtistFragment frag = new SingleArtistFragment();
        frag.setArguments(args);

        return frag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mArtistId = getArguments().getString(EXTRA_ARTIST_ID);
        mArtistName = getArguments().getString(EXTRA_ARTIST_NAME);
        mImageUri = getArguments().getString(EXTRA_ARTIST_IMAGE_URL);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.single_artist, null);

        Log.d(TAG, "fragment created");

        mImageView = (ImageView) rootView.findViewById(R.id.single_artist_image_view);
        getActivity().setTitle(mArtistName);
        if (mImageUri != null) {
            Picasso.with(getActivity()).load(mImageUri).into(mImageView);
        }
        else{
            mImageView.setImageResource(R.drawable.abc_ic_clear_mtrl_alpha);
        }

        return rootView;
    }

    private class FetchTracksTask extends AsyncTask<String, Void, Tracks> {
        @Override
        protected Tracks doInBackground(String... params) {
            SpotifyApi api = new SpotifyApi();
            SpotifyService spotify = api.getService();

            // TODO: Get top tracks of artist

        }
    }
}
