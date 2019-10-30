package com.dqn.itemmove;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    private ArrayList<String> mList;
    private MyAdapter         mAdapter;
    private int               mSpec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);

        mSpec = (int) getResources().getDimension(R.dimen.w_move_item_content_spec);

        mList = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            mList.add("---");
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new MyAdapter();

        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {


            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                //LogUtils.e(TAG,"onScrollStateChanged  newState= "+newState);

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                //LogUtils.e(TAG, "onScrolled  dy=" + dy);

                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (layoutManager instanceof LinearLayoutManager) {
                    LinearLayoutManager manager = (LinearLayoutManager) layoutManager;

                    int firstPosition = manager.findFirstVisibleItemPosition();
                    int listPosition = manager.findLastVisibleItemPosition();
                    //LogUtils.e(TAG, "firstPosition =" + firstPosition);
                    //LogUtils.e(TAG, "listPosition =" + listPosition);


                    MyAdapter adapter = (MyAdapter) recyclerView.getAdapter();
                    if (dy > 0) {
                        //还原最下面的位置
                        adapter.mMapHolder.get(listPosition).setRestoreDown();
                    } else if (dy < 0) {
                        //还原最上面的第二个item的位置
                        //防止快速滑动的问题
                        MyHolder myHolder = adapter.mMapHolder.get(firstPosition + 1);
                        if (myHolder != null) {
                            myHolder.setRestoreDown();
                        }
                    }


                    MyHolder holderFirst = adapter.mMapHolder.get(firstPosition);
                    View itemView = manager.findViewByPosition(firstPosition);
                    int top = itemView.getTop();
                    //LogUtils.e(TAG, "onScrolled  itemView.getTop()=" + top);
                    holderFirst.setDy(dy, top);

                }
            }
        });
    }

    class MyAdapter extends RecyclerView.Adapter<MyHolder> {

        public Map<Integer, MyHolder> mMapHolder = new TreeMap<>();

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = View.inflate(MainActivity.this, R.layout.item_item_move, null);
            MyHolder myHolder = new MyHolder(view);
            return myHolder;
        }

        @Override
        public void onBindViewHolder(MyHolder holder, int position) {
            //LogUtils.e(TAG, "onBindViewHolder position=" + position);


            mMapHolder.put(position, holder);


            ArrayList<String> mList = new ArrayList<>();
            for (int i = 0; i < 13; i++) {
                mList.add("---");
            }


            if (position % 2 == 0) {
                MyItemAdapter adapter = new MyItemAdapter(MainActivity.this, mList);
                //LinearLayoutManager ms= new LinearLayoutManager(RVMoveItemActivity.this);

                //ms.setOrientation(LinearLayoutManager.HORIZONTAL);// 设置 recyclerview 布局方式为横向布局

                GridLayoutManager ms = new GridLayoutManager(MainActivity.this, 4);
                //ms.scrollToPosition(0);
                holder.mItemRV.setLayoutManager(ms);
                holder.mItemRV.addItemDecoration(new ItemSpacing(mSpec));
                holder.mItemRV.setAdapter(adapter);

                holder.mItemRV.scrollTo(1,50);


                holder.mItemRV.setFocusable(false);

            } else {
                MyItemAdapter adapter = new MyItemAdapter(MainActivity.this, mList);
                //LinearLayoutManager ms= new LinearLayoutManager(RVMoveItemActivity.this);

                //ms.setOrientation(LinearLayoutManager.HORIZONTAL);// 设置 recyclerview 布局方式为横向布局

                GridLayoutManager ms = new GridLayoutManager(MainActivity.this, 2,
                        GridLayoutManager.HORIZONTAL, false);

                holder.mItemRV.setLayoutManager(ms);
                holder.mItemRV.addItemDecoration(new ItemSpacing(mSpec));
                holder.mItemRV.setAdapter(adapter);
                holder.mItemRV.setFocusable(false);
            }


        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

    }


    class MyHolder extends RecyclerView.ViewHolder {

        public MoveItemView moveItemView;
        public RecyclerView mItemRV;

        public MyHolder(View itemView) {
            super(itemView);
            moveItemView = itemView.findViewById(R.id.moveItemView);
            mItemRV = itemView.findViewById(R.id.rv);
        }

        public void setDy(int dy, int itemTop) {

            moveItemView.setDy(dy, itemTop);
        }


        public void setRestoreDown() {
            moveItemView.setRestoreDown();
        }

        public void setRestoreUp() {
            moveItemView.setRestoreUp();
        }


    }


    class MyItemAdapter extends RecyclerView.Adapter<MyItemHolder> {

        Context mContext;
        ArrayList<String> mList;

        public MyItemAdapter(Context context, ArrayList<String> list) {
            mContext = context;
            mList = list;
        }

        @Override
        public MyItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = View.inflate(mContext, R.layout.item_item_rv, null);
            MyItemHolder myHolder = new MyItemHolder(view);
            return myHolder;
        }

        @Override
        public void onBindViewHolder(MyItemHolder holder, int position) {
            //LogUtils.e(TAG, "onBindViewHolder position=" + position);

        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

    }

    class MyItemHolder extends RecyclerView.ViewHolder {

        public MyItemHolder(View itemView) {
            super(itemView);
            View viewById = itemView.findViewById(R.id.v_icon);
            viewById.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //LogUtils.e("MyItemHolder","点击图片");
                }
            });
        }
    }
}
