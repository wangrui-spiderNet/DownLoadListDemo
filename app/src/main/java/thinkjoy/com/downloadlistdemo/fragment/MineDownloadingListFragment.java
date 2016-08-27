package thinkjoy.com.downloadlistdemo.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;

import thinkjoy.com.downloadlistdemo.R;
import thinkjoy.com.downloadlistdemo.base.BaseListFragment;
import thinkjoy.com.downloadlistdemo.bean.MaterialDownInfoBean;
import thinkjoy.com.downloadlistdemo.widget.recycle.BaseViewHolder;
import thinkjoy.com.downloadlistdemo.widget.recycle.PullRecycler;

/**
 * 正在下载
 * Created by wangrui on 2016/6/24.
 */
public class MineDownloadingListFragment extends BaseListFragment<MaterialDownInfoBean> {

    protected View view;
    protected RelativeLayout gd_mine_download_bottom_info_layout;
    protected TextView gd_mine_download_sdcard_size;
    protected ProgressBar gd_mine_download_progress;
    protected LinearLayout gd_mine_download_bottom_switch_layout;
    protected TextView gd_mine_download_choose_all, mine_download_delete;
    protected HashSet<MaterialDownInfoBean> deleteDownLoadingDataList;

    private static MineDownloadingListFragment downloadedFragment;
    private int page=1;
    private boolean isEdit=false;
    private static final String TAG="MineDownloadedListFragment";

    public static MineDownloadingListFragment newInstance(Bundle bundle){
        downloadedFragment=new MineDownloadingListFragment();

        if(bundle!=null){
            downloadedFragment.setArguments(bundle);
        }

        return  downloadedFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(view==null){
            view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_baselist, null);
            recycler = (PullRecycler) view.findViewById(R.id.gd_base_fragment_pullrecycler);
            gd_mine_download_bottom_info_layout = (RelativeLayout) view.findViewById(R.id.gd_mine_download_bottom_info_layout);
            gd_mine_download_sdcard_size = (TextView) view.findViewById(R.id.gd_mine_download_sdcard_size);
            gd_mine_download_progress = (ProgressBar) view.findViewById(R.id.gd_mine_download_progress);
            gd_mine_download_bottom_switch_layout = (LinearLayout) view.findViewById(R.id.gd_mine_download_bottom_switch_layout);
            gd_mine_download_choose_all = (TextView) view.findViewById(R.id.gd_mine_download_choose_all);
            mine_download_delete = (TextView) view.findViewById(R.id.gd_mine_download_delete);
            gd_mine_download_choose_all.setOnClickListener(this);

        }else{
            ViewGroup viewGroup=(ViewGroup) view.getParent();
            if(viewGroup!=null){
                viewGroup.removeView(view);
            }
        }

        if (mDataList == null) {
            mDataList = new ArrayList<MaterialDownInfoBean>();
        }

        if(deleteDownLoadingDataList ==null){
            deleteDownLoadingDataList =new HashSet<MaterialDownInfoBean>();
        }

        /**
         * 假数据
         */
        for (int i = 0; i < 15; i++) {
            MaterialDownInfoBean materialDownInfo = new MaterialDownInfoBean();
            materialDownInfo.setDocUrl("http://www.baidu.cn--" + i);
            mDataList.add(materialDownInfo);
        }

        initPullRecycleView();

        return view;
    }

    @Override
    public PullRecycler getRecyclerView() {
        return (PullRecycler) view.findViewById(R.id.gd_base_fragment_pullrecycler);
    }

    @Override
    public void setEdit(boolean isEdit) {
        this.isEdit=isEdit;

        if(isEdit){
            if(gd_mine_download_bottom_switch_layout!=null&&gd_mine_download_bottom_info_layout!=null){
                gd_mine_download_bottom_switch_layout.setVisibility(View.VISIBLE);
                gd_mine_download_bottom_info_layout.setVisibility(View.GONE);
            }

        }else{
            if(gd_mine_download_bottom_switch_layout!=null&&gd_mine_download_bottom_info_layout!=null){
                gd_mine_download_bottom_switch_layout.setVisibility(View.GONE);
                gd_mine_download_bottom_info_layout.setVisibility(View.VISIBLE);
            }
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean getEdit() {
        return isEdit;
    }

    @Override
    public void onRefresh(int action) {
        super.onRefresh(action);

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
        for (int i = 0; i < 15; i++) {
            MaterialDownInfoBean materialDownInfo = new MaterialDownInfoBean();
            materialDownInfo.setDocUrl("http://www.baidu.cn--" + i);
            mDataList.add(materialDownInfo);
        }

        recycler.enableLoadMore(true);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        },2000);

        recycler.onRefreshCompleted();
    }

    @Override
    public void onClick(View v) {
        if(v== mine_download_delete){

            Toast.makeText(getActivity(),"取消收藏",Toast.LENGTH_SHORT).show();
        }else if(v==gd_mine_download_choose_all){
            deleteDownLoadingDataList.addAll(mDataList);
            for(int i=0;i<mDataList.size();i++){
                mDataList.get(i).setSelected(true);
            }
            mine_download_delete.setText(getString(R.string.common_cancel_collect)+"("+deleteDownLoadingDataList.size()+")");
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected BaseViewHolder getViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_collection_downloading_list_layout, parent, false);
        return new ViewHolder(view);
    }

    class ViewHolder extends BaseViewHolder {

        CheckBox mine_collection_downloading_checkbox;
        ImageView mine_collection_downloading_image;
        TextView mine_collection_downloading_doc_name;
        ProgressBar mine_collection_downloading_progress;
        ImageView mine_collection_download_status_image;
        TextView mine_collection_downloading_doc_size;
        TextView mine_collection_downloading_percent;

        public ViewHolder(View itemView) {
            super(itemView);
            mine_collection_downloading_checkbox = (CheckBox) itemView.findViewById(R.id.mine_collection_downloading_checkbox);
            mine_collection_downloading_image = (ImageView) itemView.findViewById(R.id.mine_collection_downloading_image);
            mine_collection_downloading_doc_name = (TextView) itemView.findViewById(R.id.mine_collection_downloading_doc_name);
            mine_collection_downloading_progress = (ProgressBar) itemView.findViewById(R.id.mine_collection_downloading_progress);
            mine_collection_download_status_image = (ImageView) itemView.findViewById(R.id.mine_collection_download_status_image);
            mine_collection_downloading_doc_size = (TextView) itemView.findViewById(R.id.mine_collection_downloading_doc_size);
            mine_collection_downloading_percent = (TextView) itemView.findViewById(R.id.mine_collection_downloading_percent);
        }

        @Override
        public void onBindViewHolder(final int position) {
            mine_collection_downloading_doc_name.setText(mDataList.get(position).getDocName());
            mine_collection_download_status_image.setVisibility(View.VISIBLE);

            if(isEdit){
                mine_collection_downloading_checkbox.setVisibility(View.VISIBLE);
            }else{
                mine_collection_downloading_checkbox.setVisibility(View.GONE);
            }

            mine_collection_downloading_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        deleteDownLoadingDataList.add(mDataList.get(position));
                        mDataList.get(position).setSelected(true);
                    }else{
                        deleteDownLoadingDataList.remove(mDataList.get(position));
                        mDataList.get(position).setSelected(false);
                    }
                    mine_download_delete.setText(getString(R.string.common_delete)+"("+deleteDownLoadingDataList.size()+")");
                }
            });

            mine_download_delete.setText(getString(R.string.common_delete)+"("+deleteDownLoadingDataList.size()+")");
            mine_collection_downloading_checkbox.setChecked(mDataList.get(position).isSelected());
        }

        @Override
        public void onItemClick(View view, int position) {

        }

    }
}
