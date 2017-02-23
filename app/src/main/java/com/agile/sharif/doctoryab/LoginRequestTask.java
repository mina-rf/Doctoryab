package com.agile.sharif.doctoryab;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by mina on 2/22/17.
 */

public class LoginRequestTask extends AsyncTask<Void ,Void , JSONObject> {

    private URL url ;
    private TextView message;
    private Context context ;
    HashMap<String,String> data;

    public LoginRequestTask(URL url , TextView message , Context context , HashMap<String,String> data){
        this.url = url;
        this.message = message;
        this.context = context;
        this.data = data;

    }

    @Override
    protected JSONObject doInBackground(Void... Urls) {
        try {
            StringBuilder stringBuilder = new HttpRequest(url , "POST" , data , null).getResponseStringBuilder();
            return new JSONObject(stringBuilder.toString());

        } catch (Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        }
    }

    protected void onPostExecute(JSONObject response) {
        try {

            System.out.println("here on postexeccute" + response);
            if (response == null) {
                System.out.println("here");
                message.setText("نام کاربری یا رمز عبور اشتباه است.");

            }
            else{
                message.setText("ورود موفقیت آمیز");
                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("token", response.get("token").toString());
                editor.commit();
                System.out.println("user logged in!");
                ((MainActivity)context).refresh();
            }


        } catch (JSONException e) {
            Log.e("ERROR", e.getMessage(), e);
        }

    }
}
