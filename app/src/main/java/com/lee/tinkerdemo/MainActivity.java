package com.lee.tinkerdemo;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.tinker.TinkerInstaller;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        
    }

    /**
     * =====================Tinker 默认配置========================
     */
    protected void onResume() {
        super.onResume();
        // 加载补丁包
        TinkerInstaller.onReceiveUpgradePatch(getApplicationContext(),
                Environment.getExternalStorageDirectory().getAbsolutePath() + "/tinker/tiner.apk");
    }

    @Override
    protected void onPause() {
        super.onPause();
        //清除补丁包
        Tinker.with(getApplicationContext()).cleanPatch();
    }
}
