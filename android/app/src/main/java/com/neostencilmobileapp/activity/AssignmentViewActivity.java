package com.neostencilmobileapp.activity;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.neostencilmobileapp.R;
import com.neostencilmobileapp.common.AppConstants;
import com.neostencilmobileapp.fragment.MediaFragment;
import com.neostencilmobileapp.model.HttpParamObject;
import com.neostencilmobileapp.model.SingleUnitResponse;
import com.neostencilmobileapp.model.UnitModel;
import com.neostencilmobileapp.model.UserAssignmentSubmitRequest;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AssignmentViewActivity extends BaseActivity {

    private UnitModel unitModel;
    private MediaFragment mediaFragment;
    private int unitId;
    private ProgressBar progressBar;
    private File uploadFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_view);

        progressBar = findViewById(R.id.progress_bar_assignment);
        progressBar.getIndeterminateDrawable()
                .setColorFilter(getResources().getColor(R.color.light_gray), PorterDuff.Mode.SRC_IN);

        mediaFragment = new MediaFragment();

        mediaFragment = new MediaFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(mediaFragment, "Assignment").commit();


        TextView tv_assignment_html = findViewById(R.id.tv_assignment_html);
        Button btn_upload_assignment = findViewById(R.id.btn_upload_assignment);


        if (unitModel != null) {
            unitId = unitModel.getUnitId();
            String description = unitModel.getDescription();

            if(!description.contains("https://storage.googleapis.com")){
                String replace = description.replace("<a href=\"", "<a href=\"https://www.neostencil.com");
                tv_assignment_html.setText(Html.fromHtml(replace));
            }
            else{
                tv_assignment_html.setText(Html.fromHtml(description));
            }


            tv_assignment_html.setMovementMethod(LinkMovementMethod.getInstance());


        }

        btn_upload_assignment.setOnClickListener(this);

    }

    @Override
    protected void loadBundle(Bundle bundle) {
        super.loadBundle(bundle);

        unitModel = (UnitModel) bundle.getSerializable(AppConstants.ASSIGNMENT_MODEL);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {

            case R.id.btn_upload_assignment:
                askPermissions();
                break;

        }
    }

    private void uploadAssignment() {

        mediaFragment.getDoc(new MediaFragment.MediaListener() {
            @Override
            public void setUri(Uri uri, String MediaType) {

            }

            @Override
            public void setUri(Uri uri, String MediaType, String path) {

                if (MediaType.equals("doc")) {

                    File doc_path = null;
                    if (path != null) {
                        doc_path = new File(path);
                    } else {
                        doc_path = new File(uri.getPath());
                    }

                    if (doc_path.exists()) {
                        AsyncTask asyncTask = executeTask();
                        asyncTask.execute(doc_path);

                    }
                }


            }


            @Override
            public void setBitmap(Bitmap bitmap, String MediaType) {


            }
        }, this);


    }

    @Override
    public void preTask() {
        super.preTask();

        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public Object backgroundTask(Object... params) {
        super.backgroundTask(params);

        uploadFile = (File) params[0];

        OkHttpClient client;
        client = new OkHttpClient().newBuilder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .build();

        RequestBody formBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", uploadFile.getName(), RequestBody.create(MediaType.parse(URLConnection.guessContentTypeFromName(uploadFile.getName())), uploadFile))
                .addFormDataPart("type", "assignment")
                .addFormDataPart("contentType", URLConnection.guessContentTypeFromName(uploadFile.getName()))
                .build();

        Response response = null;


        try {
            response = client.newCall(new Request.Builder().url(getResources().getString(R.string.BASE_URL)+"user/uploadfile").post(formBody).
                    addHeader("Content-Type", "multipart/form-data")
                    .addHeader("Authorization", "Bearer "+ MainActivity.authToken)
                    .build()).execute();

            if (response.isSuccessful()) {
                if (response.body() != null) {
                    String url = response.body().string();
                    return url;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }


    @Override
    public void postTask(Object response) {
        super.postTask(response);
        if (response != null && uploadFile!=null) {
            submitUserAssignment(String.valueOf(response), uploadFile.getName(), URLConnection.guessContentTypeFromName(uploadFile.getName()));

        }
        else{
            showToast("Failed");
        }
    }


    protected void askPermissions() {
        new TedPermission(this)
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .setPermissionListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        onPermissionVerify();
                    }

                    @Override
                    public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                        Log.e("Denied", "denied");
                    }
                }).check();
    }

    private void onPermissionVerify() {

        uploadAssignment();

    }

    private HttpParamObject createHttpParamObjectForUserAssignment(String url, String name, String type) {
        UserAssignmentSubmitRequest userAssignmentSubmitRequest = new UserAssignmentSubmitRequest(unitId, name, url, type);

        HttpParamObject httpParamObject = new HttpParamObject();
        httpParamObject.setUrl(getResources().getString(R.string.BASE_URL) + "user/assignmentsubmission");
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", "Bearer " + MainActivity.authToken.toString());

        httpParamObject.setJson(new Gson().toJson(userAssignmentSubmitRequest));
        httpParamObject.setHeaders(headers);

        return httpParamObject;

    }

    private void submitUserAssignment(String url, String name, String type) {
        HttpParamObject httpParamObject = createHttpParamObjectForUserAssignment(url, name, type);

        new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                HttpParamObject httpParamObject1 = (HttpParamObject) objects[0];

                OkHttpClient client;
                client = new OkHttpClient().newBuilder()
                        .connectTimeout(120, TimeUnit.SECONDS)
                        .readTimeout(120, TimeUnit.SECONDS)
                        .writeTimeout(120, TimeUnit.SECONDS)
                        .build();

                okhttp3.Request.Builder builder = new okhttp3.Request.Builder().url(httpParamObject1.getUrl());
                MediaType mediaType = MediaType.parse("application/json");

                Response response = null;

                for (Map.Entry<String, String> pair : httpParamObject1.getHeaders().entrySet()) {
                    builder.addHeader(pair.getKey(), pair.getValue());
                }
                try {
                    final RequestBody body = RequestBody.create(mediaType, httpParamObject1.getJson());
                    builder = builder.patch(body);
                    response = client.newCall(builder.build()).execute();

                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            System.out.print("Successful");

                            return true;
                        }
                    } else {
                        return false;
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

                return false;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                progressBar.setVisibility(View.GONE);
                boolean isTrue = (boolean) o;
                if (isTrue) {
                    showToast("Assignment Submit Successful");
                }
                else{
                    showToast("Failed");
                }
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
        }.execute(httpParamObject);
    }


}
