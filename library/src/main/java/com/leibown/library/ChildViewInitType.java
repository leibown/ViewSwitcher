package com.leibown.library;

public enum ChildViewInitType {


    /**
     * 默认模式，一开始就初始化View并添加到ViewSwitcher中
     */
    DEFAULT,

    /**
     * 延迟初始化，当View或者fragment需要显示时才初始化View
     */
    DELAY_INIT


}
