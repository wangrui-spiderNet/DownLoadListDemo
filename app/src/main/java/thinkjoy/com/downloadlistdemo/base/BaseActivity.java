package thinkjoy.com.downloadlistdemo.base;


import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import thinkjoy.com.downloadlistdemo.R;


/**
 * Created by wangrui on 2016/6/22.
 */
public abstract class BaseActivity extends android.support.v4.app.FragmentActivity implements View.OnClickListener {
    /**
     * 顶部状态栏
     */
    public RelativeLayout layout_actionbar;
    /**
     * 左侧返回按钮
     */
    public View backView;
    /**
     * 左侧返回标题
     */
    public TextView backTitle;
    /**
     * 顶部操作栏的标题显示
     */
    public TextView actionbar_title;
    /**
     * 顶部操作栏右侧文字
     */
    public TextView actionbar_right_textButton;
    /**
     * 中间内容区域的容器
     */
    public LinearLayout base_content;
    /**
     * 中间内容区域的布局
     */
    private View contentView;
    /**
     * FrameLayout
     */
    public FrameLayout framelayout_root;

    public CheckBox common_title_bar_checkbox_edit;

    protected Context mContext = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //设置沉浸式标题栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        // api15 以上打开硬件加速
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
            if (!getComponentName().getClassName().equals("cn.thinkjoy.gdchannel.base.BaseActivity")) {
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED, WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
            }
        }

        super.setContentView(R.layout.activity_base_layout);
        this.init();
    }

    private void init() {
        this.layout_actionbar = (RelativeLayout) findViewById(R.id.layout_actionbar);
        this.backView = findViewById(R.id.backView);
        this.actionbar_title = (TextView) findViewById(R.id.common_title_bar_title);
        this.actionbar_right_textButton = (TextView) findViewById(R.id.common_title_bar_right);
        this.backTitle = (TextView) findViewById(R.id.back_title);
        this.common_title_bar_checkbox_edit = (CheckBox) findViewById(R.id.common_title_bar_checkbox_edit);
        this.base_content = (LinearLayout) findViewById(R.id.base_content);
        this.framelayout_root = (FrameLayout) findViewById(R.id.framelayout_root);
        this.addListener();
    }

    private void addListener() {
        this.backView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                BaseActivity.this.finish();
            }
        });
    }

    /**
     * 设置内容区域
     *
     * @param resId 资源文件id
     */
    @Override
    public void setContentView(int resId) {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.contentView = inflater.inflate(resId, null);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        this.contentView.setLayoutParams(layoutParams);
        if (null != this.base_content) {
            this.base_content.addView(this.contentView);
        }
    }

    /**
     *
     * TODO 抽象方法，作用:必须子类实现它，已防止未统计上
     *
     * @author xszhang
     * @return
     * @since v0.0.1
     */
    protected abstract String getTAG();

    /**
     *
     * TODO 方法描述 初始化activity 中的所有控件
     *
     * @author xszhang
     * @since v0.0.1
     */
    protected abstract void initView();

    /**
     *
     * TODO 方法描述 给控件添加Listener
     *
     * @author xszhang
     * @since v0.0.1
     */
    protected abstract void setListener();

    /**
     *
     * TODO 方法描述 加载数据
     *
     * @author xszhang
     * @since v0.0.1
     */
    protected abstract void loadData();

    /**
     * onBackPressed
     */
    @Override
    public void onBackPressed() {
        backView.performClick();
        super.onBackPressed();
    }

}
