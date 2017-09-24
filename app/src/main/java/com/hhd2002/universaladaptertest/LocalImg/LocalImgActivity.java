package com.hhd2002.universaladaptertest.LocalImg;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.hhd2002.universaladapter.IUniversalListener;
import com.hhd2002.universaladapter.UniversalAdapter;
import com.hhd2002.universaladapter.UniversalViewHolder;
import com.hhd2002.universaladaptertest.MyLoadMoreVh;
import com.hhd2002.universaladaptertest.MyUtils;
import com.hhd2002.universaladaptertest.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hhd on 2017-09-22.
 */

public class LocalImgActivity extends AppCompatActivity {

    @BindView(R.id.rv_obj)
    RecyclerView _RvObj;
    @BindView(R.id.sr_obj)
    SwipeRefreshLayout _SrObj;

    private boolean _isLoading = false;
    private UniversalAdapter _adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_local_img);
        ButterKnife.bind(this);

        _SrObj.setColorSchemeColors(0xff5b79c2);

        _SrObj.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onRefresh() {

                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... voids) {
                        _loadMore10LocalImages();
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        _SrObj.setRefreshing(false);
                    }
                }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        });

        StaggeredGridLayoutManager lm = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        _RvObj.setLayoutManager(lm);

        ArrayList<Class> itemTypeList = new ArrayList<>();
        itemTypeList.add(LocalImgItem.class);

        ArrayList<Class<? extends UniversalViewHolder>> vhTypeList = new ArrayList<>();
        vhTypeList.add(LocalImgVh.class);

        _adapter = new UniversalAdapter(
                _RvObj,
                itemTypeList,
                vhTypeList
        );

        _adapter.setListener(new IUniversalListener() {
            @Override
            public void onClickItem(Object item, int position, View convertView) {
                LocalImgItem gtImage = (LocalImgItem) item;
                String json = MyUtils.createGson().toJson(gtImage);
                Toast.makeText(getBaseContext(), json, Toast.LENGTH_SHORT).show();
            }

            @SuppressLint("StaticFieldLeak")
            @Override
            public void onLastItem() {
                if (_isLoading)
                    return;

                if (!_canLoadMore())
                    return;

                _isLoading = true;
                MyLoadMoreVh loadMoreVh = (MyLoadMoreVh) _adapter.getLoadMoreVh();
                loadMoreVh.showProgress();

                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... voids) {
                        _loadMore10LocalImages();
                        _isLoading = false;
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        loadMoreVh.hideProgress();
                        _adapter.notifyDataSetChanged();
                    }
                }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        });

        _adapter.setLoadMoreVhType(MyLoadMoreVh.class);
        _RvObj.setAdapter(_adapter);
        _loadMore10LocalImages();
    }

    private boolean _canLoadMore() {
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        int cursorRowCount = cur.getCount();
        boolean canLoadMore = (_adapter.getItems().size() < cursorRowCount);
        return canLoadMore;
    }

    private void _loadMore10LocalImages() {
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        int cursorRowCount = cur.getCount();

        if (cursorRowCount == 0)
            return;

        int start = Math.max(cursorRowCount - 1 - _adapter.getItems().size(), 0);
        int end = Math.max(cursorRowCount - 1 - _adapter.getItems().size() - 10, 0);

        for (int row = start; row >= end; row--) {

            Cursor curThumb = null;
            LocalImgItem newImage = new LocalImgItem();

            try {
                cur.moveToPosition(row);
                int colCount = cur.getColumnCount();

                for (int col = 0; col < colCount; col++) {
                    String columnName = cur.getColumnName(col);
                    String value = cur.getString(col);
                    MyUtils.writeLog(String.format("[MEDIASTORE.IMAGES.MEDIA LOOP] %s:%s", columnName, value));
                }

                String id = cur.getString(cur.getColumnIndex(MediaStore.MediaColumns._ID));
                newImage.filePath = cur.getString(cur.getColumnIndex(MediaStore.MediaColumns.DATA));
                String widthStr = cur.getString(cur.getColumnIndex(MediaStore.MediaColumns.WIDTH));
                newImage.width = Integer.parseInt(widthStr);
                String heightStr = cur.getString(cur.getColumnIndex(MediaStore.MediaColumns.HEIGHT));
                newImage.height = Integer.parseInt(heightStr);

                curThumb = cr.query(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI,
                        new String[]{MediaStore.Images.Thumbnails.DATA},
                        MediaStore.Images.Thumbnails.IMAGE_ID + "=?",
                        new String[]{id},
                        null);

                curThumb.moveToFirst();

                try {
                    newImage.thumbFilePath = curThumb.getString(curThumb.getColumnIndex(MediaStore.Images.Thumbnails.DATA));
                } catch (Exception e2) {
                    newImage.thumbFilePath = newImage.filePath;
                }

                _adapter.getItems().add(newImage);
                MyUtils.writeLog("newImage", newImage);

            } catch (Exception e) {
                MyUtils.writeLog(e.toString());
            } finally {
                if (curThumb != null) {
                    curThumb.close();
                }
            }
        }

        cur.close();

        try {
            Thread.sleep(3000);
        } catch (Exception e) {
            MyUtils.writeLog(e.toString());
        }
    }
}


