package com.agile.sharif.doctoryab;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by mina on 2/22/17.
 */

public class LoginFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.login_fragment, container, false);
        final TextView username = (TextView) view.findViewById(R.id.login_user);
        final TextView password = (TextView) view.findViewById(R.id.login_pass);
        final TextView mesasge = (TextView) view.findViewById(R.id.login_msg);

        Button submitBtn = (Button) view.findViewById(R.id.login_btn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("try to login");
                try {
                     HashMap<String,String> data = new HashMap<>();
                    data.put("username" , username.getText().toString());
                    data.put("password" , password.getText().toString());
                    new LoginRequestTask(new URL("http://10.0.2.2:8000/get_auth_token/") , mesasge , getActivity() , data).execute();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });
        return view;
    }
}