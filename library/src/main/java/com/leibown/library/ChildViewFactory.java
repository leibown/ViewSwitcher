package com.leibown.library;

import android.content.Context;
import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class ChildViewFactory {


    public static ChildView create(Context context, View view, ChildViewInitType childViewInitType) {
        return new ChildView(context, view, childViewInitType);
    }

    public static ChildView create(Context context, @LayoutRes int layoutId, ChildViewInitType childViewInitType) {
        return new LayoutChildView(context, layoutId, childViewInitType);
    }

    public static ChildView create(Context context, Fragment fragment, FragmentManager fragmentManager, ChildViewInitType childViewInitType) {
        return new FragmentChildView(context, fragment, fragmentManager, childViewInitType);
    }


}
