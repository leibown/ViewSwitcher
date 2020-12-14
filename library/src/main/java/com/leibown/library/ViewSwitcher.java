package com.leibown.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;


public class ViewSwitcher extends FrameLayout {

    private SwitcherHelper switcherHelper;
    private int showIndex;

    public ViewSwitcher(Context context) {
        this(context, null);
    }

    public ViewSwitcher(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewSwitcher(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initParameter(context);

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.ViewSwitcher);

        if (attributes != null) {
            showIndex = attributes.getInteger(R.styleable.ViewSwitcher_showIndex, -1);
            attributes.recycle();
        }

    }

    private void initParameter(Context context) {
        switcherHelper = new SwitcherHelper(context, new SwitcherHelper.CallBack() {
            @Override
            public void addView(View child, int index, ViewGroup.LayoutParams params, ChildViewInitType childViewInitType) {
                ViewSwitcher.this.addView(child, index, params, childViewInitType);
            }

            @Override
            public void removeView(View child) {
                ViewSwitcher.super.removeView(child);
            }
        });
    }


    @Override
    public void addView(View child) {
        addView(child, -1);
    }

    @Override
    public void addView(View child, int index) {
        addView(child, index, null);
    }


    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        switcherHelper.addView(child, index, params, null);
    }


    private void addView(View view, int index, ViewGroup.LayoutParams params, ChildViewInitType childViewInitType) {

        if (params == null) {
            params = view.getLayoutParams();
        }

        if (!(params instanceof MarginLayoutParams))
            params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);


        attachViewToParent(view, index, params);
        if (showIndex >= 0) {
            if (showIndex == getChildCount() - 1) {
                switcherHelper.show(view);
            }
        }
    }

    @Override
    public void removeView(View view) {
        switcherHelper.removeView(view);
    }

    @Override
    public void removeViewAt(int index) {
        switcherHelper.removeViewAt(index);
    }


    public SwitcherHelper getSwitcherHelper() {
        return switcherHelper;
    }
}
