package com.neostencilmobileapp.activity;


import android.Manifest;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import com.facebook.FacebookSdk;
import com.google.firebase.perf.metrics.AddTrace;
import com.google.gson.Gson;
import com.neostencilmobileapp.R;
import com.neostencilmobileapp.adapter.UnitName;
import com.neostencilmobileapp.adapter.UnitTopic;
import com.neostencilmobileapp.common.AppConstants;
import com.neostencilmobileapp.model.HttpParamObject;
import com.neostencilmobileapp.network.GetCurriculum;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import io.flutter.app.FlutterActivity;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugins.GeneratedPluginRegistrant;
import io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin;

import com.google.firebase.perf.metrics.Trace;


public class MainActivity extends FlutterActivity {

    private static final String CHANNEL = "samples.flutter.io/platform_view";
    private static final String METHOD_SWITCH_VIEW = "switchView";
    private static final int GET_UNIT_REQUEST_CODE = 1;
    public static StringBuilder authToken = null;
    public static SharedPreferences sharedPreferences;
    public MethodChannel.Result result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(false);      // Disable the button
            actionBar.setDisplayHomeAsUpEnabled(false); // Remove the left caret
            actionBar.setDisplayShowHomeEnabled(false); // Remove the icon
        }

        GeneratedPluginRegistrant.registerWith(this);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);

        new MethodChannel(getFlutterView(), CHANNEL).setMethodCallHandler(
                (methodCall, result) -> {
                    this.result = result;

                    if (methodCall.method.equals(METHOD_SWITCH_VIEW)) {
                        int courseBatchId = methodCall.argument("courseBatchId");
                        authToken = new StringBuilder(methodCall.argument("authToken"));
                        getCurriculum(courseBatchId, authToken.toString());
                    } else if (methodCall.method.equals("sendMail")) {


                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        Date date = new Date();
                        System.out.println(formatter.format(date));


                        String android_id = Settings.Secure.getString(getContentResolver(),
                                Settings.Secure.ANDROID_ID);

                        DisplayMetrics displayMetrics = new DisplayMetrics();
                        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                        int height = displayMetrics.heightPixels / (displayMetrics.densityDpi/160);
                        int width = displayMetrics.widthPixels / (displayMetrics.densityDpi/160);


                        String bodyText = "Android Id:- "+android_id +"\n" + "Device Name:- "+android.os.Build.MANUFACTURER +" " +
                                android.os.Build.BRAND + " " + android.os.Build.PRODUCT + "\n" +
                                "Version:- " + android.os.Build.VERSION.RELEASE + "\n" + "SDK:- " + android.os.Build.VERSION.SDK_INT + "\n" +
                                "Width (in dp):- " +width + "\n" + "Height (in dp):- "+ height + "\n" + " Please don't edit above information, this will help us to serve your problem."
                                ;

                        Intent emailIntent = new Intent(Intent.ACTION_SEND);
                        emailIntent.setType("text/plain");
                        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"dev@neostencil.com"});
                        emailIntent.putExtra(Intent.EXTRA_SUBJECT, formatter.format(date) + " Feedback for NeoStencil app");
                        emailIntent.putExtra(Intent.EXTRA_TEXT, bodyText);
                        startActivity(emailIntent);

                    }
                    else {
                        result.notImplemented();
                    }
                }
        );
    }




    /**
     *
     * @param curriculumList
     * @param courseBatchId
     * @param firstUnitId
     *
     * Launch curriculum activity to show curriculum and show previous unit user had left if any, otherwise show first unit
     */
    private void onLaunchFullScreen(List<UnitTopic> curriculumList, int courseBatchId, int firstUnitId) {

        HashMap<String, Double> previousPlayUnit = getPreviousPlayUnit(curriculumList, courseBatchId, firstUnitId);

        Intent fullScreenIntent = new Intent(this, CurriculumActivity.class);
        fullScreenIntent.putExtra(AppConstants.UNIT_LIST, (Serializable) curriculumList);
        fullScreenIntent.putExtra(AppConstants.DEFAULT_UNIT_ID,previousPlayUnit.get("unitId"));
        fullScreenIntent.putExtra(AppConstants.DEFAULT_UNIT_TOPIC_POSITION,previousPlayUnit.get("position"));
        fullScreenIntent.putExtra(AppConstants.DEFAULT_UNIT_ID_DURATION,previousPlayUnit.get("duration"));
        fullScreenIntent.putExtra(AppConstants.CLICK_BATCH_ID,courseBatchId);
        fullScreenIntent.putExtra(AppConstants.UNIT_ID_POSITION,previousPlayUnit.get("unitIdPosition"));
        startActivityForResult(fullScreenIntent, GET_UNIT_REQUEST_CODE);
    }


    private void getCurriculum(int courseBatchId, String authToken){

        GetCurriculum getCurriculum = new GetCurriculum((list, unitId) -> {
            List<UnitTopic> curriculumList = list;
            onLaunchFullScreen(curriculumList,courseBatchId,unitId);
        });


        getCurriculum.execute(createHttpObject(courseBatchId,authToken));
    }

    private HttpParamObject createHttpObject(int courseBatchId, String authToken){
        HttpParamObject httpParamObject = new HttpParamObject();
        httpParamObject.setUrl(getResources().getString(R.string.BASE_URL)+"user/batches/"+courseBatchId+"/units");
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization","Bearer "+authToken);
        httpParamObject.setHeaders(headers);
        return httpParamObject;

    }

    private HashMap<String,Double> getPreviousPlayUnit(List<UnitTopic> curriculumList,int courseBatchId,int unitId){

        HashMap<String,Double> dataHashMap = new HashMap<>();
        String data = sharedPreferences.getString(String.valueOf(courseBatchId), null);
        double previousPlayDuration = 0;
        double defaultUnitId ;
        double defaultUnitPosition = 0;
        double defaultPosition = 0;

        if(data!=null && !data.isEmpty()){
            HashMap<String,String> savedUnitMetaDataMap = new Gson().fromJson(data, HashMap.class);
            previousPlayDuration = Double.parseDouble(savedUnitMetaDataMap.get("duration"));
            defaultUnitId = Integer.parseInt(savedUnitMetaDataMap.get("unitId"));
        }

        else{
            defaultUnitId = unitId;

        }
        List<? extends ExpandableGroup> groups = curriculumList;

        boolean findDefaultUnit = false;

        if(groups!=null && groups.size()>0){

            for(int i=0;i<groups.size();i++){
                UnitTopic expandableGroup = (UnitTopic) groups.get(i);

                List<UnitName> items = expandableGroup.getItems();

                for(int j=0;j<items.size();j++){
                    UnitName unitName = items.get(j);

                    if(unitName.getUnitId()==defaultUnitId){

                        expandableGroup.setDrawableId(R.drawable.ic_up_arrow);
                        unitName.setColorResId(Color.RED);
                        defaultPosition = i;
                        defaultUnitPosition = j;
                        findDefaultUnit = true;
                        break;
                    }

                }

                if(findDefaultUnit){
                    break;
                }


            }
        }

        dataHashMap.put("duration", previousPlayDuration);
        dataHashMap.put("position", defaultPosition);
        dataHashMap.put("unitId", defaultUnitId);
        dataHashMap.put("unitIdPosition",defaultUnitPosition);


        return dataHashMap;


    }




}

