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
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by User on 4/4/2017.
 */

public class CustomTransactionCardAdapter extends ArrayAdapter<Transaction_card> {

    private static final String TAG = "CustomListAdapter";

    private Context mContext;
    private int mResource;
    private int lastPosition = -1;
    ArrayList<Transaction_card> mList;
    SparseBooleanArray mSelectedItemsIds;

    /**
     * Holds variables in a View
     */
    private static class ViewHolder {
        private TextView CategoryType;
        private TextView time;
        private TextView Note;
        private TextView amount;
    }

    public CustomTransactionCardAdapter(Context context, int resource, ArrayList<Transaction_card> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
        mList = objects;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //get the persons information
//        String CategoryType = getItem(position).GetCategoryType();
//        String time= getItem(position).GetTime();
//        String Note= getItem(position).GetNote();
//        String amount= getItem(position).GetAmount();


            ViewHolder holder;
            holder= new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder.CategoryType = (TextView) convertView.findViewById(R.id.trans_cat);
            holder.time = (TextView) convertView.findViewById(R.id.trans_date);
            holder.Note=(TextView) convertView.findViewById(R.id.trans_note);
            holder.amount=(TextView)convertView.findViewById(R.id.trans_amount);
            Log.e(TAG, "getView: position: "+position );
            holder.CategoryType.setText(mList.get(position).GetCategoryType());
            holder.time.setText(mList.get(position).GetTime());
            holder.Note.setText(mList.get(position).GetNote());
            holder.amount.setText("Amount: "+mList.get(position).GetAmount());
            lastPosition = position;

             return convertView;

    }

    public void Remove(Budget_card budgetCard) {
        mList.remove(budgetCard);
        notifyDataSetChanged();
    }

    public void toggleSelection(int position) {
        selectView(position, !mSelectedItemsIds.get(position));
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

}