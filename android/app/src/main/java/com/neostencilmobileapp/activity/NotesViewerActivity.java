 package com.neostencilmobileapp.activity;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;
import com.neostencilmobileapp.R;
import com.neostencilmobileapp.common.AppConstants;
import com.neostencilmobileapp.model.MobileNote;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

 public class NotesViewerActivity extends BaseActivity {

     private MobileNote mobileNote;
     private PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_viewer);

        pdfView = (PDFView) findViewById(R.id.pdfView);

        AsyncTask asyncTask = executeTask();
        asyncTask.execute((Object) null);
    }

     @Override
     protected void loadBundle(Bundle bundle) {
         super.loadBundle(bundle);

         mobileNote = (MobileNote) bundle.getSerializable(AppConstants.SHOW_NOTE_MODEL);
     }

     @Override
     public void preTask() {
         super.preTask();
     }

     @Override
     public Object backgroundTask(Object... params) {
         super.backgroundTask(params);
         InputStream inputStream = null;
         try {
             inputStream = new URL(getString(R.string.SITE_URL)+mobileNote.getUrl()).openStream();
         } catch (IOException e) {
             e.printStackTrace();
         }

         return inputStream;
     }

     @Override
     public void postTask(Object response) {
         super.postTask(response);

         if(response!=null){
             PDFView.Configurator configurator = pdfView.fromStream((InputStream) response);
             configurator.load();

         }
     }

     @Override
     public void onBackPressed() {
         super.onBackPressed();

         finish();
     }
 }
