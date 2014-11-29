package com.vcoinapp.vendor;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.vcoinapp.vendor.Util.Tool;
import com.vcoinapp.vendor.intents.UtilBase;

import java.util.ArrayList;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class SimpleScannerActivity extends UtilBase implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);

        mScannerView = new ZXingScannerView(this);
        final List<BarcodeFormat> l = new ArrayList<BarcodeFormat>();
        l.add(BarcodeFormat.QR_CODE);
        mScannerView.setFormats(l);
        // Programmatically initialize the scanner view
        setContentView(mScannerView); // Set the scanner view as the content view
    }


    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {
        final String tp = rawResult.getText();
        final String t = rawResult.getBarcodeFormat().toString();
        // Do something with the result here
        Log.v("TAG", tp); // Prints scan results
        Log.v("TAG", t); // Prints the scan format (qrcode, pdf417 etc.)
        try {

            progress_bar_start(R.string.please_wait);
            MainThread m = (MainThread) getApplication();
            m.getRM().read_qr_initializ(tp);
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
            r.play();
            ///  progressBar_dismiss();
            progressBar_dismiss();
            finish();
        } catch (Exception e) {
            //  e.printStackTrace();
            //   Tool.trace(this, e.getMessage());
            // Log.v("TAG", e.getMessage());
            final String tt = e.getMessage();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressBar_dismiss(tt);
                    mScannerView.startCamera();
                }
            });

        }
        // showMessageDialog("Contents = " + rawResult.getText() + ", Format = " + rawResult.getBarcodeFormat().toString());
    }
}