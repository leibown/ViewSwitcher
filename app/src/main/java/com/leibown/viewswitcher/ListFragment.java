package com.leibown.viewswitcher;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_list, container, false);
        RecyclerView recyclerView = inflate.findViewById(R.id.rv_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<String> stringList = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            stringList.add(i + "");
        }
        recyclerView.setAdapter(new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_list, stringList) {
            @Override
            protected void convert(BaseViewHolder holder, String s) {
                holder.setText(R.id.tv_1, s);
            }
        });

        return inflate;
    }


}