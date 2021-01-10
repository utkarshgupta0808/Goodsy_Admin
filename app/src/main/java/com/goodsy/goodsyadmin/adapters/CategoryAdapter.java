package com.goodsy.goodsyadmin.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.goodsy.goodsyadmin.R;
import com.goodsy.goodsyadmin.models.CategoryModel;

import java.util.ArrayList;

public class CategoryAdapter extends ArrayAdapter<CategoryModel> {

    TextView textViewName;

    public CategoryAdapter(@NonNull Context context, ArrayList<CategoryModel> statesList) {
        super(context, 0, statesList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @SuppressLint("ResourceAsColor")
    private View initView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.recycler_category, parent, false
            );
        }

        textViewName = convertView.findViewById(R.id.text_time);

        CategoryModel currentItem = getItem(position);

        if (currentItem != null) {
            textViewName.setText(currentItem.getItemCategory());
        }

        if (position == 0) {
            textViewName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_icon_down, 0);
//            textViewName.setTextColor(R.color.textColor);
        }

        return convertView;

    }
}
