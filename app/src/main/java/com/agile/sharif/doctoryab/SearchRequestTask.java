
/**
 * Created by mina on 2/20/17.
 */

package com.agile.sharif.doctoryab;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.widget.ActionBarOverlayLayout;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import static android.R.id.content;


public class SearchRequestTask extends AsyncTask<Void, Void, JSONArray> {

    private Exception exception;
    private URL url;
    private ProgressBar progressBar;
    private LinearLayout layout;
    private Context mContext;
    private Activity activity;
    private String keyword;

    public SearchRequestTask(URL url, ProgressBar progressBar, LinearLayout layout, Context mContext, String keyword, Activity activity) {
        super();
//        int dataLen = data.length;
        this.url = url;
        this.layout = layout;
        this.progressBar = progressBar;
        this.mContext = mContext;
        this.activity = activity;
        this.keyword = keyword;

    }

    protected void onPreExecute() {
        progressBar.setVisibility(View.VISIBLE);
    }

    protected JSONArray doInBackground(Void... urls) {


        try {
            HashMap<String , String> data = new HashMap<>();
            data.put("keyword" , keyword);
            StringBuilder stringBuilder = new HttpRequest(url , "POST" , data).getResponseStringBuilder();
            JSONArray response = new JSONArray(stringBuilder.toString());
            return response;

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
            layout.removeAllViews();
            progressBar.setVisibility(View.GONE);
            for (int i = 0; i < response.length(); i++) {
                addDoctorCard(response.getJSONObject(i), layout, mContext);
            }

        } catch (JSONException e) {

        }

    }

    private void addDoctorCard(final JSONObject doctor, LinearLayout layout, Context mContext) throws JSONException {


        CardView card = new CardView(mContext);
        card.setMinimumWidth(1000);
        card.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        params.setMargins(15, 15, 15, 15);


        LinearLayout cardLayout = new LinearLayout(mContext);
        cardLayout.setOrientation(LinearLayout.HORIZONTAL);

        ImageView docImage = new ImageView(mContext);
        docImage.setImageResource(R.mipmap.doctor);

        LinearLayout cardInfoLayout = new LinearLayout(mContext);
        cardInfoLayout.setOrientation(LinearLayout.VERTICAL);
        cardInfoLayout.setPadding(5, 5, 15, 5);

        card.setContentPadding(15, 15, 15, 15);
        card.setLayoutParams(params);


        TextView docName = setTextView(16 ,doctor.getJSONObject("user").getJSONObject("user").get("first_name").toString() + " " + doctor.getJSONObject("user").getJSONObject("user").get("last_name").toString() );
        TextView docExpertise = setTextView(14 ,doctor.get("diploma").toString() + " " + doctor.getJSONObject("expertise").get("name").toString() );


        cardLayout.addView(docImage);
        cardInfoLayout.addView(docName);
        cardInfoLayout.addView(docExpertise);
        cardLayout.addView(cardInfoLayout);
        card.addView(cardLayout);

        layout.addView(card);

        setListenerForCard(card , doctor.get("id").toString());

    }

    private TextView setTextView(int fontSize , String text){
        TextView textView = new TextView(mContext);
        textView.setText(text);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, fontSize);
        textView.setTextColor(Color.BLACK);
        textView.setTextDirection(View.TEXT_DIRECTION_ANY_RTL);
        return textView;
    }

    private void setListenerForCard(CardView card , final String docID){
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    new DoctorRequestTask(new URL("http://10.0.2.2:8000/user/doctor/" +docID + "/"), activity).execute();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
