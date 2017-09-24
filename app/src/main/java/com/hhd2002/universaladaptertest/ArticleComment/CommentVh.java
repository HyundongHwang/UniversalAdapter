package com.hhd2002.universaladaptertest.ArticleComment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hhd2002.universaladapter.UniversalViewHolder;
import com.hhd2002.universaladaptertest.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentVh extends UniversalViewHolder {
    public String writer;
    public String text;

    @BindView(R.id.tv_writer)
    TextView _TvWriter;
    @BindView(R.id.tv_text)
    TextView _TvText;

    public CommentVh(View itemView) {
        super(itemView);
    }

    @Override
    public View inflateConvertView(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View convertView = inflater.inflate(R.layout.item_comment, parent, false);
        return convertView;
    }

    @Override
    public void findAllViews(Object parentCallback) {
        ButterKnife.bind(this, this.itemView);
    }

    @Override
    public void onBindViewHolder() {
        CommentItem thisItem = (CommentItem) this.item;
        _TvWriter.setText(thisItem.writer);
        _TvText.setText(thisItem.text);
    }
}
