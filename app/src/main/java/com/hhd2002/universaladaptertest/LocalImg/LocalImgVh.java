package com.hhd2002.universaladaptertest.LocalImg;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hhd2002.universaladapter.UniversalViewHolder;
import com.hhd2002.universaladaptertest.MyUtils;
import com.hhd2002.universaladaptertest.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;


/**
 * Created by hhd on 2017-09-22.
 */

public class LocalImgVh extends UniversalViewHolder {

    @BindView(R.id.img_obj)
    ImageView _ImgObj;

    public LocalImgVh(View converView) {
        super(converView);
    }

    @Override
    public View inflateConvertView(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View convertView = inflater.inflate(R.layout.item_local_img, parent, false);
        return convertView;
    }

    @Override
    public void findAllViews(Object parentCallback) {
        ButterKnife.bind(this, this.itemView);
    }

    @Override
    public void onBindViewHolder() {
        LocalImgItem gtImage = (LocalImgItem) this.item;
        int displayWidth = MyUtils.getDisplayWidth(this.itemView.getContext()) / 3;
        int displayHeight = displayWidth * gtImage.height / Math.max(gtImage.width, 1);
        _ImgObj.getLayoutParams().width = displayWidth;
        _ImgObj.getLayoutParams().height = displayHeight;

        Glide.with(this.itemView.getContext())
                .load(gtImage.thumbFilePath)
                .transition(withCrossFade())
                .into(_ImgObj);
    }
}
