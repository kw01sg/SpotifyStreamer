package com.example.kianboon.spotifystreamer;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsPager;

/**
 * Created by KianBoon on 09-Jul-15.
 */
public class ResultFragment extends ListFragment {
    private static final String TAG = ResultFragment.class.getSimpleName();
    public static final String EXTRA_ENTRY = "com.example.kianboon.spotifystreamer.result";

    private String mSearchEntry;
    public ArtistAdapter mAdapter;
    private List<Artist> mArtists;

    public static ResultFragment newInstance(String result) {
        Bundle args = new Bundle();
        args.putString(EXTRA_ENTRY, result);

        ResultFragment fragment = new ResultFragment();
        fragment.setArguments(args);

        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSearchEntry = getArguments().getString(EXTRA_ENTRY);
        Log.d(TAG, mSearchEntry);
        getActivity().setTitle(mSearchEntry);

        mAdapter = new ArtistAdapter(new ArrayList<Artist>());
        setListAdapter(mAdapter);

        FetchResultTask resultTask = new FetchResultTask();
        resultTask.execute(mSearchEntry);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Artist artist = mArtists.get(position);

        String artistId = artist.id;
        String name = artist.name;

        String url;
        if (artist.images.size() > 0) {
            url = artist.images.get(0).url;
        }else {
            url = null;
        }

        Intent i = new Intent(getActivity(), SingleArtistActivity.class);
        i.putExtra(SingleArtistActivity.EXTRA_ARTIST_NAME, name);
        i.putExtra(SingleArtistActivity.EXTRA_ARTIST_ID, artistId);
        i.putExtra(SingleArtistActivity.EXTRA_ARTIST_IMAGE_URL, url);
        startActivity(i);

    }

    private class FetchResultTask extends AsyncTask<String, Void, ArtistsPager> {

        private final String TAG = FetchResultTask.class.getSimpleName();

        @Override
        protected ArtistsPager doInBackground(String... params) {
            if (params.length == 0)
                return null;

            SpotifyApi api = new SpotifyApi();
            SpotifyService spotify = api.getService();
            ArtistsPager results = spotify.searchArtists(params[0]);

            if (results == null) {
                Log.d(TAG, "results is null");
            }

            return results;
        }

        @Override
        protected void onPostExecute(ArtistsPager results) {
            mArtists = results.artists.items;

            for (Artist art : mArtists) {
                mAdapter.add(art);
            }
        }
    }

    private class ArtistAdapter extends ArrayAdapter<Artist> {
        private final String TAG = ArtistAdapter.class.getSimpleName();

        public ArtistAdapter(ArrayList<Artist> array) {
            super(getActivity(), 0, array);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // If we are not given with a View, inflate one
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_artist, null);
            }

            Artist artist = getItem(position);

            // Find TextView and ImageView
            TextView textView = (TextView) convertView.findViewById(R.id.list_item_artist_text_view);
            ImageView imageView = (ImageView) convertView.findViewById(R.id.list_item_artist_image_view);

            // Populate views
            if(artist.images.size() > 0) {
                Picasso.with(getActivity()).load(artist.images.get(0).url).into(imageView);
            }else {
                imageView.setImageResource(R.drawable.abc_ic_clear_mtrl_alpha);
            }
            textView.setText(artist.name);

            return convertView;
        }
    }


}
