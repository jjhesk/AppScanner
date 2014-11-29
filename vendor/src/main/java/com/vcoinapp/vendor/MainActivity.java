package com.vcoinapp.vendor;

import android.app.Activity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.vcoinapp.vendor.intents.UtilBase;

public class MainActivity extends UtilBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);
        //  setContentView(R.layout.activity_scanner_fragment);
        if (savedInstanceState == null) {
            button_state();

        }
    }

    private void button_state() {
        getFragmentManager().beginTransaction()
                .add(R.id.container, new ResultFragment())
                .commit();
    }

    private void scan_state() {
        startActivity(new Intent(this, SimpleScannerActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void help_info(View v) {
        Log.d("111", "11");
    }

    public void start_scanning(View v) {
        scan_state();
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class ResultFragment extends Fragment {

        public ResultFragment() {

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.activity_main, container, false);
            return rootView;
        }

    }
}
