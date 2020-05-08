package com.example.listview_using_asynctask;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends BaseAdapter {
    Context context;
    List<Details_pojo>details_pojoList = new ArrayList<>();

    public ListAdapter(Context context, List<Details_pojo> details_pojoList) {
        this.context = context;
        this.details_pojoList = details_pojoList;
    }
    //The getCount() function returns the total number of items to be displayed in a list.

    @Override
    public int getCount() {
        return details_pojoList.size(); //returns the total count to adapter
    }


    //This function is used to Get the data item associated with the specified position in the data set to obtain the corresponding data of the specific location in the collection of data items.
    @Override
    public Object getItem(int position) {
        return details_pojoList.get(position);
    }


    //As for the getItemId (int position), it returns the corresponding to the position item ID. The function returns a long value of item position to the adapter.
    @Override
    public long getItemId(int i) {
        return i;
    }


    //This function is automatically called when the list item view is ready to be displayed or about to be displayed.
    //In this function we set the layout for list items using LayoutInflater class and then add the data to the views like ImageView, TextView etc.
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view ==  null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);     //set layout for displaying items
        }

        TextView name, email;
        name =  view.findViewById(R.id.name);         //get id for text view
        email = view.findViewById(R.id.email);       //get id for text view
        name.setText(details_pojoList.get(position).getName());
        email.setText(details_pojoList.get(position).getEmail());

        return view;
    }
}
