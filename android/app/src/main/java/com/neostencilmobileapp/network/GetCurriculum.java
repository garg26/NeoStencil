package com.neostencilmobileapp.network;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import com.google.gson.Gson;
import com.neostencilmobileapp.R;
import com.neostencilmobileapp.model.HttpParamObject;
import com.neostencilmobileapp.adapter.UnitName;
import com.neostencilmobileapp.adapter.UnitTopic;
import com.neostencilmobileapp.model.UserBatchCurriculumList;
import com.neostencilmobileapp.model.UserBatchUnitModel;

import java.io.IOException;
import java.util.ArrayList;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Response;

public class GetCurriculum extends AsyncTask<Object,Object,Object> {
    private OnTaskCompleted listener;


    public GetCurriculum(OnTaskCompleted onTaskCompleted){
        this.listener=onTaskCompleted;

    }

    @Override
    protected Object doInBackground(Object... objects) {
        HttpParamObject httpParamObject = (HttpParamObject) objects[0];

        OkHttpClient client;
        client = new OkHttpClient().newBuilder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .build();

        okhttp3.Request.Builder builder = new okhttp3.Request.Builder().url(httpParamObject.getUrl());

        for (Map.Entry<String, String> pair : httpParamObject.getHeaders().entrySet()) {
            builder.addHeader(pair.getKey(), pair.getValue());
        }
        Response response = null;
        try {
             response = client.newCall(builder.build()).execute();

            if(response.isSuccessful()){
               return new Gson().fromJson(response.body().string(),UserBatchCurriculumList.class);
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
            UserBatchCurriculumList userBatchCurriculumList = (UserBatchCurriculumList) o;
            LinkedHashMap<String, List<UserBatchUnitModel>> response = userBatchCurriculumList.getResponse();

            if(response.size()>0){

                Integer unitId = response.values().iterator().next().get(0).getUnit().getUnitId();

                List<UnitTopic> curriculumList = new ArrayList<>();


                for(String key : response.keySet()){
                    List<UserBatchUnitModel> list = response.get(key);
                    List<UnitName> unitNameList = null;



                    if(list!=null && list.size()>0){
                        unitNameList = new ArrayList<>();

                        for(UserBatchUnitModel userBatchUnitModel : list){
                            UnitName unitName = new UnitName(userBatchUnitModel.getUnit().getTitle(),userBatchUnitModel.getUnit().getType(),userBatchUnitModel.getUnit().getUnitId(), Color.GRAY);
                            unitNameList.add(unitName);
                        }


                    }

                    UnitTopic unitTopic = new UnitTopic(key,unitNameList, R.drawable.ic_down_arrow);
                    curriculumList.add(unitTopic);

                }

                listener.onTaskCompleted(curriculumList,unitId);

            }



        }

    }



    public interface OnTaskCompleted{
        void onTaskCompleted(List<UnitTopic> list, Integer unitId);
    }


}
