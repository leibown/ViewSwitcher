package com.leibown.viewswitcher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leibown.library.ChildView;
import com.leibown.library.ChildViewFactory;
import com.leibown.library.ChildViewInitType;
import com.leibown.library.DisplayUtil;
import com.leibown.library.OnChangedListener;
import com.leibown.library.SwitcherHelper;
import com.leibown.library.ViewSwitcher;

public class MainActivity extends AppCompatActivity {

    private int count;

    private String[] colors = {"#A93226", "#CB4335", "#884EA0", "#2471A3", "#2E86C1", "#17A589", "#138D75", "#229954", "#28B463", "#D4AC0D", "#2E86C1", "#884EA0",
            "#A93226", "#CB4335", "#884EA0", "#2471A3", "#2E86C1", "#17A589", "#138D75", "#229954", "#28B463", "#D4AC0D", "#2E86C1", "#884EA0"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewSwitcher viewSwitcher = findViewById(R.id.viewSwitcher);
        final SwitcherHelper switcherHelper = viewSwitcher.getSwitcherHelper();
        switcherHelper.show(R.id.tv_1);
        for (int i = 4; i < 24; i++) {
            BlankFragment fragment = BlankFragment.getInstance("我是第" + i + "个", colors[i % 24]);
            if (i < 10) {
                switcherHelper.addView(fragment, getSupportFragmentManager());
            } else {
                switcherHelper.addView(fragment, getSupportFragmentManager(), ChildViewInitType.DELAY_INIT);
            }

        }

//        RecyclerView


        switcherHelper.addView(ChildViewFactory.create(this, R.layout.layout_123, null));

        ChildView childView = switcherHelper.addView(R.layout.layout_123, ChildViewInitType.DELAY_INIT);
//        switcherHelper.show(childView);

        switcherHelper.setOnChangedListener(new OnChangedListener() {
            @Override
            public void onAdd(int index, ChildView childView) {
            }

            @Override
            public void onShow(int index, ChildView childView) {
            }

            @Override
            public void onHide(int index, ChildView childView) {
            }

            @Override
            public void onRemoved(int index, ChildView childView) {
            }
        });

        final LinearLayout llContainer = findViewById(R.id.llContainer);
        for (int i = 0; i < switcherHelper.getChildCount(); i++) {
            TextView textView = new TextView(this);
            int px = DisplayUtil.dip2px(this, 10);
            textView.setPadding(px + 10, px, px + 10, px);
            textView.setText(String.valueOf(i));
            final int finalI = i;
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switcherHelper.showAt(finalI);
                    int childCount = llContainer.getChildCount();
                    for (int i1 = 0; i1 < childCount; i1++) {
                        llContainer.getChildAt(i1).setBackgroundColor(Color.TRANSPARENT);
                    }
                    v.setBackgroundColor(Color.GRAY);
                }
            });
            llContainer.addView(textView);
        }
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switcherHelper.removeViewAt(count);
                llContainer.removeViewAt(count);
                count--;
            }
        });
        count = switcherHelper.getChildCount() - 1;

    }


}