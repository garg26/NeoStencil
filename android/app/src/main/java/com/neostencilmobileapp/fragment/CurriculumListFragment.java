package com.neostencilmobileapp.fragment;


import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.neostencilmobileapp.R;
import com.neostencilmobileapp.activity.MainActivity;
import com.neostencilmobileapp.adapter.CurriculumAdapter;
import com.neostencilmobileapp.adapter.UnitName;
import com.neostencilmobileapp.adapter.UnitTopic;
import com.neostencilmobileapp.common.AppConstants;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

import io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin;

public class CurriculumListFragment extends BaseFragment {

    private List<UnitTopic> curriculumList;
    public CurriculumAdapter adapter;
    private double togglePosition;
    private double defaultUnitId;
    private double unitIdPosition;

    @Override
    public void initViews() {

        RecyclerView recyclerView = (RecyclerView) findView(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        RecyclerView.ItemAnimator animator = recyclerView.getItemAnimator();

        if (animator instanceof DefaultItemAnimator) {
            ((DefaultItemAnimator) animator).setSupportsChangeAnimations(true);
        }


        adapter = new CurriculumAdapter(curriculumList, getContext(), Double.valueOf(togglePosition), unitId -> {

            /**
             *  Remove the previous state of default unit id when user click on unit
             */

            if (defaultUnitId != 0) {
                defaultUnitId = 0;

                List<? extends ExpandableGroup> groups = adapter.getGroups();
                ExpandableGroup expandableGroup = groups.get((int) togglePosition);
                List<UnitName> items = expandableGroup.getItems();
                UnitName unitName = items.get((int) unitIdPosition);

                unitName.setColorResId(Color.GRAY);

                adapter.notifyItemChanged((int) unitIdPosition, unitName);
            }


            SharedPreferences sharedPreferences = MainActivity.sharedPreferences;
            sharedPreferences.edit().putString("unitIdClick", String.valueOf(unitId));


            UnitShowFragment unitShowFragment = new UnitShowFragment();

            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

            fragmentTransaction.replace(R.id.unit_show_fragment_container, unitShowFragment);

            fragmentTransaction.commit();


        });

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.scrollToPosition((int) unitIdPosition);
        adapter.toggleGroup((int) togglePosition);
        recyclerView.setAdapter(adapter);


    }

    @Override
    public int getViewID() {
        return R.layout.fragment_curriculum_show;
    }

    @Override
    protected void loadBundle(Bundle bundle) {
        super.loadBundle(bundle);

        curriculumList = (List<UnitTopic>) bundle.getSerializable(AppConstants.UNIT_LIST);
        togglePosition = bundle.getDouble(AppConstants.DEFAULT_UNIT_TOPIC_POSITION);
        defaultUnitId = bundle.getDouble(AppConstants.DEFAULT_UNIT_ID);
        unitIdPosition = bundle.getDouble(AppConstants.UNIT_ID_POSITION, 0);


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        adapter.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter.onRestoreInstanceState(savedInstanceState);
    }

}
