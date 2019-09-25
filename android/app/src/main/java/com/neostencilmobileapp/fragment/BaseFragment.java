package com.neostencilmobileapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.perf.metrics.AddTrace;

public abstract class BaseFragment extends Fragment implements
        View.OnClickListener,AsyncTask {

    protected AppCompatActivity activity;
    protected View v;

    @Override
    @AddTrace(name = "onCreateTrace", enabled = true /* optional */)

    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        super.onCreate(savedInstanceState);
        Log.e("onCreate", "savedInstanceState:" + savedInstanceState);
        if (getActivity().getIntent().getExtras() != null) {
            loadBundle(getActivity().getIntent().getExtras());
        }
    }

    protected void loadBundle(Bundle bundle) {
    }

    @Override
    @AddTrace(name = "onCreateViewTrace", enabled = true /* optional */)

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activity = (AppCompatActivity) getActivity();
        v = inflater.inflate(getViewID(), null);
        initViews();
        return v;

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
    public void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);
    }

    public abstract void initViews();

    public abstract int getViewID();

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

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

    public View findView(int id) {
        return v.findViewById(id);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
