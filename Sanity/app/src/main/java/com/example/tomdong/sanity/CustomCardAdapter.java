package com.example.tomdong.sanity;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by User on 4/4/2017.
 */

public class CustomCardAdapter extends ArrayAdapter<Category_card> {

    private static final String TAG = "CustomListAdapter";

    private Context mContext;
    private int mResource;
    private int lastPosition = -1;

    /**
     * Holds variables in a View
     */
    private static class ViewHolder {
        TextView CategoryType;
        ProgressBar PBar;
    }

    public CustomCardAdapter(Context context, int resource, ArrayList<Category_card> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //get the persons information
        String CatoryType = getItem(position).GetCatoryType();
        try {
            final View result;
            final ViewHolder holder;
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(mContext);
                convertView = inflater.inflate(mResource, parent, false);
                holder = new ViewHolder();
                holder.CategoryType = (TextView) convertView.findViewById(R.id.CategoryType);
                holder.PBar = (ProgressBar) convertView.findViewById(R.id.bar_in_card);
                result = convertView;
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
                result = convertView;
            }

            lastPosition = position;

            holder.CategoryType.setText(CatoryType);

            return convertView;
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "getView: IllegalArgumentException: " + e.getMessage());
            return convertView;
        }

    }
    public void Remove(int Position)
    {
        Log.e(TAG, "Delete Position: "+getItem(Position).GetBudgetType());
        remove(getItem(Position));
    }

}