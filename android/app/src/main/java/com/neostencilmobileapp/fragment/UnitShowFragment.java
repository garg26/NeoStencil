package com.neostencilmobileapp.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.exoplayer2.drm.ExoMediaDrm;
import com.google.android.exoplayer2.util.Util;
import com.google.gson.Gson;
import com.longtailvideo.jwplayer.JWPlayerView;
import com.longtailvideo.jwplayer.events.TimeEvent;
import com.longtailvideo.jwplayer.events.listeners.VideoPlayerEvents;
import com.longtailvideo.jwplayer.media.drm.MediaDrmCallback;
import com.longtailvideo.jwplayer.media.playlists.PlaylistItem;
import com.neostencilmobileapp.R;
import com.neostencilmobileapp.activity.AssignmentViewActivity;
import com.neostencilmobileapp.activity.MainActivity;
import com.neostencilmobileapp.activity.NotesViewerActivity;
import com.neostencilmobileapp.common.AppConstants;
import com.neostencilmobileapp.common.KeepScreenOnHandler;
import com.neostencilmobileapp.common.UnitType;
import com.neostencilmobileapp.methods.ShowUnit;
import com.neostencilmobileapp.model.HttpParamObject;
import com.neostencilmobileapp.model.MobileNote;
import com.neostencilmobileapp.model.PlayLectureOnMobile;
import com.neostencilmobileapp.model.SingleUnitResponse;
import com.neostencilmobileapp.model.UnitModel;
import com.neostencilmobileapp.model.UnitOnMobile;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;


public class UnitShowFragment extends BaseFragment implements ShowUnit, VideoPlayerEvents.OnTimeListener {

    private double defaultUnitId;
    private double previousPlayDuration;
    private int clickBatchId;
    private JWPlayerView jwPlayerView;
    private HashMap<String,String> unitMetaData;
    private double playDuration;
    private LinearLayout llNotes;
    private TextView tvNoteTitle;
    private Button btnOpenNotes;
    private ProgressBar progressBar;

    @Override
    public void initViews() {

        jwPlayerView = (JWPlayerView) findView(R.id.jwplayer);
        llNotes = (LinearLayout) findView(R.id.ll_notes);
        tvNoteTitle = (TextView) findView(R.id.tv_note_title);
        btnOpenNotes = (Button) findView(R.id.btn_open_notes);



        SharedPreferences sharedPreferences = MainActivity.sharedPreferences;
        String onClickUnitId = sharedPreferences.getString("unitIdClick", null);

        HttpParamObject httpObject = null;
        if(onClickUnitId!=null) {

            if(Integer.parseInt(onClickUnitId)!=0){
                httpObject = createHttpObject(Integer.parseInt(onClickUnitId));
            }
            else{
                httpObject = createHttpObject((int) defaultUnitId);
            }

        }else{
            httpObject = createHttpObject((int) defaultUnitId);
        }
        AsyncTask asyncTask = executeTask();
        asyncTask.execute(httpObject);

    }

    @Override
    protected void loadBundle(Bundle bundle) {
        super.loadBundle(bundle);
        defaultUnitId = bundle.getDouble(AppConstants.DEFAULT_UNIT_ID);
        clickBatchId = bundle.getInt(AppConstants.CLICK_BATCH_ID);
        previousPlayDuration = bundle.getDouble(AppConstants.DEFAULT_UNIT_ID_DURATION);
    }

    @Override
    public int getViewID() {
        return R.layout.fragment_unit_show;
    }

    @Override
    public void preTask() {
        super.preTask();
        showProgressBar();
    }

    @Override
    public void postTask(Object response) {
        super.postTask(response);
        hideProgressBar();
        if(response!=null && getActivity()!=null && isAdded()){
            showUnit((SingleUnitResponse) response);

        }
    }



    @Override
    public Object backgroundTask(Object... params) {
        super.backgroundTask(params);
        HttpParamObject httpParamObject = (HttpParamObject) params[0];

        OkHttpClient client;
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

    private HttpParamObject createHttpObject(int unitId){
        HttpParamObject httpParamObject = new HttpParamObject();
        httpParamObject.setUrl(getResources().getString(R.string.BASE_URL)+"/admin_techops_student/units/"+unitId+"/mobile");

        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization","Bearer "+ MainActivity.authToken.toString());

        HashMap<String, Object> postParams = new HashMap<>();
        postParams.put("unitID",unitId);

        httpParamObject.setPostParams(postParams);

        httpParamObject.setHeaders(headers);
        return httpParamObject;

    }

    private void showUnit(SingleUnitResponse response) {

        UnitOnMobile unitOnMobile = response.getUnitOnMobile();

        if(unitOnMobile!=null) {

            unitMetaData = new HashMap<>();
            unitMetaData.put("unitId",unitOnMobile.getUnit().getUnitId().toString());

            switch (UnitType.valueOf(response.getUnitType())) {

                case LECTURE:
                    if(llNotes!=null && llNotes.isShown()){
                        llNotes.setVisibility(View.GONE);
                    }

                    jwPlayerView.setVisibility(View.VISIBLE);
                    playLecture(response.getUnitOnMobile().getLectureOnMobile(),unitOnMobile.getUnit(), previousPlayDuration);
                    break;
                case ASSIGNMENT:
                    if(jwPlayerView!=null && jwPlayerView.isShown()){
                        jwPlayerView.setVisibility(View.GONE);
                    }
                    llNotes.setVisibility(View.VISIBLE);

                    tvNoteTitle.setText(response.getUnitOnMobile().getUnit().getTitle());
                    btnOpenNotes.setText(getResources().getString(R.string.open_assignment));

                    showAssignment(response.getUnitOnMobile().getUnit());
                    break;
                case NOTES:
                    if(jwPlayerView!=null && jwPlayerView.isShown()){
                        jwPlayerView.setVisibility(View.GONE);
                    }
                    llNotes.setVisibility(View.VISIBLE);

                    tvNoteTitle.setText(response.getUnitOnMobile().getNote().getTitle());
                    btnOpenNotes.setText(getResources().getString(R.string.open_document));

                    showNotes(response.getUnitOnMobile().getNote());
                    break;
                case QUIZ:
                    if(jwPlayerView!=null && jwPlayerView.isShown()){
                        jwPlayerView.setVisibility(View.GONE);
                    }
                    llNotes.setVisibility(View.VISIBLE);

                    tvNoteTitle.setText("Not Available!!");
                    btnOpenNotes.setVisibility(View.GONE);
                    break;
            }
        }

    }

    @Override
    public void playLecture(PlayLectureOnMobile lectureOnMobile,UnitModel unit,double duration) {
        new KeepScreenOnHandler(jwPlayerView, getActivity().getWindow());
        jwPlayerView.setBackgroundAudio(false);

        jwPlayerView.addOnTimeListener(this);


        PlaylistItem pi = new PlaylistItem.Builder()
                .title(unit.getTitle())
                .description(unit.getDescription())
                .file(lectureOnMobile.getManifestFile())
                .mediaDrmCallback(new MediaDrmCallback() {

                    @Override
                    public byte[] executeProvisionRequest(UUID uuid, ExoMediaDrm.ProvisionRequest request) throws Exception {
                        String url = request.getDefaultUrl() + "&signedRequest=" + new String(request.getData());
                        return executePost(url, null, null);
                    }

                    @Override
                    public byte[] executeKeyRequest(UUID uuid, ExoMediaDrm.KeyRequest request) throws Exception {
                        final Map<String, String> KEY_REQUEST_PROPERTIES = new HashMap<>();
                        KEY_REQUEST_PROPERTIES.put("name","customData");
                        KEY_REQUEST_PROPERTIES.put("Authorization",lectureOnMobile.getWidevine().getCustomData());

                        String url = request.getDefaultUrl();
                        if (TextUtils.isEmpty(url)) {
                            url =lectureOnMobile.getWidevine().getUrl();
                        }

                        return executePost(url, request.getData(), KEY_REQUEST_PROPERTIES);
                    }
                })
                .build();

        jwPlayerView.seek(duration);
        jwPlayerView.load(pi);
        jwPlayerView.play();

    }

    @Override
    public void showAssignment(UnitModel assignment) {
        btnOpenNotes.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), AssignmentViewActivity.class);
            intent.putExtra(AppConstants.ASSIGNMENT_MODEL,assignment);
            startActivity(intent);
        });
    }

    @Override
    public void showNotes(MobileNote notes) {
        btnOpenNotes.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), NotesViewerActivity.class);
            intent.putExtra(AppConstants.SHOW_NOTE_MODEL,notes);
            startActivity(intent);
        });
    }

    @Override
    public void onTime(TimeEvent timeEvent) {
        playDuration= timeEvent.getPosition();
    }

    @Override
    public void onDestroy() {
        if(unitMetaData!=null) {
            unitMetaData.put("duration", String.valueOf(playDuration));
            MainActivity.sharedPreferences.edit().putString(String.valueOf(clickBatchId), new Gson().toJson(unitMetaData));
        }
        if(jwPlayerView!=null){
            jwPlayerView.stop();
        }
        super.onDestroy();
    }

    public static byte[] executePost(String url, byte[] data, Map<String, String> requestProperties)
            throws IOException {
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) new URL(url).openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(data != null);
            urlConnection.setDoInput(true);
            if (requestProperties != null) {
                for (Map.Entry<String, String> requestProperty : requestProperties.entrySet()) {
                    urlConnection.setRequestProperty(requestProperty.getKey(), requestProperty.getValue());
                }
            }
            // Write the request body, if there is one.
            if (data != null) {
                OutputStream out = urlConnection.getOutputStream();
                try {
                    out.write(data);
                } finally {
                    out.close();
                }
            }
            // Read and return the response body.
            InputStream inputStream = urlConnection.getInputStream();
            try {
                return Util.toByteArray(inputStream);
            } finally {
                inputStream.close();
            }
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            if(jwPlayerView.getFullscreen()) {
                jwPlayerView.setFullscreen(false, true);
            }
        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            if(!jwPlayerView.getFullscreen()) {
                jwPlayerView.setFullscreen(true, true);
            }
        }
    }

    @Override
    public void onPause() {

        if(jwPlayerView!=null){
            jwPlayerView.pause();
        }

        super.onPause();


    }

    private void showProgressBar(){

        if(progressBar==null){
            progressBar = (ProgressBar) findView(R.id.show_unit_progress_bar);
            progressBar.getIndeterminateDrawable()
                    .setColorFilter(getResources().getColor(R.color.light_gray), PorterDuff.Mode.SRC_IN);
        }

        progressBar.setVisibility(View.VISIBLE);

    }

    private void hideProgressBar(){

        if(progressBar!=null && progressBar.isShown()){
            progressBar.setVisibility(View.GONE);
        }

    }


}
