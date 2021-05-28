package com.example.permissionner.demo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.permissionner.R;

import java.util.List;

/**
 * wp
 */
public class ListAdapter extends BaseAdapter {
    private List<String> permissionList;
    private LayoutInflater inflater;

    public ListAdapter(Context context, List<String> permissionList) {
        this.permissionList = permissionList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return permissionList == null ? 0 : permissionList.size();
    }

    @Override
    public String getItem(int i) {
        return permissionList == null ? "" : permissionList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View contentView = inflater.inflate(R.layout.item_single_text, viewGroup, false);
        TextView permissionNameTv = contentView.findViewById(R.id.tv_permission_name);
        permissionNameTv.setText(getItem(i));
        return contentView;
    }
}
