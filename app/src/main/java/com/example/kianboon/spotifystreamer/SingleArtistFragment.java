package com.example.kianboon.spotifystreamer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by KianBoon on 10-Jul-15.
 */
public class SingleArtistFragment extends Fragment {
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
        if (mImageView != null) {
            Picasso.with(getActivity()).load(mImageUri).into(mImageView);
        }
        else{
            mImageView.setImageResource(R.drawable.abc_ic_clear_mtrl_alpha);
        }

        return rootView;
    }
}
