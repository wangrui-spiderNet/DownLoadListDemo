package thinkjoy.com.downloadlistdemo;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import thinkjoy.com.downloadlistdemo.base.BaseListActivity;
import thinkjoy.com.downloadlistdemo.bean.MaterialDownInfoBean;
import thinkjoy.com.downloadlistdemo.widget.recycle.BaseViewHolder;
import thinkjoy.com.downloadlistdemo.widget.recycle.PullRecycler;

/**
 * 我的收藏
 * Created by wangrui on 2016/6/27.
 */
public class MineCollectionListActivity extends BaseListActivity<MaterialDownInfoBean> {

    private LinearLayout gd_mine_collect_bottom;
    private TextView gd_mine_collection_choose_all;
    private TextView gd_mine_collection_cancel_collect;

    private int random;
    private int page = 1;
    private boolean isEdit;
    protected HashSet<MaterialDownInfoBean> cancelDataList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baselist);
        initView();
        initPullRecycleView();
        setListener();
    }

    @Override
    public PullRecycler getRecyclerView() {

        return (PullRecycler) findViewById(R.id.gd_base_activty_pullrecycler);
    }

    @Override
    protected void setUpData() {
        super.setUpData();
        recycler.setRefreshing();
    }

    @Override
    protected BaseViewHolder getViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_doc, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void showItemCheckboxEdit(boolean isEdit) {
        this.isEdit=isEdit;
        adapter.notifyDataSetChanged();
    }

    @Override
    protected RecyclerView.ItemDecoration getItemDecoration() {
        if (random == 0) {
            return super.getItemDecoration();
        } else {
            return null;
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        if(v==gd_mine_collection_cancel_collect){
            Iterator<MaterialDownInfoBean>  iterator=cancelDataList.iterator();

            while (iterator.hasNext()){
                MaterialDownInfoBean infoBean=iterator.next();
                if(mDataList.contains(infoBean)){
                    mDataList.remove(infoBean);
                }
            }

            cancelDataList.clear();
            adapter.notifyDataSetChanged();


        }else if(v==gd_mine_collection_choose_all){

            for(int i=0;i<mDataList.size();i++){
                mDataList.get(i).setSelected(true);
            }
            cancelDataList.addAll(mDataList);
            gd_mine_collection_cancel_collect.setText(getString(R.string.common_cancel_collect)+"("+cancelDataList.size()+")");
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRefresh(final int action) {

        if(mDataList==null){
            mDataList=new ArrayList<MaterialDownInfoBean>();
        }

        if(cancelDataList==null){
            cancelDataList=new HashSet<MaterialDownInfoBean>();
        }

        if (action == PullRecycler.ACTION_PULL_TO_REFRESH) {
            page = 1;
        }

        /**
         * 请求网络数据
         */

        if (action == PullRecycler.ACTION_PULL_TO_REFRESH) {
            mDataList.clear();
        }
        /**
         * 假数据
         */
        for (int i = 0; i < 10; i++) {
            MaterialDownInfoBean documentBean = new MaterialDownInfoBean();
            documentBean.setDocUrl("http://www.baidu.cn--" + i);
            mDataList.add(documentBean);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        },2000);
        recycler.onRefreshCompleted();

    }

    @Override
    protected String getTAG() {
        return null;
    }

    @Override
    protected void initView() {
        gd_mine_collect_bottom = (LinearLayout) findViewById(R.id.gd_mine_collect_bottom);
        gd_mine_collection_choose_all = (TextView) findViewById(R.id.gd_mine_collection_choose_all);
        gd_mine_collection_cancel_collect = (TextView) findViewById(R.id.gd_mine_collection_cancel_collect);
        common_title_bar_checkbox_edit = (CheckBox) findViewById(R.id.common_title_bar_checkbox_edit);

        backView.setVisibility(View.VISIBLE);
        common_title_bar_checkbox_edit.setVisibility(View.VISIBLE);
        actionbar_title.setText(R.string.common_mine_collection);
    }

    @Override
    protected void setListener() {
        gd_mine_collection_cancel_collect.setOnClickListener(this);
        gd_mine_collection_choose_all.setOnClickListener(this);

        common_title_bar_checkbox_edit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                showItemCheckboxEdit(isChecked);
                if (isChecked) {
                    gd_mine_collect_bottom.setVisibility(View.VISIBLE);
                } else {
                    gd_mine_collect_bottom.setVisibility(View.GONE);
                    cancelDataList.clear();
                    gd_mine_collection_cancel_collect.setText(getString(R.string.common_cancel_collect) + "(" + cancelDataList.size() + ")");
                }
            }
        });
    }

    @Override
    protected void loadData() {

    }

    class ViewHolder extends BaseViewHolder {

        TextView gd_mine_collection_file_name;
        TextView gd_mine_collection_file_size;
        CheckBox gd_mine_collection_checkbox;


        public ViewHolder(View itemView) {
            super(itemView);
            gd_mine_collection_checkbox = (CheckBox) itemView.findViewById(R.id.gd_mine_collection_checkbox);
            gd_mine_collection_file_name = (TextView) itemView.findViewById(R.id.gd_mine_collection_file_name);
            gd_mine_collection_file_size = (TextView) itemView.findViewById(R.id.gd_mine_collection_file_size);
        }

        @Override
        public void onBindViewHolder(final int position) {
            gd_mine_collection_file_name.setText(mDataList.get(position).getDocUrl());
            if(isEdit){
                gd_mine_collection_checkbox.setVisibility(View.VISIBLE);
            }else{
                gd_mine_collection_checkbox.setVisibility(View.GONE);
            }

            gd_mine_collection_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if(isChecked){
                        cancelDataList.add(mDataList.get(position));
                        mDataList.get(position).setSelected(true);
                    }else{
                        cancelDataList.remove(mDataList.get(position));
                        mDataList.get(position).setSelected(false);
                    }

                    gd_mine_collection_cancel_collect.setText(getString(R.string.common_cancel_collect)+"("+cancelDataList.size()+")");

                }
            });

            gd_mine_collection_cancel_collect.setText(getString(R.string.common_delete)+"("+cancelDataList.size()+")");
            gd_mine_collection_checkbox.setChecked(mDataList.get(position).isSelected());
        }

        @Override
        public void onItemClick(View view, int position) {

        }

    }
}
