package com.dqn.itemmove;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;


/**
 * Created by Administrator on 2018/7/19/019.
 * <p>
 * 布局 4列
 */

public class MoveItemView extends FrameLayout {

    private static final String TAG = "MoveItemView";

    private View mView01;
    private View mView02;

    //private int mSpec; //gridView间隔
    private int mViewWidth;
    private int mViewHeight;
    private int mItemTitleHeight;
    private int mTitleWidth;
    private int mContentSize;
    // private int mImageWidth; // 图片布局的 图片宽度(计算得来)
    private int mContentHeight;
    private int mItemTop;

    public MoveItemView(@NonNull Context context) {
        this(context, null);
    }

    public MoveItemView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MoveItemView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //getResources().getDimension(R.dimen.h_move_item);
        mItemTitleHeight = (int) getResources().getDimension(R.dimen.h_move_item_title);
        //mSpec = (int) getResources().getDimension(R.dimen.w_move_item_content_spec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mViewWidth = getWidth();
        mViewHeight = getHeight();

        //mImageWidth = (int) ((mViewWidth - mSpec * 3) * 1f / 4);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //widthMeasureSpec 就不计算了, 根据布局走, match_parent
        //heightMeasureSpec 计算

        //LogUtils.e(TAG, "onMeasure");
        //measureChildren(widthMeasureSpec, heightMeasureSpec);

        if (mView01 == null) {
            mView01 = getChildAt(0);
            mView02 = getChildAt(1);
        }


        if (mContentSize > 0) {
            //LogUtils.e(TAG, "图片数量mContentSize = " + mContentSize);
            //计算行数
            //int lines = (int) Math.ceil(mContentSize * 1f / 4);
            //mView02是title布局 高度已经写死,

            //mContentHeight = lines * mImageWidth + (lines - 1) * mSpec;

            //int heightMeasureSpecmView01 = MeasureSpec.makeMeasureSpec(mContentHeight, MeasureSpec
            //.EXACTLY);
            //mView01.measure(widthMeasureSpec, heightMeasureSpecmView01);


            //mViewHeight = mContentHeight + mItemTitleHeight;
            //heightMeasureSpec = MeasureSpec.makeMeasureSpec(mViewHeight, MeasureSpec.EXACTLY);
        }


        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        //LogUtils.e(TAG, "onLayout");
        //mView01.layout(0, (int) mItemTitleHeight, mView01.getWidth(), (int) (mItemTitleHeight +
        //mView01.getHeight()));
        //mView01.layout(0, (int) mItemTitleHeight, mViewWidth, mContentHeight + mItemTitleHeight);
        //防止键盘弹出的过程中, 界面重汇导致title布局位置错误
        //mView02.layout(0, 0, mView02.getWidth(), mView02.getHeight());
        mTitleWidth = mView02.getWidth();
        top = Math.abs(mItemTop);
        if (mViewHeight>0 ) {
            
            if (top > mViewHeight - mItemTitleHeight) {
                top = mViewHeight - mItemTitleHeight;
            }

            if (top<0) {
                top = 0;
            }
            int bottem = top + mItemTitleHeight;
            mView02.layout(0, top, mTitleWidth, bottem);

        }else {
            mView02.layout(0, 0, mTitleWidth, mItemTitleHeight);
        }
        
    }

    protected int dp2px(int dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, getResources()
                .getDisplayMetrics());
    }

    protected int sp2px(int spValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, getResources()
                .getDisplayMetrics());
    }


    /**
     * 移动的距离
     * itemTop  -mViewHeight - 0
     *
     * @param mDy
     */
    public void setDy(int mDy, int itemTop) {

        // LogUtils.e(TAG, "mDy=" + mDy);
        // LogUtils.e(TAG, "mView02.getTop()=" + mView02.getTop());
        //LogUtils.e(TAG, "mViewHeight =" + mViewHeight);
        //invalidate();


        mItemTop = itemTop;
        mViewHeight = getHeight();

        if (mDy > 0) {


          /*  int top = (mView02.getTop() + mDy);
            if (top < 0) {
                top = 0;
            } else if (top > mViewHeight - mItemTitleHeight) {
                top = mViewHeight - mItemTitleHeight;
            }
            int bottem = top + mItemTitleHeight;
            mView02.layout(0, top, mTitleWidth, bottem);*/

            int top = Math.abs(itemTop);
            if (top > mViewHeight - mItemTitleHeight) {
                top = mViewHeight - mItemTitleHeight;
            }

            int bottem = top + mItemTitleHeight;

            mView02.layout(0, top, mTitleWidth, bottem);


        } else if (mDy < 0) {


            if (Math.abs(itemTop) <= mViewHeight - mItemTitleHeight) {
                //开始滑动
                /*int top = (mView02.getTop() + mDy);

                if (top < 0) {
                    top = 0;
                } else if (top > mViewHeight - mItemTitleHeight) {
                    top = mViewHeight - mItemTitleHeight;
                }


                int bottem = top + mItemTitleHeight;
                mView02.layout(0, top, mTitleWidth, bottem);*/

                int top = Math.abs(itemTop);
                if (top > mViewHeight - mItemTitleHeight) {
                    top = mViewHeight - mItemTitleHeight;
                } else if (top < 3) {
                    top = 0;
                }

                int bottem = top + mItemTitleHeight;
                mView02.layout(0, top, mTitleWidth, bottem);
            } else {
                //定死
                //防止item复用的问题
                mView02.layout(0, mViewHeight - mItemTitleHeight, mTitleWidth, mViewHeight);
            }

        }

    }

    /**
     * 解决复用的问题
     */
    public void setRestoreDown() {

        mItemTop = 0;

        if (mView02 != null) {
            mView02.layout(0, 0, mTitleWidth, mItemTitleHeight);
        }

    }

    /**
     * 解决复用的问题
     */
    public void setRestoreUp() {
        mItemTop = 0;
        if (mView02 != null) {
            mView02.layout(0, mViewHeight - mItemTitleHeight, mTitleWidth, mViewHeight);
        }

    }

    /**
     * 得到图片布局数量
     * 为了计算行数
     *
     * @param size
     */
    public void setContentViewNum(int size) {
        this.mContentSize = size;
        //invalidate();
    }
}
