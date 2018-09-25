package com.example.newsimooc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class NewsAdapter extends BaseAdapter {

    private List<NewsBean> mList;
    private LayoutInflater mInflater;

    public NewsAdapter(Context context, List<NewsBean> data) {
        this.mList = data;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder = null;
        if (view == null){
            viewHolder = new ViewHolder();
            view = mInflater.inflate(R.layout.item_layout, null);
            viewHolder.ivIcon = view.findViewById(R.id.iv_icon);
            viewHolder.tvTitle = view.findViewById(R.id.tv_title);
            viewHolder.tvContent = view.findViewById(R.id.tv_content);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.ivIcon.setImageResource(R.mipmap.ic_launcher);
        viewHolder.tvTitle.setText(mList.get(i).newsTltle);
        viewHolder.tvContent.setText(mList.get(i).newsContent);
        return view;
    }

    class ViewHolder{
        public TextView tvTitle,tvContent;
        public ImageView ivIcon;
    }
}
