package com.neostencilmobileapp.fragment;

public interface AsyncTask {

    public void preTask();

    public void postTask(Object response);

    public Object backgroundTask(Object... params);
}
