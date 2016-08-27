package thinkjoy.com.downloadlistdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class MainActivity extends Activity implements View.OnClickListener{
    private RelativeLayout gd_mine_collection_layout,gd_mine_download_layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_me);
        gd_mine_collection_layout=(RelativeLayout)findViewById(R.id.gd_mine_collection_layout);
        gd_mine_download_layout=(RelativeLayout)findViewById(R.id.gd_mine_download_layout);
        setListener();
    }

    private void setListener(){
        gd_mine_collection_layout.setOnClickListener(this);
        gd_mine_download_layout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if(v==gd_mine_collection_layout){
            Intent intent=new Intent(this, MineCollectionListActivity.class);
            startActivity(intent);
        }else if(v==gd_mine_download_layout){
            Intent intent=new Intent(this, MineDownLoadActivity.class);
            startActivity(intent);
        }
    }
}
