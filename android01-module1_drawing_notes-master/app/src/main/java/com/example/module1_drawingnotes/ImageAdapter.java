package com.example.module1_drawingnotes;


import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageAdapter extends BaseAdapter {
    @Override
    public int getCount() {
        return ImageUtils.getListImage().size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView=View.inflate(parent.getContext(),R.layout.item_grid_image,null);
        ImageView ivImage= convertView.findViewById(R.id.iv_image);
        TextView tvImage= convertView.findViewById(R.id.tv_image);
        ivImage.setImageBitmap(BitmapFactory.decodeFile(ImageUtils.getListImage().get(position).getAbsolutePath()));
        ivImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
        tvImage.setText(ImageUtils.getListImage().get(position).getName());
        return convertView;
    }
}
