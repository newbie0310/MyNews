package com.example.myapplication.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.entity.LeftItemMenu;
import com.example.myapplication.ui.MainActivity;
import com.example.myapplication.util.MenuDataUtils;

import java.util.List;

/**
 * Created by john1 on 2017/11/1.
 */

public class LeftItemAdapter extends BaseAdapter {
    public LayoutInflater mInflater;
    private List<LeftItemMenu> itemMenus;

    public LeftItemAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        itemMenus = MenuDataUtils.getItemMenus();
    }

    @Override
    public int getCount() {
        return itemMenus != null ? itemMenus.size() : 0;
    }

    @Override
    public Object getItem(int i) {
        return itemMenus.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null){
            holder = new ViewHolder();
            view = mInflater.inflate(R.layout.item_left_menu_layout,null);
            holder.item_left_view_img = view.findViewById(R.id.item_left_view_img);
            holder.item_left_view_title = view.findViewById(R.id.item_left_view_title);
            view.setTag(holder);
        }else {
           holder = (ViewHolder) view.getTag();
        }
        holder.item_left_view_img.setImageResource(itemMenus.get(i).getLeftIcon());
        holder.item_left_view_title.setText(itemMenus.get(i).getTitle());
        return view;
    }

    private static class ViewHolder{
        ImageView item_left_view_img;
        TextView item_left_view_title;
    }
}
