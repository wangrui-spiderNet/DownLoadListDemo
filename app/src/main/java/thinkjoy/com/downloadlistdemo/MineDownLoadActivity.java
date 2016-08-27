package thinkjoy.com.downloadlistdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioGroup;

import thinkjoy.com.downloadlistdemo.base.BaseActivity;
import thinkjoy.com.downloadlistdemo.base.BaseListFragment;
import thinkjoy.com.downloadlistdemo.bean.MaterialDownInfoBean;
import thinkjoy.com.downloadlistdemo.fragment.MineDownloadedListFragment;
import thinkjoy.com.downloadlistdemo.fragment.MineDownloadingListFragment;

/**
 * 我的下载
 * Created by wangrui on 2016/6/23.
 */
public class MineDownLoadActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener {

    private RadioGroup gd_mine_download_rg;

    private FragmentManager mFragmentManager;
    private Fragment fragment;
    private BaseListFragment<MaterialDownInfoBean> downloadedFragment;
    private BaseListFragment<MaterialDownInfoBean> downloadingFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_download);
        initView();
        setListener();
    }


    @Override
    protected String getTAG() {
        return null;
    }

    @Override
    protected void initView() {
        mFragmentManager = getSupportFragmentManager();
        gd_mine_download_rg = (RadioGroup) findViewById(R.id.gd_mine_download_rg);

        common_title_bar_checkbox_edit.setVisibility(View.VISIBLE);
        actionbar_title.setText(R.string.mine_download);
        backView.setVisibility(View.VISIBLE);

        fragment = MineDownloadedListFragment.newInstance(null);
        initFragment();
    }


    @Override
    protected void loadData() {

    }

    @Override
    protected void setListener() {
        common_title_bar_checkbox_edit.setOnCheckedChangeListener(this);
        gd_mine_download_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.gd_mine_downloaded_rb:
                        if (downloadedFragment == null) {
                            downloadedFragment = MineDownloadedListFragment.newInstance(null);
                        }
                        fragment = downloadedFragment;

                        initFragment();

                        reSetEditButton(downloadedFragment.getEdit());

                        break;

                    case R.id.gd_mine_downloading_rb:
                        if (downloadingFragment == null) {
                            downloadingFragment = MineDownloadingListFragment.newInstance(null);
                        }
                        fragment = downloadingFragment;
                        initFragment();

                        reSetEditButton(downloadingFragment.getEdit());

                        break;
                }
            }
        });
    }

    private void reSetEditButton(boolean isEdit) {
        common_title_bar_checkbox_edit.setOnCheckedChangeListener(null);
        common_title_bar_checkbox_edit.setChecked(isEdit);
        common_title_bar_checkbox_edit.setOnCheckedChangeListener(this);

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        if (fragment instanceof MineDownloadedListFragment) {
            ((MineDownloadedListFragment) fragment).setEdit(isChecked);
        } else if (fragment instanceof MineDownloadingListFragment) {
            ((MineDownloadingListFragment) fragment).setEdit(isChecked);
        }
    }

    @Override
    public void onClick(View v) {

    }

    private void initFragment() {
        if (fragment == null) {
            mFragmentManager.beginTransaction().add(R.id.gd_mine_download_container, fragment).commit();
        } else {
            mFragmentManager.beginTransaction().replace(R.id.gd_mine_download_container, fragment).commit();
        }
    }

}
