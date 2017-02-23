package com.agile.sharif.doctoryab;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by mina on 2/21/17.
 */

public class SearchFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.search_fragment, container, false);
        FloatingActionButton searchButton = (FloatingActionButton) view.findViewById(R.id.search_btn);
        final EditText searchEditText = (EditText) view.findViewById(R.id.search_key);
        final ProgressBar searchProgressBar = (ProgressBar) view.findViewById(R.id.search_progressBar);
        final LinearLayout searchResult = (LinearLayout) view.findViewById(R.id.search_result);


        searchButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                System.out.println("here");
                try {
                    new SearchRequestTask(new URL("https://doctoryab.herokuapp.com/appointment/search-key/"), searchProgressBar , searchResult ,getActivity(), searchEditText.getText().toString() , getActivity() ).execute();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });
        return view;
    }
}
