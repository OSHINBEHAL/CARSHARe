package com.example.raman.carshare.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.raman.carshare.R;

import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by Raman on 5/1/2017.
 */

public class MyAdapterDriver extends BaseAdapter {

    private Context context;
    private List<SingleRowDriver> singleRowList;
    private LayoutInflater inflater;

    public MyAdapterDriver(Context context, List<SingleRowDriver> singleRowList) {
        this.context = context;
        this.singleRowList = singleRowList;
    }

    @Override
    public int getCount() {
        return singleRowList.size();
    }

    @Override
    public Object getItem(int position) {
        return singleRowList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.single_row_driver, parent, false);

        //Getting views of single row
        TextView txtNAme = (TextView) convertView.findViewById(R.id.txtName);
        TextView txtDesc = (TextView) convertView.findViewById(R.id.txtDesc);
        ImageView img = (ImageView) convertView.findViewById(R.id.img);

      SingleRowDriver singleRow = singleRowList.get(position);

        //Setting values in views
        txtNAme.setText(singleRow.name);
        txtDesc.setText(singleRow.desc);
        img.setImageResource(singleRow.image);


        return convertView;
    }
}


