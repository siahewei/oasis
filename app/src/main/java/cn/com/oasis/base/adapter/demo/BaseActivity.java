package cn.com.oasis.base.adapter.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import cn.com.earth.adapter.AbsRecyclerAdapter;
import cn.com.earth.adapter.RecyclerOnClickListener;
import cn.com.oasis.R;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: hewei@incoming.cn
 * 时间:  16/12/1 上午10:53
 */

public class BaseActivity extends FragmentActivity {
    protected RecyclerView recyclerView;
    protected SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_adapter_test);
        recyclerView = (RecyclerView) findViewById(R.id.rv_content);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshlayout);
        recyclerView.addOnItemTouchListener(new RecyclerOnClickListener() {

            @Override
            public void onItemClick(AbsRecyclerAdapter adapter, View view, int position) {
                toat("click" + position);
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
        initView();
    }

    protected void initView() {

    }

    protected void toat(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }
}
