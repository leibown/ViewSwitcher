package com.leibown.viewswitcher;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class BlankFragment extends Fragment {


    private String text;
    private String color;

    public static BlankFragment getInstance(String text, String color) {
        BlankFragment blankFragment = new BlankFragment();
        Bundle bundle = new Bundle();
        bundle.putString("text", text);
        bundle.putString("color", color);
        blankFragment.setArguments(bundle);
        return blankFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View inflate = inflater.inflate(R.layout.fragment_blank, container, false);
        Bundle arguments = getArguments();
        text = arguments.getString("text");
        color = arguments.getString("color");
        TextView tV = inflate.findViewById(R.id.tv_text);
        tV.setText(text);
        tV.setBackgroundColor(Color.parseColor(color));
        Log.e("leibown", "onCreateView:" + text + "__color:" + color);
        return inflate;
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.e("leibown", "onResume:" + text + "__color:" + color);
    }


    @Override
    public void onPause() {
        super.onPause();
        Log.e("leibown", "onPause:" + text + "__color:" + color);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("leibown", "onDestroy:" + text + "__color:" + color);
    }
}