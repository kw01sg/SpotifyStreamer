package com.example.kianboon.spotifystreamer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by KianBoon on 09-Jul-15.
 */
public class MainFragment extends android.support.v4.app.Fragment {

    private static final String TAG = MainFragment.class.getSimpleName();
    private EditText mEditText;
    private Button mSearchButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.main_fragment, null);

        // Locate EditText field and Search Button
        mEditText = (EditText) rootView.findViewById(R.id.main_fragment_edit_text);

        // Put mEditText in focus
        mEditText.requestFocus();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        mSearchButton = (Button) rootView.findViewById(R.id.main_fragment_search_button);

        // Configure Search Button
        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchResult = mEditText.getText().toString();
                Log.d(TAG, searchResult);
                Intent i = new Intent(getActivity(), ResultActivity.class);
                i.putExtra(ResultActivity.EXTRA_ENTRY, searchResult);
                startActivity(i);
            }
        });

        return rootView;
    }
}
