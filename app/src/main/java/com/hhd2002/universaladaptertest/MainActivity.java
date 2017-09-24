package com.hhd2002.universaladaptertest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.hhd2002.universaladaptertest.ArticleComment.ArticleCommentActivity;
import com.hhd2002.universaladaptertest.LocalImg.LocalImgActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_test0)
    public void on_BtnTest0Clicked() {
        Intent intent = new Intent(this, ArticleCommentActivity.class);
        this.startActivity(intent);
    }

    @OnClick(R.id.btn_test1)
    public void on_BtnTest1Clicked() {
        Intent intent = new Intent(this, LocalImgActivity.class);
        this.startActivity(intent);
    }
}


