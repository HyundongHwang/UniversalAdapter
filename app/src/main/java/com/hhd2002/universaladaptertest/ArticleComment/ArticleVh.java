package com.hhd2002.universaladaptertest.ArticleComment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hhd2002.universaladapter.UniversalViewHolder;
import com.hhd2002.universaladaptertest.LocalImg.LocalImgItem;
import com.hhd2002.universaladaptertest.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ArticleVh extends UniversalViewHolder {
    @BindView(R.id.tv_text)
    TextView _TvText;
    private ArticleCommentActivity.ICallback _parentCallback;

    public ArticleVh(View itemView) {
        super(itemView);
    }

    @Override
    public View inflateConvertView(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View convertView = inflater.inflate(R.layout.item_article, parent, false);
        return convertView;
    }

    @Override
    public void findAllViews(Object parentCallback) {
        ButterKnife.bind(this, this.itemView);
        _parentCallback = (ArticleCommentActivity.ICallback) parentCallback;
    }

    @Override
    public void onBindViewHolder() {
        ArticleItem thisItem = (ArticleItem) this.item;
        _TvText.setText(thisItem.text);
    }

    @OnClick(R.id.btn_show_comments)
    public void onViewClicked() {
        _parentCallback.onClickShowComment();
    }
}
