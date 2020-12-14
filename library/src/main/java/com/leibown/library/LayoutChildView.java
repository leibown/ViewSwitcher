package com.leibown.library;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.LayoutRes;

public class LayoutChildView extends ChildView {

    private int mLayoutId;

    public LayoutChildView(Context context, @LayoutRes int layoutId, ChildViewInitType childViewInitType) {
        super(context, childViewInitType);
        mLayoutId = layoutId;
    }

    @Override
    protected View initView() {

        return View.inflate(mContext, mLayoutId, null);
    }

}
