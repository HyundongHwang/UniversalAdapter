package com.hhd2002.universaladaptertest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.hhd2002.universaladapter.UniversalViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyLoadMoreVh extends UniversalViewHolder {

    @BindView(R.id.pb)
    ProgressBar _Pb;

    public MyLoadMoreVh(View itemView) {
        super(itemView);
    }

    @Override
    public View inflateConvertView(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View convertView = inflater.inflate(R.layout.item_my_loadmore, parent, false);
        return convertView;
    }

    @Override
    public void findAllViews(Object parentCallback) {
        ButterKnife.bind(this, this.itemView);
    }

    @Override
    public void onBindViewHolder() {

    }

    public void showProgress() {
        _Pb.setVisibility(View.VISIBLE);
    }

    public void hideProgress() {
        _Pb.setVisibility(View.INVISIBLE);
    }
}
