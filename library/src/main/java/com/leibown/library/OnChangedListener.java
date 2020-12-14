package com.leibown.library;

public interface OnChangedListener {


    void onAdd(int index, ChildView childView);

    void onShow(int index, ChildView childView);

    void onHide(int index, ChildView childView);

    void onRemoved(int index, ChildView childView);


}
