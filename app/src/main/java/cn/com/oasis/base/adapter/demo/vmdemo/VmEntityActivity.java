package cn.com.oasis.base.adapter.demo.vmdemo;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;

import cn.com.earth.vm.LoadMoreViewModel;
import cn.com.earth.vm.VmRecyclerAdapter;
import cn.com.oasis.demo.DataServer;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: hewei@incoming.cn
 * 时间:  16/12/1 上午10:44
 */

public class VmEntityActivity extends cn.com.oasis.base.adapter.demo.BaseActivity {
    protected VmRecyclerAdapter vmAdapter;
    private VmEntityMode entityMode;
    private VmTaskMode taskMode;
    private GridLayoutManager layoutManager;
    private LoadMoreViewModel moreViewModel;

    @Override
    protected void initView() {
        super.initView();
        refreshLayout.setEnabled(false);
        entityMode = new VmEntityMode();
        taskMode = new VmTaskMode();
        layoutManager = new GridLayoutManager(this, 4);
        moreViewModel = new LoadMoreViewModel();
        vmAdapter = new VmRecyclerAdapter(entityMode, taskMode, moreViewModel);

        vmAdapter.setFullSpan(4);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(vmAdapter);
        vmAdapter.setLoadmoreEnable(false);

        refreshLayout.setEnabled(true);
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
            }
        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onFresh();
            }
        });
        vmAdapter.setOnVmLoadMoreListener(new VmRecyclerAdapter.OnVmLoadMoreListener() {
            @Override
            public void onLoadMore() {
                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        taskMode.addAll(DataServer.getTask("ccc", 10, true));
                        vmAdapter.loadFailed();
                        refreshLayout.setEnabled(true);
                    }
                }, 1000);
            }
        });

        onFresh();
    }

    protected void onFresh() {
        vmAdapter.clear();
        vmAdapter.setLoadmoreEnable(false);
        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                entityMode.addAll(DataServer.getList("jacky", 20, true));
            }
        }, 1000);

        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                taskMode.addAll(DataServer.getTask("task", 20, true));
                vmAdapter.setLoadmoreEnable(true);
                refreshLayout.setRefreshing(false);
            }
        }, 2000);
    }
}
