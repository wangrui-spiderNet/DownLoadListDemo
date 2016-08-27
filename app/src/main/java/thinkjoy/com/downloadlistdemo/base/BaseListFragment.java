package thinkjoy.com.downloadlistdemo.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import thinkjoy.com.downloadlistdemo.R;
import thinkjoy.com.downloadlistdemo.widget.recycle.BaseListAdapter;
import thinkjoy.com.downloadlistdemo.widget.recycle.BaseViewHolder;
import thinkjoy.com.downloadlistdemo.widget.recycle.DividerItemDecoration;
import thinkjoy.com.downloadlistdemo.widget.recycle.PullRecycler;
import thinkjoy.com.downloadlistdemo.widget.recycle.layoutmanager.ILayoutManager;
import thinkjoy.com.downloadlistdemo.widget.recycle.layoutmanager.MyLinearLayoutManager;


/**
 * Created by wangrui on 2016/6/24.
 */
public abstract class BaseListFragment<T> extends android.support.v4.app.Fragment implements PullRecycler.OnRecyclerRefreshListener,View.OnClickListener {

    protected PullRecycler recycler;
    protected BaseListAdapter adapter;
    protected ArrayList<T> mDataList;

    private static final String TAG = "BaseListFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return  super.onCreateView(inflater, container, savedInstanceState);
    }

    protected void initPullRecycleView(){
        recycler=getRecyclerView();
        setUpData();
    }

    protected void setUpData() {
        setUpAdapter();

        recycler.setOnRefreshListener(this);
        recycler.setLayoutManager(getLayoutManager());
        recycler.addItemDecoration(getItemDecoration());
        recycler.setAdapter(adapter);
    }

    public abstract PullRecycler getRecyclerView();

    protected void setUpAdapter() {
        adapter = new ListAdapter();
    }

    public abstract void setEdit(boolean isEdit);

    public abstract boolean getEdit();

    @Override
    public void onRefresh(int action) {

    }

    /**
     * 默认是LinearLayoutManager
     *
     * @return
     */
    protected ILayoutManager getLayoutManager() {
        return new MyLinearLayoutManager(getActivity().getApplicationContext());
    }

    protected RecyclerView.ItemDecoration getItemDecoration() {
        return new DividerItemDecoration(getActivity().getApplicationContext(), R.drawable.list_divider);
    }

    public class ListAdapter extends BaseListAdapter {

        @Override
        protected BaseViewHolder onCreateNormalViewHolder(ViewGroup parent, int viewType) {
            return getViewHolder(parent, viewType);
        }

        @Override
        protected int getDataCount() {
            return mDataList != null ? mDataList.size() : 0;
        }

        @Override
        protected int getDataViewType(int position) {
            return getItemType(position);
        }

        @Override
        public boolean isSectionHeader(int position) {
            return isSectionHeader(position);
        }
    }

    protected boolean isSectionHeader(int position) {
        return false;
    }

    protected int getItemType(int position) {
        return 0;
    }

    protected abstract BaseViewHolder getViewHolder(ViewGroup parent, int viewType);

}
