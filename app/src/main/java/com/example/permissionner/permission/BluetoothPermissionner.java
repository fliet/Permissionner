package com.example.permissionner.permission;

import android.app.Activity;
import android.content.Intent;

import com.example.permissionner.permission.activity.BluetoothActivity;
import com.example.permissionner.permission.callback.ActivityResultCallback;

/**
 * 开启蓝牙页面
 * wp
 * 2020-3-31 15:52:09
 */
public class BluetoothPermissionner {
    private static ActivityResultCallback callback;
    public static void openBluetooth(Activity activity, int requestCode, ActivityResultCallback activityResultCallback) {
        callback = activityResultCallback;
        ActivityResultCallbackWrapper activityResultCallbackWrapper = new ActivityResultCallbackWrapper();
        BluetoothActivity.startBluetoothOpenActivity(activity, requestCode, activityResultCallbackWrapper);
    }

    public static class ActivityResultCallbackWrapper implements ActivityResultCallback {

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            callback.onActivityResult(requestCode, resultCode, data);
        }
    }
}
