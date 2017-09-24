package com.hhd2002.universaladaptertest.ArticleComment;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hhd2002.universaladapter.IUniversalListener;
import com.hhd2002.universaladapter.UniversalAdapter;
import com.hhd2002.universaladapter.UniversalViewHolder;
import com.hhd2002.universaladaptertest.LocalImg.LocalImgItem;
import com.hhd2002.universaladaptertest.LocalImg.LocalImgVh;
import com.hhd2002.universaladaptertest.MyLoadMoreVh;
import com.hhd2002.universaladaptertest.MyUtils;
import com.hhd2002.universaladaptertest.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticleCommentActivity extends AppCompatActivity {

    @BindView(R.id.rv_obj)
    RecyclerView _RvObj;

    private UniversalAdapter _adapter;
    private ArticleCommentDAO _dao = new ArticleCommentDAO();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_article_comment);
        ButterKnife.bind(this);

        LinearLayoutManager lm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        _RvObj.setLayoutManager(lm);

        ArrayList<Class> itemTypeList = new ArrayList<>();
        itemTypeList.add(ArticleItem.class);
        itemTypeList.add(CommentItem.class);
        itemTypeList.add(CommentOfCommentItem.class);

        ArrayList<Class<? extends UniversalViewHolder>> vhTypeList = new ArrayList<>();
        vhTypeList.add(ArticleVh.class);
        vhTypeList.add(CommentVh.class);
        vhTypeList.add(CommentOfCommentVh.class);

        _adapter = new UniversalAdapter(
                _RvObj,
                itemTypeList,
                vhTypeList
        );

        _adapter.setListener(new IUniversalListener() {
            @Override
            public void onClickItem(Object item, int position, View convertView) {
                String json = MyUtils.createGson().toJson(item);
                Toast.makeText(getBaseContext(), json, Toast.LENGTH_SHORT).show();
            }

            @SuppressLint("StaticFieldLeak")
            @Override
            public void onLastItem() {
            }
        });

        _adapter.setAdditionalCallback(new ICallback() {
            @Override
            public void onClickShowComment() {
                _loadComments();
                _adapter.notifyDataSetChanged();
            }
        });

        _adapter.getItems().add(_dao.get(0));
        _RvObj.setAdapter(_adapter);
    }

    private void _loadComments() {
        if (_adapter.getItems().size() == _dao.size())
            return;


        for (int i = 1; i < _dao.size(); i++) {
            Object item = _dao.get(i);
            _adapter.getItems().add(item);
        }
    }

    public interface ICallback {
        void onClickShowComment();
    }
}

