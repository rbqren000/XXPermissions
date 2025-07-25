package com.hjq.permissions.start;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import com.hjq.permissions.tools.PermissionUtils;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/XXPermissions
 *    time   : 2025/05/20
 *    desc   : startActivity 委托 Context 实现
 */
public final class StartActivityDelegateByContext implements IStartActivityDelegate {

    @NonNull
    private final Context mContext;

    public StartActivityDelegateByContext(@NonNull Context context) {
        mContext = context;
    }

    @Override
    public void startActivity(Intent intent) {
        if (intent == null) {
            return;
        }
        // https://developer.android.google.cn/about/versions/pie/android-9.0-changes-all?hl=zh-cn#fant-required
        if (!(mContext instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        mContext.startActivity(intent);
    }

    @Override
    public void startActivityForResult(Intent intent, @IntRange(from = 1, to = 65535) int requestCode) {
        if (intent == null) {
            return;
        }
        Activity activity = PermissionUtils.findActivity(mContext);
        if (activity != null) {
            activity.startActivityForResult(intent, requestCode);
            return;
        }
        // https://developer.android.google.cn/about/versions/pie/android-9.0-changes-all?hl=zh-cn#fant-required
        if (!(mContext instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        startActivity(intent);
    }
}