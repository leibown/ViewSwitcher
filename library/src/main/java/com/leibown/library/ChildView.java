package com.leibown.library;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.util.ArrayList;

public class ChildView {

    protected static ArrayList<Integer> IDS = new ArrayList<>();

    protected Context mContext;

    protected View mView;

    protected ChildViewInitType mChildViewInitType;

    /**
     * 当初始化类型为INIT_DELAY，tempView用来存放原有的view，当调用show()
     * 方法时用tempView替换原有mView
     */
    protected View tempView;

    protected int id;

    private boolean isCreateView;

    private int transAnimator = PageTransAnimator.DEFAULT;


    public ChildView(Context context, View view) {
        this(context, view, null);
    }


    protected ChildView(Context context, ChildViewInitType childViewInitType) {
        this(context, null, childViewInitType);
    }

    public ChildView(Context context, View view, ChildViewInitType childViewInitType) {
        this.mContext = context;
        mChildViewInitType = childViewInitType == null ? ChildViewInitType.DEFAULT : childViewInitType;
        tempView = view;
        if (tempView != null) {
            id = tempView.getId();
        } else {
            id = getUnUsedId();
        }
    }

    /**
     * 此方法用于当childView的初始化类型为DELAY_INIT时，此时mView可能并没有完全初始化完成，比如FragmentChildView
     * 中使用DELAY_INIT类型初始化时，并没有直接把fragment直接放入到容器View里面去，所以此方法就用于类似这种情况下把
     * fragment加入容器View中或者做一些初始化操作等
     */
    public void createView() {
        mView = initView();
        isCreateView = true;
    }

    protected View initView() {
        return tempView;
    }

    public void setTransAnimator(int transAnimator) {
        this.transAnimator = transAnimator;
    }

    /**
     * 当ChildView初始化类型为DELAY_INIT， 此时ChildView内部view并没有创建，所以此时为空，
     * 这里用一个空view来占容器中的位置
     */
    public void createEmptyView() {
        mView = new View(mContext);
    }


    public View getView() {
        return mView;
    }


    private static long lastTimeMillis = 0;

    /**
     * 获取未使用的ID
     *
     * @return
     */
    private int getUnUsedId() {
        if (IDS.size() < 5) {
            int size = IDS.size();
            long currentTimeMillis = System.currentTimeMillis();
            for (int i = 0; i < 10 - size; i++) {
                /**
                 * 这个地方我们使用当前毫秒值和i来拼凑出ID，为了避免这一次和上一次的毫秒值相同导致ID重复，
                 * 这里使用一个类属性lastTimeMillis来储存上一次的毫秒值，如果lastTimeMillis大于或等于当前毫秒值
                 * 我们就取lastTimeMillis++的值来拼凑ID
                 */
                if (lastTimeMillis >= currentTimeMillis) {
                    lastTimeMillis++;
                } else {
                    lastTimeMillis = currentTimeMillis;
                }
                Integer integer = Integer.valueOf(String.valueOf(lastTimeMillis).substring(6) + i);
                IDS.add(integer);
            }
        }
        return IDS.remove(0);
    }


    public int getId() {
        return id;
    }


    public void show() {
        if (mView != null) {
            mView.setVisibility(View.VISIBLE);
            Animation showAnimation = getShowAnimation();
            if (showAnimation != null) {
                mView.startAnimation(showAnimation);
            }
        }
    }


    public void hide() {
        if (mView != null) {
            Animation hideAnimation = getHideAnimation();
            if (hideAnimation != null) {
                mView.startAnimation(hideAnimation);
                mView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mView.setVisibility(View.GONE);
                    }
                }, hideAnimation.getDuration());
            } else {
                mView.setVisibility(View.GONE);
            }
        }
    }

    public Animation getShowAnimation() {
        if (transAnimator == PageTransAnimator.DEFAULT) {
            return null;
        }
        // if (transAnimator==PageTransAnimator.DEFAULT){
        // }
        return AnimationUtils.loadAnimation(getView().getContext(), R.anim.slide_in_top);
    }

    public Animation getHideAnimation() {
        if (transAnimator == PageTransAnimator.DEFAULT) {
            return null;
        }
        // if (transAnimator==PageTransAnimator.DEFAULT){
        // }
        return AnimationUtils.loadAnimation(getView().getContext(), R.anim.slide_out_top);
    }

    public ChildViewInitType getChildViewInitType() {
        return mChildViewInitType;
    }

    public void release() {
        mContext = null;
        mView = null;
        tempView = null;
    }

    public boolean isCreateView() {
        return isCreateView;
    }
}
