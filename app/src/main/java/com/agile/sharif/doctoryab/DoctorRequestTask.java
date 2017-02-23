package com.agile.sharif.doctoryab;

/**
 * Created by mina on 2/21/17.
 */

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class DoctorRequestTask extends AsyncTask<Void, Void, JSONObject> {

    private Exception exception;
    private URL url;
    private Activity activity;

    public DoctorRequestTask(URL url ,Activity activity) {
        super();
        this.url = url;
        this.activity = activity;
    }



    protected JSONObject doInBackground(Void... urls) {


        try {
            StringBuilder stringBuilder = new HttpRequest(url , "GET" , null , null).getResponseStringBuilder();
            JSONObject response = new JSONObject(stringBuilder.toString());
            return response;
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        }
    }

    protected void onPostExecute(JSONObject response) {
        try {
            if (response == null) {
                response = new JSONObject("THERE WAS AN ERROR");

            }
            Fragment fragment = new DoctorFragment();
            Bundle args = getParams(response);
            fragment.setArguments(args);

            FragmentManager fragmentManager = activity.getFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.content_main , fragment).addToBackStack("tag");
            transaction.commit();


        } catch (JSONException e) {

        }

    }

    private Bundle getParams (JSONObject doctor){

        Bundle args = new Bundle();

        try {

            args.putString("name" , doctor.getJSONObject("user").getJSONObject("user").get("first_name").toString() + " "+ doctor.getJSONObject("user").getJSONObject("user").get("last_name").toString());
            args.putString("diploma" , doctor.getString("diploma"));
            args.putString("expertise" , doctor.getJSONObject("expertise").getString("name"));
            args.putString("addr" , doctor.getString("office_address"));
            args.putString("tel" , doctor.getString("office_phone_number"));
            ArrayList<String> insurances = new ArrayList<>();
            for(int i = 0 ; i < doctor.getJSONArray(("insurance")).length() ; i++){
                insurances.add(doctor.getJSONArray("insurance").getJSONObject(i).get("name").toString());
            }
            args.putStringArrayList("insurance" ,insurances);
        }
        catch (JSONException e){

        }
        return args;


    }


}
