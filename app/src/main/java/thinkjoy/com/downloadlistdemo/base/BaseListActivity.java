package thinkjoy.com.downloadlistdemo.base;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
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
 * 封装带有recycleView控件的基本页面
 */
public abstract class BaseListActivity<T> extends BaseActivity implements PullRecycler.OnRecyclerRefreshListener {
    protected BaseListAdapter adapter;
    protected ArrayList<T> mDataList;
    protected PullRecycler recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_baselist);
    }

    protected void initPullRecycleView(){
        setUpView();
        setUpData();
    }

    protected void setUpView() {
        recycler = getRecyclerView();
    }

    public abstract PullRecycler getRecyclerView();

    @Override
    public void onClick(View v) {

    }

    protected void setUpData() {
        setUpAdapter();
        recycler.setOnRefreshListener(this);
        recycler.setLayoutManager(getLayoutManager());
        recycler.addItemDecoration(getItemDecoration());
        recycler.setAdapter(adapter);
    }

    protected void setUpAdapter() {
        adapter = new ListAdapter();
    }

    public abstract void showItemCheckboxEdit(boolean isEdit);

    /**
     * 默认是LinearLayoutManager
     *
     * @return
     */
    protected ILayoutManager getLayoutManager() {
        return new MyLinearLayoutManager(getApplicationContext());
    }

    protected RecyclerView.ItemDecoration getItemDecoration() {
        return new DividerItemDecoration(getApplicationContext(), R.drawable.list_divider);
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
