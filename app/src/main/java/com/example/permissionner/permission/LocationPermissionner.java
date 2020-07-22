package com.example.permissionner.permission;

import android.app.Activity;
import android.content.Intent;

import com.example.permissionner.permission.activity.LocationActivity;
import com.example.permissionner.permission.callback.ActivityResultCallback;

/**
 * 开启位置信息设置页面
 * wp
 * 2020-3-31 15:52:09
 */
public class LocationPermissionner {
    private static ActivityResultCallback callback;

    public static void openLocationOpenActivity(Activity activity, int requestCode, ActivityResultCallback activityResultCallback) {
        callback = activityResultCallback;
        ActivityResultCallbackWrapper activityResultCallbackWrapper = new ActivityResultCallbackWrapper();
        LocationActivity.startLocationOpenActivity(activity, requestCode, activityResultCallbackWrapper);
    }

    public static class ActivityResultCallbackWrapper implements ActivityResultCallback {

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            callback.onActivityResult(requestCode, resultCode, data);
        }
    }
}
