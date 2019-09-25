package com.neostencilmobileapp.adapter;

import android.content.Context;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.annotations.Expose;
import com.neostencilmobileapp.R;
import com.thoughtbot.expandablerecyclerview.listeners.OnGroupClickListener;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import static android.view.animation.Animation.RELATIVE_TO_SELF;

public class UnitTopicHolder extends GroupViewHolder {
    private TextView topicName;
    private ImageView arrow;
    private OnGroupClickListener onGroupClickListener;

    public UnitTopicHolder(View itemView,OnGroupClickListener onGroupClickListener) {
        super(itemView);
        topicName = itemView.findViewById(R.id.list_item_topic_name);
        arrow = itemView.findViewById(R.id.list_item_arrow);
        this.onGroupClickListener = onGroupClickListener;
    }

    public void setGenreTitle(ExpandableGroup group, Context context, boolean b) {

        topicName.setText(group.getTitle());

        if(b){
           animateExpand();
        }


    }

    @Override
    public void expand() {
        animateExpand();
    }

    @Override
    public void collapse() {
        animateCollapse();
    }

    private void animateExpand() {

        RotateAnimation rotate =
                new RotateAnimation(360, 180, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(300);
        rotate.setFillAfter(true);
        arrow.startAnimation(rotate);

    }

    private void animateCollapse() {
        RotateAnimation rotate =
                new RotateAnimation(180, 360, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(300);
        rotate.setFillAfter(true);
        arrow.startAnimation(rotate);
    }


    public TextView getTopicName() {
        return topicName;
    }
}
