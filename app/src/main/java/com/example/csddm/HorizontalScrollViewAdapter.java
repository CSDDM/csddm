package com.example.csddm;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by dell on 2017/3/9.
 */

public class HorizontalScrollViewAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<Bitmap> mDatas;
    private List<String> info;

    public HorizontalScrollViewAdapter(Context context, List<Bitmap> mDatas,List<String> infos) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        this.mDatas = mDatas;
        this.info=infos;
    }

    public int getCount() {
        return mDatas.size();
    }

    public Object getItem(int position) {
        return mDatas.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position,View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(
                    R.layout.activity_index_gallery_item, parent, false);
            viewHolder.mImg = (ImageView) convertView
                    .findViewById(R.id.id_index_gallery_item_image);
            viewHolder.mText = (TextView) convertView
                    .findViewById(R.id.id_index_gallery_item_text);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //viewHolder.mImg.setImageResource(mDatas.get(position));
        viewHolder.mImg.setImageBitmap(mDatas.get(position));
        viewHolder.mText.setText(changeToData(info.get(position)));

        return convertView;
    }

    public String changeToData(String str) {
        String data=str.substring(0,10)+"\n"+"   "+str.substring(10,12)+":"+str.substring(12,14)+":"+str.substring(14,16);
        return data;
    }

    private class ViewHolder {
        ImageView mImg;
        TextView mText;
    }
}
