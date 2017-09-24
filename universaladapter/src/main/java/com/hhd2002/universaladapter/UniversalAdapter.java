package com.hhd2002.universaladapter;

import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by hhd on 2017-09-22.
 */

public class UniversalAdapter extends RecyclerView.Adapter {


    private ArrayList<Object> _items = new ArrayList<>();
    private RecyclerView _rcView;
    private ArrayList<Class> _itemTypes;
    private ArrayList<Class<? extends UniversalViewHolder>> _vhTypes;
    private IUniversalListener _listener;
    private Object _additionalCallback;
    private boolean _useLoadMore = false;
    private UniversalViewHolder _loadMoreVh;


    public UniversalAdapter(
            RecyclerView rcView,
            ArrayList<Class> itemTypes,
            ArrayList<Class<? extends UniversalViewHolder>> vhTypes) {

        _rcView = rcView;
        _itemTypes = itemTypes;
        _vhTypes = vhTypes;
        _init();
    }


    private void _init() {
        Handler uiHandler = new Handler();

        _rcView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int itemsSize = _items.size();
                RecyclerView.LayoutManager lm = recyclerView.getLayoutManager();

                if (lm instanceof LinearLayoutManager) {
                    LinearLayoutManager llm = (LinearLayoutManager) lm;
                    int lvp = llm.findLastCompletelyVisibleItemPosition();

                    if (lvp == (itemsSize - 1)) {
                        _fireOnLastItem();
                    }
                } else if (lm instanceof StaggeredGridLayoutManager) {
                    StaggeredGridLayoutManager slm = (StaggeredGridLayoutManager) lm;
                    int spanCount = slm.getSpanCount();
                    int[] into = new int[spanCount];

                    for (int i = 0; i < spanCount; i++) {
                        into[i] = i;
                    }

                    int[] lcvp = slm.findLastCompletelyVisibleItemPositions(into);

                    for (int i : lcvp) {
                        if (i == (itemsSize - 1)) {
                            _fireOnLastItem();
                            break;
                        }
                    }
                }

            }//public void onScrolled

            private void _fireOnLastItem() {
                uiHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (_listener == null)
                            return;

                        _listener.onLastItem();
                    }
                }, 100);
            }

        }); //_rcView.addOnScrollListener
    }


    @Override
    public int getItemCount() {
        int size = _items.size();

        if (_useLoadMore) {
            return size + 1;
        } else {
            return size;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        try {
            Class<? extends UniversalViewHolder> vhType = _vhTypes.get(viewType);

            View convertView =
                    vhType.getDeclaredConstructor(View.class)
                            .newInstance(parent)
                            .inflateConvertView(parent);

            UniversalViewHolder vh =
                    vhType.getDeclaredConstructor(View.class)
                            .newInstance(convertView);

            vh.findAllViews(_additionalCallback);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (_listener == null)
                        return;

                    _listener.onClickItem(
                            vh.item,
                            vh.position,
                            convertView);

                }
            });


            if (_useLoadMore &&
                    (viewType == _itemTypes.size() - 1)) {
                _loadMoreVh = vh;
            }

            return vh;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        UniversalViewHolder vh = (UniversalViewHolder) holder;

        if (position < _items.size()) {
            Object item = _items.get(position);
            vh.item = item;
            vh.position = position;
        }

        vh.onBindViewHolder();
    }

    @Override
    public int getItemViewType(int position) {

        if (_useLoadMore &&
                position == _items.size()) {
            return _itemTypes.size() - 1;
        }


        int viewType = 0;
        Object item = _items.get(position);
        int size = _itemTypes.size();

        for (int i = 0; i < size; i++) {
            Class cls = _itemTypes.get(i);

            if (item.getClass() == cls) {
                viewType = i;
                break;
            }
        }

        return viewType;
    }

    public ArrayList<Object> getItems() {
        return _items;
    }

    public void setListener(IUniversalListener listener) {
        _listener = listener;
    }

    public void setAdditionalCallback(Object additionalCallback) {
        _additionalCallback = additionalCallback;
    }

    public void setLoadMoreVhType(Class<? extends UniversalViewHolder> loadMoreVhType) {
        _useLoadMore = true;
        _itemTypes.add(UniversalLodeMoreItem.class);
        _vhTypes.add(loadMoreVhType);
    }

    public UniversalViewHolder getLoadMoreVh() {
        return _loadMoreVh;
    }
}