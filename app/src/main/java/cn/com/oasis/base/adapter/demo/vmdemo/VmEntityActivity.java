package cn.com.oasis.base.adapter.demo.vmdemo;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;

import cn.com.earth.adapter.decoration.RecyclerSpace;
import cn.com.earth.adapter.decoration.StickyDecoration;
import cn.com.earth.vm.LoadMoreViewModel;
import cn.com.earth.vm.StickyAdapter;
import cn.com.earth.vm.VmRecyclerAdapter;
import cn.com.oasis.R;
import cn.com.oasis.demo.DataServer;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: hewei@incoming.cn
 * 时间:  16/12/1 上午10:44
 */

public class VmEntityActivity extends cn.com.oasis.base.adapter.demo.BaseActivity {
    protected StickyAdapter vmAdapter;
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
        vmAdapter = new StickyAdapter(entityMode, taskMode, moreViewModel);

        vmAdapter.setFullSpan(4);
        vmAdapter.setHasStableIds(true);
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

        recyclerView.addItemDecoration(new StickyDecoration(vmAdapter));

        recyclerView.addItemDecoration(new RecyclerSpace((int) getResources().getDimension(R.dimen.q10)));

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //onFresh();
                subOnFresh();
            }
        });
        vmAdapter.setOnVmLoadMoreListener(new VmRecyclerAdapter.OnVmLoadMoreListener() {
            @Override
            public void onLoadMore() {
                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //taskMode.addAll(DataServer.getTask("ccc", 10, true));
                        vmAdapter.loadFailed();
                        refreshLayout.setEnabled(true);
                    }
                }, 1000);
            }
        });

        onFresh();
    }

    protected void onFresh() {
        vmAdapter.setLoadmoreEnable(false);
       /* recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                entityMode.addAll(DataServer.getList("jacky", 20, true));
            }
        }, 1000);*/

        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                taskMode.addAll(DataServer.getTask("tasdadak", 20, true));
                vmAdapter.setLoadmoreEnable(true);
                refreshLayout.setRefreshing(false);
            }
        }, 2000);
    }

    protected void subOnFresh() {
        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                vmAdapter.clear();
                taskMode.addAll(DataServer.getTask("taskccdadaddadabamdbamdbmadbmabadadada", 20, true));
                vmAdapter.setLoadmoreEnable(true);
                refreshLayout.setRefreshing(false);
            }
        }, 2000);
    }
}
