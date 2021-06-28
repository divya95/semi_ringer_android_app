package com.example.sringer;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class WhiteListAdapter extends CursorAdapter 
{
	private Cursor cursor;

    public WhiteListAdapter(Context context, Cursor cur) {
		super(context, cur);
		cursor = cur;
		// TODO Auto-generated constructor stub
	}

        
	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		// TODO Auto-generated method stub
		// ColID
		if(cursor.getCount()!=0)
		{
		TextView txtID = (TextView) view.findViewById(R.id.aname);
		txtID.setText(cursor.getString(2));
		
		// ColCode
		TextView txtCode = (TextView) view.findViewById(R.id.aloc);
		txtCode.setText(cursor.getString(4));
		}
	}
	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.white_list, parent, false);
		bindView(view, context, cursor);
		return view;
	}
	

    public int getCount() {
    	// TODO Auto-generated method stub
    	return cursor.getCount();
    }


    public Object getItem(int position) {
    	// TODO Auto-generated method stub
        return position;
    }

    public long getItemId(int position) {
    	// TODO Auto-generated method stub
        return position;
    }



}
