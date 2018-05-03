package com.lee.tinkerdemo;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.multidex.MultiDex;

import com.morgoo.droidplugin.PluginHelper;
import com.tencent.tinker.anno.DefaultLifeCycle;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.loader.app.DefaultApplicationLike;
import com.tencent.tinker.loader.shareutil.ShareConstants;

/**
 * @类名: ${type_name}
 * @功能描述:
 * @作者: ${user}
 * @时间: ${date}
 * @最后修改者:
 * @最后修改内容:
 */
@SuppressWarnings("unused")
@DefaultLifeCycle(application = "com.lee.tinkerdemo.SimpleTinkerInApplication",
        flags = ShareConstants.TINKER_ENABLE_ALL,
        loadVerifyFlag = false)
public class SimpleTinkerInApplicationLike extends DefaultApplicationLike {
    public SimpleTinkerInApplicationLike(Application application, int tinkerFlags,
                                         boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime, 
                                         long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, 
                applicationStartMillisTime, tinkerResultIntent);
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void onBaseContextAttached(Context base) {

        this.base = base;
        PluginHelper.getInstance().applicationAttachBaseContext(base);
        super.onBaseContextAttached(base);
        
        // 其原理是分包架构，所以在加载初要加载其余的分包
        MultiDex.install(base);

        TinkerInstaller.install(this);
        
    }
    
    private Context base;
    public static SimpleTinkerInApplicationLike sApp;

    @Override
    public void onCreate() {
        super.onCreate();
        sApp = this;

        //这里必须在super.onCreate方法之后，顺序不能变
        PluginHelper.getInstance().applicationOnCreate(getBaseContext(base));
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void registerActivityLifecycleCallbacks(Application.ActivityLifecycleCallbacks callback) {
        // 生命周期，默认配置
        getApplication().registerActivityLifecycleCallbacks(callback);
    }

    public static SimpleTinkerInApplicationLike getApp(){
        return sApp;
    }

}
//jhfghfh