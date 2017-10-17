package com.example.tomdong.sanity;

/**
 * Created by tomdong on 10/15/17.
 */

import android.content.Context;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;




import android.widget.ArrayAdapter;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;


import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by User on 4/4/2017.
 */

public class CustomBudgetCardAdapter extends ArrayAdapter<Budget_card> {

    private static final String TAG = "CustomListAdapter";

    private Context mContext;
    private int mResource;
    private int lastPosition = -1;
    ArrayList<Budget_card> mList;
    SparseBooleanArray mSelectedItemsIds;

    /**
     * Holds variables in a View
     */
    private static class ViewHolder {
        TextView BudgetType;
        ProgressBar PBar;
        TextView CateGoryAmount;
        Button deleteButton;
    }

    public CustomBudgetCardAdapter(Context context, int resource, ArrayList<Budget_card> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
        mList = objects;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //get the persons information
        final int tempPos = position;
        String BudgetCat = getItem(position).GetBudgetType();
        double amount=getItem(position).GetLimit();
        double current=getItem(position).GetCurrent();

        try {
            final View result;
            final ViewHolder holder;
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(mContext);
                convertView = inflater.inflate(mResource, parent, false);
                holder = new ViewHolder();
                holder.BudgetType = (TextView) convertView.findViewById(R.id.Budget_type);
                holder.PBar = (ProgressBar) convertView.findViewById(R.id.budget_progress_bar2);
                holder.CateGoryAmount=(TextView) convertView.findViewById(R.id.budget_amount);

                holder.BudgetType.setText(mList.get(position).GetBudgetType());
                holder.CateGoryAmount.setText("$" + Double.toString(mList.get(position).GetLimit()));
                double limitAmount = mList.get(position).GetLimit();
                double currAmount = mList.get(position).GetCurrent();
                double P = ((currAmount/limitAmount) * 100);
                holder.PBar.setProgress((int)P);
                lastPosition = position;
                result = convertView;
                convertView.setTag(holder);
            }
            else{
                holder = (ViewHolder) convertView.getTag();
                result = convertView;
            }
            lastPosition = position;

            holder.BudgetType.setText(BudgetCat);
            holder.CateGoryAmount.setText(""+amount+"$");
            double P = ((current/amount) * 100);
            holder.PBar.setProgress((int)P);

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

    
    public void toggleSelection(int position)
    {
        selectView(position,!mSelectedItemsIds.get(position));
    }

    public void removeSelection() {
        mSelectedItemsIds = new SparseBooleanArray();
        notifyDataSetChanged();
    }

    public void selectView(int position, boolean value) {
        if (value) {
            mSelectedItemsIds.put(position, value);

        } else {
            mSelectedItemsIds.delete(position);
        }
        notifyDataSetChanged();
    }

    public int getSelectedCount() {
        return mSelectedItemsIds.size();
    }

    public SparseBooleanArray getSeectedIds() {
        return mSelectedItemsIds;
    }

    public void Add(Budget_card bgt) { add(bgt); }

}