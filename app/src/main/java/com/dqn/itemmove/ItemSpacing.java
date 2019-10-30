package com.dqn.itemmove;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * item的分割线
 *
 * @author Xiao_
 * @date 2018/5/28
 */
public class ItemSpacing extends RecyclerView.ItemDecoration {

    private static final String TAG = "ItemSpacing";
    private final int mSpace;

    public ItemSpacing(int space) {
        mSpace = space;
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        final int position = parent.getChildAdapterPosition(view);
        if (parent.getLayoutManager() instanceof GridLayoutManager) {
            getGridOffsets(outRect, position, (GridLayoutManager) parent.getLayoutManager());
        } else if (parent.getLayoutManager() instanceof LinearLayoutManager) {
            getLinearOffsets(outRect, position, (LinearLayoutManager) parent.getLayoutManager());
        }
    }

    private void getGridOffsets(Rect outRect, int position, GridLayoutManager layoutManager) {
        int spanCount = layoutManager.getSpanCount();
        float total = mSpace * (spanCount - 1) / spanCount;

        GridLayoutManager.SpanSizeLookup spanSizeLookup = layoutManager.getSpanSizeLookup();
        int spanIndex = spanSizeLookup.getSpanIndex(position, spanCount);
        float ahead = mSpace * spanIndex / spanCount;



        if (layoutManager.getOrientation() == LinearLayoutManager.VERTICAL) {
            if (spanSizeLookup.getSpanSize(position) == 1) {
                outRect.bottom = Math.round(mSpace);
                if (spanIndex == 0) {
                    outRect.right = Math.round(total);
                } else if (spanIndex == spanCount - 1) {
                    outRect.left = Math.round(total);
                } else {
                    outRect.left = Math.round(ahead);
                    outRect.right = Math.round(total - ahead);
                }
            }
        } else if (layoutManager.getOrientation() == LinearLayoutManager.HORIZONTAL) {
            if (spanSizeLookup.getSpanSize(position) == 1) {
                outRect.right = Math.round(mSpace);
                if (spanIndex == 0) {
                    outRect.bottom = Math.round(total);
                } else if (spanIndex == spanCount - 1) {
                    outRect.top = Math.round(total);
                } else {
                    outRect.top = Math.round(ahead);
                    outRect.bottom = Math.round(total - ahead);
                }
            }
        }
    }

    private void getLinearOffsets(Rect outRect, int position, LinearLayoutManager layoutManager) {
        if (layoutManager.getOrientation() == LinearLayoutManager.VERTICAL) {
            if (position > 0) {
                outRect.top = mSpace;
            }
        } else if (layoutManager.getOrientation() == LinearLayoutManager.HORIZONTAL) {
            if (position > 0) {
                outRect.left = mSpace;
            }
        }
    }
}