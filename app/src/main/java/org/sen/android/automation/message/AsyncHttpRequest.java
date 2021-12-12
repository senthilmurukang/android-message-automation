package org.sen.android.automation.message;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

class AsyncHttpRequest extends AsyncTask<String, Integer, String> {
    public AsyncHttpRequest() {
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected String doInBackground(String... args) {
        StringBuilder response = null;
        try {
            String urlArgument = args[0];
            String payloadArgument = null;
            if (args.length > 1)
                payloadArgument = args[1];

            URL url = new URL(urlArgument);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");

            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");

            con.setDoOutput(true);

            if (payloadArgument != null)
                try (OutputStream os = con.getOutputStream()) {
                    byte[] input = payloadArgument.getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }

            try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
                response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response != null ? response.toString() : null;
    }
}
