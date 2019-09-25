package com.neostencilmobileapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.provider.CalendarContract;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.neostencilmobileapp.R;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

public class UnitShowHolder extends ChildViewHolder{

    public TextView unitNameView;
    private ImageView icon;
    private Context context;


    public UnitShowHolder(View itemView, Context context) {
        super(itemView);
        unitNameView = itemView.findViewById(R.id.list_item_unit_name);
        icon = itemView.findViewById(R.id.list_item_unit_icon);
        this.context = context;
    }


    public void setUnitName(String unitName, String unitTopic,int colorResId, int unitId) {
        unitNameView.setText(unitName);
        unitNameView.setId(unitId);
        unitNameView.setTextColor(colorResId);

        if(unitTopic.equals("LECTURE")){
            icon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_play));
        }
        else if(unitTopic.equals("ASSIGNMENT")){
            icon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_assignment));
        }
        else if(unitTopic.equals("NOTES")){
            icon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_notes));
        }
        else{
            icon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_notes));
        }


    }


}
