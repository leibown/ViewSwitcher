package com.leibown.library;

import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public interface ChildOperate {

    ChildView addView(View view);

    ChildView addView(View view, ChildViewInitType cChildViewInitType);

    ChildView addView(View view, int index, ChildViewInitType cChildViewInitType);

    ChildView addView(View view, int index, ViewGroup.LayoutParams params, ChildViewInitType cChildViewInitType);

    ChildView addView(int layoutId);

    ChildView addView(int layoutId, ChildViewInitType cChildViewInitType);

    ChildView addView(int layoutId, int index);

    ChildView addView(int layoutId, int index, ChildViewInitType cChildViewInitType);

    ChildView addView(Fragment fragment, FragmentManager fragmentManager);

    ChildView addView(Fragment fragment, FragmentManager fragmentManager, int index);

    ChildView addView(Fragment fragment, FragmentManager fragmentManager, ChildViewInitType cChildViewInitType);

    ChildView addView(Fragment fragment, FragmentManager fragmentManager, int index, ChildViewInitType cChildViewInitType);

    ChildView addView(ChildView childView);

    ChildView addView(ChildView childView, int index);

    ChildView addView(ChildView childView, int index, ViewGroup.LayoutParams params);

    ChildView removeView(View view);

    ChildView removeView(Fragment fragment);

    ChildView removeView(int id);

    ChildView removeViewAt(int index);

    ChildView removeView(ChildView childView);


    int indexOf(int id);

    int indexOf(View view);

    int indexOf(Fragment fragment);

    int indexOf(ChildView childView);


    ChildView getViewAt(int index);

    ChildView getView(int id);

    ChildView getView(View view);

    ChildView getView(Fragment fragment);


    int getChildCount();

    void showAt(int index);

    void show(int id);

    void show(View view);

    void show(Fragment fragment);

    void show(ChildView childView);

    void hide(int id);

    void hide(View view);

    void hide(Fragment fragment);

    void hide(ChildView childView);

}
