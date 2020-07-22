package com.example.permissionner.permission.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;

import com.example.permissionner.permission.LocationPermissionner;
import com.example.permissionner.permission.callback.ActivityResultCallback;

public class LocationActivity extends BaseTransparentActivity {
    private static final String ACTIVITY_RESULT_CALLBACK = "activityResultCallback";
    private static final String REQUEST_CODE = "requestCode";

    private ActivityResultCallback activityResultCallback;
    private int requestCode;

    /**
     * 跳转到开启蓝牙页面
     */
    public static void startLocationOpenActivity(Activity activity, int requestCode, LocationPermissionner.ActivityResultCallbackWrapper activityResultCallbackWrapper) {
        Intent intent = new Intent(activity, LocationActivity.class);

        intent.putExtra(REQUEST_CODE, requestCode);
        intent.putExtra(ACTIVITY_RESULT_CALLBACK, activityResultCallbackWrapper);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityResultCallback = (ActivityResultCallback) getIntent().getSerializableExtra(ACTIVITY_RESULT_CALLBACK);
        requestCode = getIntent().getIntExtra(REQUEST_CODE, 0);

        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (activityResultCallback != null)
            activityResultCallback.onActivityResult(requestCode, resultCode, data);

        finish();
    }
}
