package com.leibown.library;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import androidx.annotation.LayoutRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;

public class SwitcherHelper implements ChildOperate {

    private Context context;

    /**
     * 初始化类型
     * 如果添加View时没有指定type，就使用这个type作为当前view的初始化类型
     */
    private ChildViewInitType mChildViewInitType;

    /**
     * 子View集合
     */
    ArrayList<ChildView> childViews;

    /**
     * 当前正在显示的View
     */
    private ChildView currentChildView;


    /**
     * childView状态发生变化监听
     */
    private OnChangedListener onChangedListener;


    private CallBack callBack;


    public SwitcherHelper(Context context, CallBack callBack) {
        this(context, null, callBack);
    }

    public SwitcherHelper(Context context, ChildViewInitType childViewInitType, CallBack callBack) {
        this.context = context;
        mChildViewInitType = childViewInitType;
        childViews = new ArrayList<>();
        this.callBack = callBack;
    }

    public void setOnChangedListener(OnChangedListener onChangedListener) {
        this.onChangedListener = onChangedListener;
    }

    @Override
    public ChildView addView(View view) {
        return addView(view, null);
    }

    @Override
    public ChildView addView(View view, ChildViewInitType cChildViewInitType) {

        return addView(view, -1, cChildViewInitType);
    }

    @Override
    public ChildView addView(View view, int index, ChildViewInitType cChildViewInitType) {
        return addView(view, index, null, cChildViewInitType);
    }

    @Override
    public ChildView addView(View view, int index, ViewGroup.LayoutParams params, ChildViewInitType cChildViewInitType) {
        if (indexOf(view) != -1) {
            throw new RuntimeException("view has been added");
        }
        ChildView child = ChildViewFactory.create(context, view, cChildViewInitType);
        return addView(child, index, params);
    }

    @Override
    public ChildView addView(int layoutId) {
        return addView(layoutId, mChildViewInitType);
    }

    @Override
    public ChildView addView(@LayoutRes int layoutId, ChildViewInitType childViewInitType) {
        return addView(layoutId, -1, childViewInitType);
    }

    @Override
    public ChildView addView(int layoutId, int index) {
        return addView(layoutId, index, mChildViewInitType);
    }

    @Override
    public ChildView addView(int layoutId, int index, ChildViewInitType childViewInitType) {
        ChildView child = ChildViewFactory.create(context, layoutId, childViewInitType);
        return addView(child, index);
    }

    @Override
    public ChildView addView(Fragment fragment, FragmentManager fragmentManager) {
        return addView(fragment, fragmentManager, mChildViewInitType);
    }

    @Override
    public ChildView addView(Fragment fragment, FragmentManager fragmentManager, int index) {
        return addView(fragment, fragmentManager, index, mChildViewInitType);
    }

    @Override
    public ChildView addView(Fragment fragment, FragmentManager fragmentManager, ChildViewInitType childViewInitType) {
        return addView(fragment, fragmentManager, -1, childViewInitType);
    }

    @Override
    public ChildView addView(Fragment fragment, FragmentManager fragmentManager, int index, ChildViewInitType childViewInitType) {
        if (indexOf(fragment) != -1) {
            throw new RuntimeException("fragment has been added");
        }
        ChildView child = ChildViewFactory.create(context, fragment, fragmentManager, childViewInitType);
        addView(child, index);
        return child;
    }

    @Override
    public ChildView addView(ChildView childView) {
        return addView(childView, -1);
    }

    @Override
    public ChildView addView(ChildView childView, int index) {

        return addView(childView, index, null);
    }

    @Override
    public ChildView addView(ChildView childView, int index, ViewGroup.LayoutParams params) {
        if (childViews.contains(childView)) {
            throw new RuntimeException("childView has been added");
        }
        if (childView.getChildViewInitType() == ChildViewInitType.DELAY_INIT) {
            childView.createEmptyView();
        } else {
            childView.createView();
        }

        /**
         * 默认显示第一个view
         */
        if (childViews.size() == 0) {
            show(childView);
        } else {
            hide(childView);
        }

        if (index < 0) {
            childViews.add(childView);
        } else {
            childViews.add(index, childView);
        }
        if (callBack != null) {
            callBack.addView(childView.getView(), index, params, childView.getChildViewInitType());
        }
        if (onChangedListener != null) {
            onChangedListener.onAdd(childViews.size() - 1, childView);
        }
        return childView;
    }

    @Override
    public ChildView removeView(View view) {
        int childIndex = indexOf(view);
        if (childIndex == -1) {
            return null;
        }
        return childViews.remove(childIndex);
    }

    @Override
    public ChildView removeView(Fragment fragment) {
        return removeView(getView(fragment));
    }

    @Override
    public ChildView removeView(int id) {
        return removeView(getView(id));
    }

    @Override
    public ChildView removeViewAt(int index) {
        return removeView(getViewAt(index));
    }

    @Override
    public ChildView removeView(ChildView childView) {
        if (childView != null && childViews.contains(childView)) {
            int index = childViews.indexOf(childView);
            if (onChangedListener != null) {
                onChangedListener.onRemoved(index, childView);
            }
            if (index >= getChildCount() - 1) {
                showAt(getChildCount() - 2);
            } else {
                showAt(index + 1);
            }
            childViews.remove(childView);
            if (callBack != null) {
                callBack.removeView(childView.getView());
            }
            childView.release();
            return childView;
        }
        return null;
    }

    @Override
    public int indexOf(int id) {
        for (int i = 0; i < childViews.size(); i++) {
            if (childViews.get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int indexOf(View view) {
        for (int i = 0; i < childViews.size(); i++) {
            if (childViews.get(i).getView() == view) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int indexOf(Fragment fragment) {
        for (int i = 0; i < childViews.size(); i++) {
            ChildView childView = childViews.get(i);
            if (childView instanceof FragmentChildView) {
                if (((FragmentChildView) childView).getFragment() == fragment) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public int indexOf(ChildView childView) {
        return childViews.indexOf(childView);
    }

    @Override
    public ChildView getViewAt(int index) {
        return childViews.get(index);
    }

    @Override
    public ChildView getView(int id) {
        int childIndex = indexOf(id);
        if (childIndex == -1)
            return null;
        return childViews.get(childIndex);
    }

    @Override
    public ChildView getView(View view) {
        int childIndex = indexOf(view);
        if (childIndex == -1)
            return null;
        return childViews.get(childIndex);
    }

    @Override
    public ChildView getView(Fragment fragment) {
        int childIndex = indexOf(fragment);
        if (childIndex == -1)
            return null;
        return childViews.get(childIndex);
    }

    @Override
    public int getChildCount() {

        return childViews.size();
    }

    @Override
    public void showAt(int index) {
        show(getViewAt(index));
    }

    @Override
    public void show(int id) {
        show(getView(id));
    }

    @Override
    public void show(View view) {
        show(getView(view));
    }

    @Override
    public void show(Fragment fragment) {
        show(getView(fragment));
    }

    @Override
    public void show(ChildView childView) {
        if (childView == null) {
            throw new RuntimeException("the view is null,check you is already addView");
        }

        if (childView == currentChildView) {
            return;
        }

        /**
         *延迟初始化
         */
        if (childView.getChildViewInitType() == ChildViewInitType.DELAY_INIT && !childView.isCreateView()) {
            View emptyView = childView.getView();
            childView.createView();
            if (callBack != null) {
                callBack.removeView(emptyView);
                callBack.addView(childView.getView(), childViews.indexOf(childView),
                        childView.getView().getLayoutParams(), childView.getChildViewInitType());
            }

        }

        if (onChangedListener != null) {
            onChangedListener.onHide(childViews.indexOf(currentChildView), currentChildView);
        }
        if (currentChildView != null) {
            Animation showAnimation = childView.getShowAnimation();
            if (showAnimation != null) {
                currentChildView.getView().setZ(-1);
                childView.getView().setZ(100);
                final ChildView oldChildView = currentChildView;
                currentChildView.getView().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        oldChildView.hide();
                    }
                }, showAnimation.getDuration());
            } else {
                currentChildView.hide();
            }
            Animation hideAnimation = currentChildView.getHideAnimation();
            if (hideAnimation != null) {
                currentChildView.getView().setZ(100);
                childView.getView().setZ(-1);
            }
        }

        currentChildView = childView;
        currentChildView.show();

        if (onChangedListener != null) {
            onChangedListener.onShow(childViews.indexOf(currentChildView), currentChildView);
        }
    }

    public int getCurrentIndex() {
        return indexOf(currentChildView);
    }

    public ChildView getCurrentChildView() {
        return currentChildView;
    }

    @Override
    public void hide(int id) {
        hide(getView(id));
    }

    @Override
    public void hide(View view) {
        hide(getView(view));
    }

    @Override
    public void hide(Fragment fragment) {
        hide(getView(fragment));
    }

    @Override
    public void hide(ChildView childView) {
        if (childView == null) {
            throw new RuntimeException("the view is null,check you is already addView");
        }
        childView.hide();

        if (onChangedListener != null) {
            onChangedListener.onHide(childViews.indexOf(childView), childView);
        }
    }


    public interface CallBack {

        void addView(View child, int index, ViewGroup.LayoutParams params, ChildViewInitType childViewInitType);

        void removeView(View child);

    }


}
