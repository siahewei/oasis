package cn.com.earth.vm;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 介绍: manager viewModes
 * 作者: jacky
 * 邮箱: hewei@incoming.cn
 * 时间:  16/11/30 下午9:29
 */

public class VmRecyclerAdapterHelper implements IVmObserver {

    /**
     * viewType divider
     */
    protected static Integer DIVIDER_MIN = 3;
    protected static Integer DIVIDER = 3;
    protected ILoadingMore loadingMoreController;

    /**
     * index view mode, only support cache in constructor of adapter
     */
    protected Map<Integer, AbsViewMode> viewModes = new TreeMap<>();

    private VmRecyclerAdapter adapter;

    /**
     * constructor
     *
     * @param adapter
     * @param viewModes
     */
    public VmRecyclerAdapterHelper(VmRecyclerAdapter adapter, AbsViewMode... viewModes) {
        this.adapter = adapter;
        List<AbsViewMode> viewModeList = new ArrayList<>(Arrays.asList(viewModes));
        DIVIDER = Integer.MAX_VALUE / (DIVIDER_MIN + viewModeList.size() * 2);

        for (int i = 0; i < viewModeList.size(); i++) {
            addViewMode(viewModeList.get(i), i);
        }
    }

    /**
     * @param absViewMode
     */
    public void addViewModel(AbsViewMode absViewMode) {
        addViewMode(absViewMode, viewModes.size() - 1);
    }

    /**
     * set full span
     *
     * @param fullSpan
     */
    public void setSpan(int fullSpan) {
        Iterator tit = viewModes.entrySet().iterator();
        while (tit.hasNext()) {
            Map.Entry e = (Map.Entry) tit.next();
            ((AbsViewMode) e.getValue()).setFullSpan(fullSpan);
        }
    }

    /**
     * add view mode to save
     *
     * @param viewMode
     * @param index
     */
    private void addViewMode(AbsViewMode viewMode, int index) {
        if (viewMode instanceof ILoadingMore) {
            loadingMoreController = (ILoadingMore) viewMode;
        }
        viewModes.put(index, viewMode);
        viewMode.setListener(this);
        viewMode.setStartViewType(index * DIVIDER);
    }

    /**
     * calculate item count of the data set
     *
     * @return
     */
    public int getItemCount() {
        Iterator tit = viewModes.entrySet().iterator();
        int count = 0;
        AbsViewMode ret = null;
        while (tit.hasNext()) {
            Map.Entry e = (Map.Entry) tit.next();
            ret = (AbsViewMode) e.getValue();
            count += ret.getDataItemCount();
        }

        return count;
    }


    /**
     * get viewmode instance by viewType
     * <p>
     * loop through onece
     *
     * @param viewType
     * @return
     */
    public AbsViewMode getViewModeByViewType(int viewType) {
        // covert: viewType / DIVEDER --> index
        int index = viewType / DIVIDER;
        return viewModes.get(index);
    }

    /**
     * get viewMode by view position on recyclerView
     *
     * @param viewPos
     * @return
     */
    public AbsViewMode getViewModeByPos(int viewPos) {
        // 遍历
        Iterator tit = viewModes.entrySet().iterator();
        int count = 0;
        AbsViewMode ret = null;
        while (tit.hasNext()) {
            Map.Entry e = (Map.Entry) tit.next();
            ret = (AbsViewMode) e.getValue();
            count += ret.getDataItemCount();
            if (viewPos < count) {
                return ret;
            }
        }
        return null;
    }

    public void clear() {
        // 遍历
        Iterator tit = viewModes.entrySet().iterator();
        int count = 0;
        AbsViewMode ret = null;
        while (tit.hasNext()) {
            Map.Entry e = (Map.Entry) tit.next();
            ret = (AbsViewMode) e.getValue();
            ret.clear();

        }
    }

    /**
     * get viewMode by view position on recyclerView and get data postion in dataset
     *
     * @param viewPos
     * @param dataPos @output
     * @return
     */
    public AbsViewMode getViewModeByPos(int viewPos, int[] dataPos) {
        Log.i("jacky", "viewpos:" + viewPos);
        if (viewModes.isEmpty()) {
            return null;
        }

        // loop through
        dataPos[0] = 0;
        Iterator tit = viewModes.entrySet().iterator();
        int count = 0;
        int preCount = 0;
        AbsViewMode ret = null;
        while (tit.hasNext()) {
            Map.Entry e = (Map.Entry) tit.next();
            ret = (AbsViewMode) e.getValue();
            count += ret.getDataItemCount();

            if (viewPos < count && viewPos >= preCount) {
                dataPos[0] = viewPos - preCount;
                if (dataPos.length == 2) {
                    dataPos[1] = preCount;
                }

                return ret;
            }
            if (viewPos < count) {
                return ret;
            }
            preCount = count;
        }
        return null;
    }

    /**
     * get viewType in viewMode
     *
     * @param viewType
     * @return
     */
    public int getViewTypeInViewMode(int viewType) {
        return viewType % DIVIDER;
    }

    /**
     * convert viewPosition of the recyclerView to data position of dataset
     *
     * @param viewPos
     * @return
     */
    public int getDataPos(int viewPos) {
        Iterator tit = viewModes.entrySet().iterator();
        int count = 0;
        int preCount = 0;
        AbsViewMode ret = null;

        while (tit.hasNext()) {
            Map.Entry e = (Map.Entry) tit.next();
            ret = (AbsViewMode) e.getValue();
            count += ret.getDataItemCount();
            if (viewPos < count && viewPos >= preCount) {
                return viewPos - preCount;
            }
            preCount = count;
        }
        return -1;
    }

    @Override
    public void notifyItemChanged(int pos) {
        adapter.notifyItemChanged(pos);
    }

    @Override
    public void notifyItemInserted(int pos) {
        adapter.notifyItemInserted(pos);

    }

    @Override
    public void notifyItemRemoved(int pos) {
        adapter.notifyItemRemoved(pos);
    }

    @Override
    public void notifyItemRangeChanged(int startPos, int endPos) {
        adapter.notifyItemRangeChanged(startPos, endPos);
    }

    @Override
    public void notifyItemRangeInserted(int startPos, int endPos) {
        adapter.notifyItemRangeInserted(startPos, endPos);
    }

    @Override
    public void notifyItemRangeRemoved(int statPos, int endPos) {
        adapter.notifyItemRangeRemoved(statPos, endPos);
    }

    @Override
    public void notifyDataChanged() {
        adapter.notifyDataSetChanged();
    }

    public int getLastStartType() {
        if (viewModes.isEmpty()) {
            return 0;
        } else {
            return DIVIDER * (viewModes.size() - 1);
        }
    }

    public ILoadingMore getLoadingMoreController() {
        return loadingMoreController;
    }
}
