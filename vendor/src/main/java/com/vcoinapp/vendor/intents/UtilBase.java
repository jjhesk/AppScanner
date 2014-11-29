package com.vcoinapp.vendor.intents;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;


/**
 * Created by Hesk on 28/11/2014.
 */
public class UtilBase extends Activity {

    private ProgressDialog progressBar;
    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        prepare_progress_bar();

    }
    public void progressBar_dismiss() {
        if (progressBar.isShowing()) {
            progressBar.dismiss();
        }
    }

    public void progressBar_dismiss(int resId) {
        if (progressBar.isShowing()) {
            progressBar.dismiss();
        }
        final DialogSimpleNotification d = DialogSimpleNotification.newInstance(resId);
        d.show(getFragmentManager(), null);
    }

    public void progressBar_dismiss(String str, DsingleCB ccb) {
        if (progressBar.isShowing()) {
            progressBar.dismiss();
        }
        final DialogSimpleNotification d = DialogSimpleNotification.newInstance(str, ccb);
        d.show(getFragmentManager(), null);
        //simple_dialog_one_button(str, Constant.DialogCallBack.SINGLE_CLICK);
    }

    public void progressBar_dismiss(String str, DCBBool ccb) {
        if (progressBar.isShowing()) {
            progressBar.dismiss();
        }
        final DialogDoYouWantTo d = DialogDoYouWantTo.newInstance(str, ccb);
        d.show(getFragmentManager(), null);
        //simple_dialog_one_button(str, Constant.DialogCallBack.SINGLE_CLICK);
    }

    public void progressBar_dismiss(int strId, DCBBool ccb) {
        if (progressBar.isShowing()) {
            progressBar.dismiss();
        }
        final DialogDoYouWantTo d = DialogDoYouWantTo.newInstance(strId, ccb);
        d.show(getFragmentManager(), null);
        //simple_dialog_one_button(str, Constant.DialogCallBack.SINGLE_CLICK);
    }

    public void progressBar_dismiss(String str) {
        if (progressBar.isShowing()) {
            progressBar.dismiss();
        }
        final DialogSimpleNotification d = DialogSimpleNotification.newInstance(str);
        d.show(getFragmentManager(), null);
        //simple_dialog_one_button(str, Constant.DialogCallBack.SINGLE_CLICK);
    }

    private void prepare_progress_bar() {
        progressBar = new ProgressDialog(this);
        progressBar.setIndeterminate(true);
        progressBar.setCancelable(false);
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }


    public void progress_bar_start(final String info) {
       /* runOnUiThread(new Runnable() {
            @Override
            public void run() {*/

        progressBar.setMessage(info);
        progressBar.show();
        /*    }
        });*/
    }

    public void progress_bar_start(final int resId) {
        // runOnUiThread(new Runnable() {
        //   @Override
        //     public void run() {
        final String n = getResources().getString(resId);
        progressBar.setMessage(n);
        progressBar.show();
        //     }
        // });
    }

}
