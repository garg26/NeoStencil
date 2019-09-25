package com.neostencilmobileapp.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.neostencilmobileapp.R;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.listeners.GroupExpandCollapseListener;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.models.ExpandableListPosition;

import java.util.List;


public class CurriculumAdapter extends ExpandableRecyclerViewAdapter<UnitTopicHolder, UnitShowHolder> {

    private OnTaskCompleted listener;
    private Integer row_index = -1;
    private Context context;
    private Double togglePosition;

    public CurriculumAdapter(List<? extends ExpandableGroup> groups, Context context, Double togglePosition, OnTaskCompleted listener) {
        super(groups);
        this.listener = listener;
        this.context = context;
        this.togglePosition = togglePosition;

    }


    @Override
    public UnitTopicHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        int i=0;
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_unit_topic_show, parent, false);


        return new UnitTopicHolder(view,this);
    }

    @Override
    public UnitShowHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_unit_list_item_show, parent, false);
        return new UnitShowHolder(view,context);
    }

    @Override
    public void onBindChildViewHolder(UnitShowHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {

        final UnitName unitName = (UnitName) group.getItems().get(childIndex);
        holder.setUnitName(unitName.getName(), unitName.getUnitTopic(), unitName.getColorResId(), unitName.getUnitId());
        holder.itemView.setOnClickListener(view -> {
            row_index = flatPosition;

            notifyDataSetChanged();

            listener.onTaskCompleted(unitName.getUnitId());


        });

        ColorStateList textColors = holder.unitNameView.getTextColors();

        if (row_index == flatPosition) {

            holder.unitNameView.setTextColor(Color.RED);
        } else {
            holder.unitNameView.setTextColor(textColors);
        }


    }

    @Override
    public void onBindGroupViewHolder(UnitTopicHolder holder, int flatPosition, ExpandableGroup group) {

        if(togglePosition!=null) {
            if (group.getTitle().equalsIgnoreCase(this.getGroups().get(togglePosition.intValue()).getTitle())) {
                holder.setGenreTitle(group, context, true);
            } else {
                holder.setGenreTitle(group, context, false);
            }
        }
        else{
            holder.setGenreTitle(group, context, false);
        }


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        


    }

    public interface OnTaskCompleted {
        void onTaskCompleted(int unitId);
    }




    @Override
    public boolean onGroupClick(int flatPos) {

        togglePosition = null;
        ExpandableListPosition listPos = expandableList.getUnflattenedPosition(flatPos);
        flatPos = listPos.groupPos;
        boolean b = false;

        for (int i=0;i<this.getGroups().size();i++) {
            if (this.isGroupExpanded(this.getGroups().get(i))) {
                this.toggleGroup(this.getGroups().get(i));
            }

        }




        return super.onGroupClick(flatPos);

    }

}
