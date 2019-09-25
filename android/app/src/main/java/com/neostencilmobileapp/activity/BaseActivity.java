package com.neostencilmobileapp.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.perf.metrics.AddTrace;
import com.neostencilmobileapp.fragment.AsyncTask;

public class BaseActivity extends AppCompatActivity implements
        View.OnClickListener, AsyncTask {

    @Override
    @AddTrace(name = "onCreateTrace", enabled = true /* optional */)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().getExtras() != null) {
            loadBundle(getIntent().getExtras());
        }
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);

    }

    protected void loadBundle(Bundle bundle) {
    }

    public void showToast(String message) {
        if (!TextUtils.isEmpty(message))
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
    }

    protected android.os.AsyncTask executeTask() {


        android.os.AsyncTask asyncTask = new android.os.AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                return backgroundTask(objects);

            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                preTask();
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                postTask(o);
            }
        };

        return asyncTask;
    }

    @Override
    public void preTask() {

    }

    @Override
    public void postTask(Object response) {

    }

    @Override
    public Object backgroundTask(Object... params) {
        return params[0];
    }


    @Override
    public void onClick(View view) {

    }

}
