package com.neostencilmobileapp.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.google.android.exoplayer2.audio.AudioCapabilities;
import com.google.android.exoplayer2.audio.AudioCapabilitiesReceiver;
import com.neostencilmobileapp.common.AppConstants;
import com.neostencilmobileapp.R;
import com.neostencilmobileapp.fragment.CurriculumListFragment;
import com.neostencilmobileapp.fragment.UnitShowFragment;


public class CurriculumActivity extends BaseActivity {

    private Bundle newBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.curriculum_activity);

        CurriculumListFragment curriculumListFragment = new CurriculumListFragment();
        UnitShowFragment unitShowFragment = new UnitShowFragment();

        unitShowFragment.setArguments(newBundle);
        curriculumListFragment.setArguments(newBundle);

        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.curriculum_show_fragment_container,curriculumListFragment);
        fragmentTransaction.add(R.id.unit_show_fragment_container,unitShowFragment);

        fragmentTransaction.commit();


    }

    @Override
    protected void loadBundle(Bundle bundle) {
        super.loadBundle(bundle);

        newBundle = new Bundle();

        newBundle.putDouble(AppConstants.DEFAULT_UNIT_ID,bundle.getDouble(AppConstants.DEFAULT_UNIT_ID, 0));
        newBundle.putDouble(AppConstants.DEFAULT_UNIT_TOPIC_POSITION,bundle.getDouble(AppConstants.DEFAULT_UNIT_TOPIC_POSITION, 0));
        newBundle.putDouble(AppConstants.UNIT_ID_POSITION,bundle.getDouble(AppConstants.UNIT_ID_POSITION, 0));
        newBundle.putInt(AppConstants.CLICK_BATCH_ID, (int) bundle.getDouble(AppConstants.CLICK_BATCH_ID, 0.0));
        newBundle.putSerializable(AppConstants.DEFAULT_UNIT_BUNDLE,bundle.getSerializable(AppConstants.UNIT_LIST));
        newBundle.putDouble(AppConstants.DEFAULT_UNIT_ID_DURATION,bundle.getDouble(AppConstants.DEFAULT_UNIT_ID_DURATION));

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


    @Override
    public void onBackPressed() {
        returnToFlutterView();
        super.onBackPressed();


    }


    private void returnToFlutterView() {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

}
