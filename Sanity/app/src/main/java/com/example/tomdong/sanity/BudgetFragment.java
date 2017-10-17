package com.example.tomdong.sanity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;


import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import com.example.tomdong.sanity.dummy.DummyContent.DummyItem;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Calendar;


import Model.Budget;
import Model.BudgetModel;

import static android.content.ContentValues.TAG;


/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class BudgetFragment extends Fragment implements Button.OnClickListener {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private SwipeMenuListView mListView;
    private ImageView mImageView;

    Button addBgtDateButton;
    TextView addBgtDateText;
    private int addBgtYear, addBgtMonth, addBgtDay;
    CustomBudgetCardAdapter adapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public BudgetFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static BudgetFragment newInstance(int columnCount) {
        BudgetFragment fragment = new BudgetFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

        final Calendar c = Calendar.getInstance();
        addBgtDay = c.get(Calendar.DAY_OF_MONTH);
        addBgtMonth = c.get(Calendar.MONTH);
        addBgtYear = c.get(Calendar.YEAR);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_budget_list, container, false);

        mListView=view.findViewById(R.id.my_budgets_listview);
        mImageView=view.findViewById(R.id.my_budgets_icon);
        FloatingActionButton addBgtFab = (FloatingActionButton) view.findViewById(R.id.add_budget_fab);
        addBgtFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddBgtDialog();
            }
        });


        ArrayList<Budget_card> list = new ArrayList<>();
        DateFormat f = new SimpleDateFormat("yyyy-MM-dd");


        Map<Long, Budget> budgetMap = BudgetModel.GetInstance().GetBudgetMap();
        for (Budget budget : budgetMap.values())
            list.add(new Budget_card(budget.getmName(), f.format(new Date(budget.getmDueTime()*1000)),budget.getmPeriod(), budget.GetAmountLimit(), budget.GetCurrAmount()));
/*
        list.add(new Budget_card("Parking"));
        list.add(new Budget_card("Eating"));
        list.add(new Budget_card("Studying"));
        list.add(new Budget_card("Working"));
        list.add(new Budget_card("Skiing"));
        list.add(new Budget_card("Gaming"));
        list.add(new Budget_card("Travelling"));
        list.add(new Budget_card("pooping"));*/
        final CustomBudgetCardAdapter adapter = new CustomBudgetCardAdapter(getContext(), R.layout.fragment_budget, list);
/*
        list.add(new Budget_card("Parking", "Parking", 2, 1));
        list.add(new Budget_card("Parking", "Eating", 20, 15));
        list.add(new Budget_card("Parking", "Studying", 5, 2));
        list.add(new Budget_card("Parking", "Working", 10, 2));
        list.add(new Budget_card("Parking", "Skiing", 14, 6));
        list.add(new Budget_card("Parking", "Gaming", 109,78));
        list.add(new Budget_card("Parking", "Travelling", 77, 60));
        list.add(new Budget_card("shit", "pooping", 30, 17));
        final CustomBudgetCardAdapter adapter = new CustomBudgetCardAdapter(getContext(),R.layout.fragment_budget, list);
*/

        mListView.setAdapter(adapter);
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(170);
                menu.addMenuItem(deleteItem);

            }
        };

        mListView.setMenuCreator(creator);
        mListView.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);

        mListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        Log.e(TAG, "Delete Position: " + position);
                        adapter.Remove(position);
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                startActivity(new Intent(getContext(), BudgetViewActivity.class));

            }
        });
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                addBgtDateText.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
            }
        }, addBgtYear, addBgtMonth, addBgtDay);
        datePickerDialog.show();
    }


    protected void showAddBgtDialog() {

        // get input_dialog.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View promptView = layoutInflater.inflate(R.layout.budget_add_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setView(promptView);

        addBgtDateText = (TextView) promptView.findViewById(R.id.add_bgt_date);
        addBgtDateText.setText(addBgtYear + "-" + (addBgtMonth + 1) + "-" + addBgtDay);
        addBgtDateButton = (Button) promptView.findViewById(R.id.add_bgt_date_button);
        addBgtDateButton.setOnClickListener(this);

        final EditText bgtName = (EditText) promptView.findViewById(R.id.add_bgt_name);
        final TextView bgtDate = (TextView) promptView.findViewById(R.id.add_bgt_date);
        final TextView bgtPeriod = (EditText) promptView.findViewById(R.id.add_bgt_period);


        //EditText addBgtText = (EditText) promptView.findViewById(R.id.add_bgt_name);

        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getContext(),"Add Budget!",Toast.LENGTH_SHORT).show();
                        adapter.Add(new Budget_card(bgtName.getText().toString(),
                                bgtDate.getText().toString(),
                                Integer.valueOf(bgtPeriod.getText().toString()),
                                0,
                                0));
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(DummyItem item);
    }
}
