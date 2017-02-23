package com.agile.sharif.doctoryab;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * Created by mina on 2/22/17.
 */

public class HttpRequest {

    private URL url;
    private String method;
    private HashMap<String, String> data;
    private String token;

    public HttpRequest(URL url, String method, HashMap<String, String> data, String token) {
        this.url = url;
        this.method = method;
        this.data = data;
        this.token = token;
    }

    public StringBuilder getResponseStringBuilder() throws IOException {

        StringBuilder stringBuilder = null;
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        System.out.println("response");

        if (token != null) {
            urlConnection.setRequestProperty("Authorization", "Token " + token);
        }


        if (data != null) {
            addParam(urlConnection);
        }
        urlConnection.setRequestMethod(method);

        try {
            System.out.println("response" + urlConnection.getResponseCode() + " " + urlConnection.toString());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            bufferedReader.close();
        } finally {
            urlConnection.disconnect();
        }


        return stringBuilder;
    }

    private void addParam(URLConnection urlConnection) throws IOException {

        urlConnection.setDoInput(true);
        urlConnection.setDoOutput(true);


        String dataStr = "";
        for (String key :
                data.keySet()) {
            dataStr += (URLEncoder.encode(key, "UTF-8") + "=" + URLEncoder.encode(data.get(key), "UTF-8") + "&");
        }

        dataStr = dataStr.substring(0, dataStr.length() - 1);
        System.out.println("here  " + urlConnection.toString() + " " + urlConnection.getOutputStream());
        OutputStream os = urlConnection.getOutputStream();
        BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(os, "UTF-8"));
        writer.write(dataStr);
        writer.flush();
        writer.close();
        os.close();


    }
}
