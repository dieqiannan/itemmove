package com.dqn.itemmove;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Created by Administrator on 2018/7/20/020.
 */

public class WHEImageView extends android.support.v7.widget.AppCompatImageView {
    public WHEImageView(Context context) {
        super(context);
    }

    public WHEImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WHEImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        //对应华为手机又问题
        
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
        
    }
}
