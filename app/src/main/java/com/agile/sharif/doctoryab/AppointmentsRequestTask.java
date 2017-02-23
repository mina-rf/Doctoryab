package com.agile.sharif.doctoryab;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

/**
 * Created by mina on 2/22/17.
 */

public class AppointmentsRequestTask extends AsyncTask<Void , Void , JSONArray> {

    private URL url ;
    private LinearLayout layout;
    private Context context;

    public AppointmentsRequestTask(URL url , LinearLayout layout , Context context){
        this.url = url;
        this.layout = layout;
        this.context = context;

    }

    protected JSONArray doInBackground(Void... urls) {


        try {
            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
            String token = settings.getString("token", null);
            StringBuilder stringBuilder = new HttpRequest(url , "GET" , null,token).getResponseStringBuilder();
            return new JSONArray(stringBuilder.toString());
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        }
    }

    protected void onPostExecute(JSONArray response) {
        try {
            if (response == null) {
                response = new JSONArray("THERE WAS AN ERROR");
            }
//            response = response.getJSONObject(0).getJSONArray("appointments");
            System.out.println(response.length());
            for (int i = 0; i < response.length(); i++) {
                addAppointment(response.getJSONObject(i), layout , context);
            }


        } catch (JSONException e) {
            System.out.println(e.toString());
        }

    }

    private void addAppointment(JSONObject appointment, LinearLayout layout ,Context context) throws JSONException {

        System.out.println("add card");

        CardView card = new CardView(context);

        TextView date = setTextView( 14 ," تاریخ " + appointment.get("date").toString() );
        TextView time = setTextView( 14 , " ساعت " + appointment.get("start_time").toString());
        JSONObject doc = appointment.getJSONObject("doctor").getJSONObject("user").getJSONObject("user");
        TextView doctor = setTextView(14 , "دکتر " + doc.get("first_name") + " " + doc.get("last_name"));

        LinearLayout infoLayout = new LinearLayout(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(15, 15, 15, 15);
        infoLayout.setLayoutParams(params);
        infoLayout.setOrientation(LinearLayout.VERTICAL);
        infoLayout.setPadding(10,10,10,10);

        LinearLayout cardLayout = new LinearLayout(context);
        cardLayout.setLayoutParams(params);
        cardLayout.setOrientation(LinearLayout.HORIZONTAL);

        ImageView status = new ImageView(context);
        System.out.println("status" + appointment.get("get_status").toString());
        if (appointment.get("get_status").toString().equals("در انتظار تایید")){
            status.setImageResource(R.drawable.ic_sync_black_24dp);
        }else if (appointment.get("get_status").toString().equals("تایید شده")){
            status.setImageResource(R.drawable.ic_check_black_24dp);
        }else{
            status.setImageResource(R.drawable.ic_close_black_24dp);

        }

        infoLayout.addView(date);
        infoLayout.addView(time);
        infoLayout.addView(doctor);


        cardLayout.addView(status);
        cardLayout.addView(infoLayout);

        card.addView(cardLayout);

        card.setLayoutParams(params);
        layout.addView(card);

    }

    private TextView setTextView(int fontSize , String text){
        TextView textView = new TextView(context);
        textView.setText(text);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, fontSize);
        textView.setTextColor(Color.BLACK);
        textView.setTextDirection(View.TEXT_DIRECTION_ANY_RTL);
        return textView;
    }
}
