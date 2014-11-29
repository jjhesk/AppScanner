package com.vcoinapp.vendor.task;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.vcoinapp.vendor.MainThread;
import com.vcoinapp.vendor.R;
import com.vcoinapp.vendor.RedemptionClaimManager;
import com.vcoinapp.vendor.Util.Tool;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Hesk on 28/11/2014.
 */
public class redeemWorker extends AsyncTask<Void, Void, JSONObject> {
    private HttpParams httpParams;
    private Context c;
    private RedemptionClaimManager cb_manager;
    private String getMac, PATH;
    public static final String TAG = "Redeem Class";
    private int n_step;
    private String qr, trace_id;

    public static String convertStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is,
                "UTF-8"));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line + "\n");
        }
        is.close();
        return sb.toString();
    }

    public redeemWorker(Context c) {
        final MainThread m = (MainThread) c.getApplicationContext();
        cb_manager = m.getRM();
        httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, 5000);
        HttpConnectionParams.setSoTimeout(httpParams, 5000);
        getMac = Tool.get_mac_address(c);
        PATH = RedemptionClaimManager.param.production;
    }

    private JSONObject return_result(HttpResponse response, HttpEntity entity) throws Exception {
        String resultString = "";
        if (entity != null) {
            InputStream instream = entity.getContent();
            resultString = convertStreamToString(instream); // now you have
            System.out.println("RESPONSE: " + resultString);
            instream.close();
            if (response.getStatusLine().getStatusCode() == 200) {
                Log.d(TAG, "work just fine!");
                final JSONObject i = new JSONObject(resultString);
                return i;
            } else throw new Exception("header is not 200.");
        } else throw new Exception("no data result, please check with technical issue.");
    }

    public void setQR(String qr1) {
        qr = qr1;
    }

    public void setStep(int s) {
        n_step = s;
    }

    @Override
    protected JSONObject doInBackground(Void... v) {
        JSONObject resultStringo = new JSONObject();
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient(httpParams);
            HttpPost httpPostRequest = new HttpPost(PATH);
            if (n_step == 1) {
                httpPostRequest.setEntity(RedemptionClaimManager.param.getrequest_1(getMac, qr));
            }
            if (n_step == 2) {
                httpPostRequest.setEntity(RedemptionClaimManager.param.getrequest_2(getMac, qr, trace_id));
            }

            HttpResponse response = httpClient.execute(httpPostRequest);
            HttpEntity entity = response.getEntity();
            resultStringo = return_result(response, entity);
        } catch (Exception e) {
            Log.d("work ERROR", e.toString());
            resultStringo = new JSONObject();
            try {
                resultStringo.put("result", 1021);
                resultStringo.put("msg", e.toString());
            } catch (JSONException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
        return resultStringo;
    }

    @Override
    protected void onPostExecute(JSONObject result) {
        Log.d(TAG, "onPostExecute result == " + result.toString());
        try {
            deCodeJson(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        super.onPostExecute(result);
    }

    @Override
    protected void onPreExecute() {


        if (Tool.isOnline(c)) {
            super.onPreExecute();
            // assertEquals(0, progressBar.getProgress());
        } else {
            Tool.trace(c, R.string.warning_online_alert);
        }
    }

    protected void deCodeJson(JSONObject i) throws JSONException {
        int result = i.getInt("result");
        if (result == 1) {
            JSONObject data = i.getJSONObject("content");
            if (data != null) {
                //  cb_manager.store_http_result(data);
            }
        } else if (result == 1021) {
            cb_manager.redeem_error("timeout from the server connection. Please try again");
        } else {
            cb_manager.redeem_error(i.getString("msg") + " - " + result);
        }

    }

}
