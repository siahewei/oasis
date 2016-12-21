package cn.com.oasis.demo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.util.SortedList;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.com.earth.adapter.AbsRecyclerAdapter;
import cn.com.earth.adapter.BaseRecyclerAdapter;
import cn.com.earth.adapter.RecyclerOnClickListener;
import cn.com.oasis.R;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: hewei@incoming.cn
 * 时间:  16/11/28 下午11:55
 */

public class TestAdapterActivity extends Activity {
    private RecyclerView recyclerView;
    private Handler handler = new Handler();
    private SwipeRefreshLayout refreshLayout;
    private TestAdapter adapter;
    private boolean isFirst = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.demo_adapter_test);
        recyclerView = (RecyclerView) findViewById(R.id.rv_content);
        recyclerView.addOnItemTouchListener(new RecyclerOnClickListener() {

            @Override
            public void onItemClick(AbsRecyclerAdapter adapter, View view, int position) {

            }

            @Override
            public void onItemLongClick(AbsRecyclerAdapter adapter, View view, int position) {

            }

            @Override
            public void onItemChildClick(AbsRecyclerAdapter adapter, View view, int position) {

            }

            @Override
            public void onItemChildLongClick(AbsRecyclerAdapter adapter, View view, int position) {

            }
        });
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshlayout);
        final List<Entity> data = new ArrayList<>();
        adapter = new TestAdapter(data);
        adapter.setOnLoadMoreListener(new BaseRecyclerAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.addDatas(DataServer.getList("new", 40, false));
                        adapter.loadMoreFail();
                    }
                }, 3000);
            }
        });
        refreshLayout.setEnabled(true);

        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
            }
        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refresh();
                    }
                }, 3000);

            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        isFirst = true;

        refresh();
        //sortList();
    }


    private void refresh() {
        adapter.setEnableLoadMore(false);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(false);
                adapter.clear();
                adapter.addDatas(DataServer.getList("fresh", 10, false));
                adapter.setEnableLoadMore(true);
            }
        }, 10000);
    }

    SortedList<Entity> list = new SortedList<Entity>(Entity.class, new SortedList.Callback<Entity>() {
        @Override
        public int compare(Entity o1, Entity o2) {
            return o1.compare(o1, o2);
        }

        @Override
        public void onChanged(int position, int count) {
            Log.e("jacky", "onChanged" + position + " dada" + count);
        }

        @Override
        public boolean areContentsTheSame(Entity oldItem, Entity newItem) {
            return oldItem.getData().equals(newItem.getData());
        }

        @Override
        public boolean areItemsTheSame(Entity item1, Entity item2) {
            return item1.compare(item1, item2) == 0;
        }

        @Override
        public void onInserted(int position, int count) {
            Log.e("jacky", "onInserted" + position + " dada" + count);
        }

        @Override
        public void onRemoved(int position, int count) {
            Log.e("jacky", "onRemoved" + position + " dada" + count);

        }

        @Override
        public void onMoved(int fromPosition, int toPosition) {
            Log.e("jacky", "onMoved" + fromPosition + " dada" + toPosition);

        }
    });

    private void sortList() {
        list.addAll(DataServer.getList("123", 10, true));
        list.addAll(DataServer.getList("adada", 20, false));

        for (int i = 0; i < list.size(); i++) {
            Log.e("jacky", list.get(i).toString());
        }

        Log.e("jacky", "size:" + list.size());
    }
}
