package com.leibown.viewswitcher;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leibown.library.SwitcherHelper;
import com.leibown.library.ViewSwitcher;

public class SecondFragment extends Fragment {


    public SecondFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_second, container, false);
        ViewSwitcher viewSwitcher = inflate.findViewById(R.id.viewSwitcher);
        SwitcherHelper switcherHelper = viewSwitcher.getSwitcherHelper();
        switcherHelper.addView(new ListFragment(), getChildFragmentManager());
        return inflate;
    }
}