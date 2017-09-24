package com.hhd2002.universaladapter;

import android.view.View;

public interface IUniversalListener {

    void onClickItem(Object item,
                     int position,
                     View convertView);

    void onLastItem();
}
