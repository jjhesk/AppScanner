package com.vcoinapp.vendor;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.vcoinapp.vendor.task.redeemWorker;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hesk on 17/11/2014.
 */
public class RedemptionClaimManager {
    public static class param {
        public static final String production = "http://devlogin.vcoinapp.com";
        public static final String request = "/api/redemption/redeem_obtain_complex/?mac=%s&step=%s&qr=%s&trace_id=%s";

        public static UrlEncodedFormEntity getrequest_1(String mac, String qr) throws UnsupportedEncodingException {
            final List<NameValuePair> pairs = new ArrayList<NameValuePair>(2);
            pairs.add(new BasicNameValuePair("mac", mac));
            pairs.add(new BasicNameValuePair("step", "1"));
            pairs.add(new BasicNameValuePair("qr", qr));
            return new UrlEncodedFormEntity(pairs);
        }

        public static UrlEncodedFormEntity getrequest_2(String mac, String qr, String trace_id) throws UnsupportedEncodingException {
            final List<NameValuePair> pairs = new ArrayList<NameValuePair>(2);
            pairs.add(new BasicNameValuePair("mac", mac));
            pairs.add(new BasicNameValuePair("step", "2"));
            pairs.add(new BasicNameValuePair("qr", qr));
            pairs.add(new BasicNameValuePair("trace_id", trace_id));
            return new UrlEncodedFormEntity(pairs);
        }
    }

    public static class Data {
        private String QR1, QR2, trace_id, address, offer_expiry_date, distribution, product_name, extension;

        public int checkQR(String hash) throws Exception {
            if (hash.equalsIgnoreCase(QR1)) return 2;
            else if (hash.equalsIgnoreCase(QR2)) return 1;
            else throw new Exception("Not an valid QR, please check it again");
        }

        public String getQR1() {
            return QR1;
        }

        public String getQR2() {
            return QR2;
        }
    }

    private static boolean isProduction = true;
    public static String init_qr;
    public static int check_QR_n;
    public static Data current_d;
    private Context mcontext;
    private int step = 0;

    public RedemptionClaimManager(Context c) {
        mcontext = c;
        //    PATH = mcontext.getResources().getString(R.string.server_production);
    }

    public void read_qr_initializ(String data) throws Exception {
        if (data.length() != 32) throw new Exception("Not in the right format");
        init_qr = data;
        final redeemWorker re = new redeemWorker(mcontext);
        re.setStep(1);
        re.setQR(data);
        re.execute();
    }

    public void read_qr_second(String data) throws Exception {
        if (check_QR_n == 2 && !current_d.getQR2().equalsIgnoreCase(data) || check_QR_n == 1 && !current_d.getQR1().equalsIgnoreCase(data))
            throw new Exception("The second QR code is not validated. please check again");
        // final redeemWorker re = new redeemWorker(mcontext);
        // re.setStep(2);
        // re.setQR(data);
        //re.execute();
    }

    public void response_s1(String data) throws Exception {
        final Gson g = new Gson();
        current_d = g.fromJson(data, Data.class);
        check_QR_n = current_d.checkQR(init_qr);
    }

    public void response_s2(String data) {

    }

    public void store_http_result(String raw_data) {

    }

    public void redeem_error(String r) {

    }

    public int step() {
        return step;
    }
}
