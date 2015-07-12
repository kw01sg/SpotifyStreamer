package com.example.kianboon.spotifystreamer;

import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.Tracks;

/**
 * Created by KianBoon on 10-Jul-15.
 */
public class SingleArtistFragment extends ListFragment {
    private static final String TAG = SingleArtistFragment.class.getSimpleName();

    public static final String EXTRA_ARTIST_ID = "com.example.kianboon.spotifystreamer.artistid";
    public static final String EXTRA_ARTIST_NAME = "com.example.kianboon.spotifystreamer.artistname";
    public static final String EXTRA_ARTIST_IMAGE_URL = "com.example.kianboon.spotifystreamer.imageurl";

    private String mArtistId;
    private String mArtistName;
    private String mImageUri;
    private List<Track> mTopTracks;
    private TrackAdapter mTrackAdapter;

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
        mTopTracks = new ArrayList<Track>();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.single_artist, null);

        getActivity().setTitle(mArtistName);

        mTrackAdapter = new TrackAdapter();
        setListAdapter(mTrackAdapter);

        FetchTracksTask tracksTask = new FetchTracksTask();
        tracksTask.execute(mArtistId);

        return rootView;
    }

    private class FetchTracksTask extends AsyncTask<String, Void, Tracks> {
        @Override
        protected Tracks doInBackground(String... params) {
            // Get top tracks of artist
            SpotifyApi api = new SpotifyApi();
            SpotifyService spotify = api.getService();

            Map<String, Object> options = new Hashtable<String, Object>();
            options.put("country", "sg");

            return spotify.getArtistTopTrack(mArtistId, options);
        }

        @Override
        protected void onPostExecute(Tracks tracks) {
            if (!mTopTracks.addAll(tracks.tracks)) {
                Toast toast = Toast.makeText(getActivity(), R.string.no_track_results, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();;
            }else {
                for (Track t : mTopTracks) {
                    mTrackAdapter.add(t);
                }
            }
        }
    }

    private class TrackAdapter extends ArrayAdapter<Track> {

        public TrackAdapter() {
            super(getActivity(), 0, new ArrayList<Track>());
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // If we are not given a convertView, inflate one
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_track, null);
            }

            Track track = getItem(position);

            // Configure Album Art
            ImageView albumImageView = (ImageView) convertView.findViewById(R.id.list_item_track_image_view);
            List<kaaes.spotify.webapi.android.models.Image> albumImages = track.album.images;
            if(albumImages.size() > 0) {
                Picasso.with(getActivity()).load(albumImages.get(0).url).into(albumImageView);
            }else {
                albumImageView.setImageResource(R.drawable.abc_ic_clear_mtrl_alpha);
            }

            // Configure Album TextView
            TextView albumTextView = (TextView) convertView.findViewById(R.id.list_item_track_album_title);
            String albumTitle = track.album.name;
            albumTextView.setText(albumTitle);


            // Configure Track TextView
            TextView trackTextView = (TextView) convertView.findViewById(R.id.list_item_track_track_title);
            String trackTitle = track.name;
            trackTextView.setText(trackTitle);

            return convertView;
        }
    }
}
