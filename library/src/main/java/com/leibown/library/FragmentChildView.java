package com.leibown.library;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class FragmentChildView extends ChildView {

    private Fragment mFragment;

    private FragmentManager mFragmentManager;


    public FragmentChildView(Context context, Fragment fragment, FragmentManager fragmentManager, ChildViewInitType childViewInitType) {
        super(context, childViewInitType);
        this.mFragment = fragment;
        this.mFragmentManager = fragmentManager;

    }

    @Override
    protected View initView() {
        View frameLayout = new FrameLayout(mContext);
        frameLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        frameLayout.setId(id);
        initFragment(frameLayout);
        return frameLayout;
    }

    private void initFragment(View fragmentContainer) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.add(fragmentContainer.getId(), mFragment);
        fragmentTransaction.commit();
    }


    public Fragment getFragment() {
        return mFragment;
    }

    @Override
    public void show() {
        super.show();
        if (mFragment.isAdded()) {
            mFragment.onResume();
        }
    }

    @Override
    public void hide() {
        if (mFragment != null && mFragment.isAdded()) {
            mFragment.onPause();
        }
        super.hide();
    }

    @Override
    public void release() {
        super.release();
        if (mFragment != null && mFragmentManager != null) {
            if (mFragment.isAdded()) {
                mFragmentManager.beginTransaction().remove(mFragment);
            }
            mFragment.onDestroy();
            mFragment = null;
            mFragmentManager = null;
        }
    }
}
