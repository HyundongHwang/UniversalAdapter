package com.hhd2002.universaladapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public abstract class UniversalViewHolder extends RecyclerView.ViewHolder {

    public ArrayList<Object> items;
    public Object item;
    public int position;

    public UniversalViewHolder(View itemView) {
        super(itemView);
    }

    public abstract View inflateConvertView(
            ViewGroup parent);

    public abstract void findAllViews(Object parentCallback);

    public abstract void onBindViewHolder();
}
