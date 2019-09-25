package com.neostencilmobileapp.network;

import android.os.AsyncTask;
import com.google.gson.Gson;
import com.neostencilmobileapp.model.HttpParamObject;
import com.neostencilmobileapp.model.SingleUnitResponse;

import org.json.JSONObject;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

public class GetUnitById extends AsyncTask<Object,Object,Object> {

    private OnTaskCompleted onTaskCompleted;
    public GetUnitById(OnTaskCompleted onTaskCompleted){
        this.onTaskCompleted = onTaskCompleted;
    }


    @Override
    protected Object doInBackground(Object... objects) {

        HttpParamObject httpParamObject = (HttpParamObject) objects[0];

        OkHttpClient client = new OkHttpClient();
        client = new OkHttpClient().newBuilder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .build();

        okhttp3.Request.Builder builder = new okhttp3.Request.Builder().url(httpParamObject.getUrl());
        MediaType mediaType = MediaType.parse("application/json");

        Response response = null;

        for (Map.Entry<String, String> pair : httpParamObject.getHeaders().entrySet()) {
            builder.addHeader(pair.getKey(), pair.getValue());
        }
        try {
            final RequestBody body = RequestBody.create(mediaType, String.valueOf(new JSONObject(httpParamObject.getPostParams())));
            builder = builder.post(body);
            response = client.newCall(builder.build()).execute();

            if(response.isSuccessful()){
                if (response.body() != null) {
                    return new Gson().fromJson(response.body().string(),SingleUnitResponse.class);
                }
            }

            else{
                return null;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }




    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);

        if(o!=null){
            onTaskCompleted.onTaskCompleted((SingleUnitResponse) o);

        }

    }

    public interface OnTaskCompleted{
        void onTaskCompleted(SingleUnitResponse list);
    }


}

