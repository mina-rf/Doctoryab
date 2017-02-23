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

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by mina on 2/22/17.
 */

public class AppointmentsFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.appointments_fragment, container, false);
        LinearLayout layout = (LinearLayout) view.findViewById( R.id.apps_layout);
        try {

            new AppointmentsRequestTask(new URL("https://doctoryab.herokuapp.com/user/api/get-appointments/") ,layout , getActivity() ).execute();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return view;
    }

}
