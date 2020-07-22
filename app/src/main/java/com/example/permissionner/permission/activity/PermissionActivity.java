package com.example.permissionner.permission.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.example.permissionner.permission.callback.OnPermissionResultCallback;

import java.util.ArrayList;

public class PermissionActivity extends BaseTransparentActivity {
    private static final String APPLY_PERMISSIONS = "apply_permissions";
    private static OnPermissionResultCallback onPermissionResultCallback;

    /**
     * 本次申请的权限
     */
    private String[] currApplyPermissions;


    public static void requestPermission(Context context, String[] currApplyPermissions, OnPermissionResultCallback onPermissionResultCallback) {
        PermissionActivity.onPermissionResultCallback = onPermissionResultCallback;

        Intent intent = new Intent(context, PermissionActivity.class);
        intent.putExtra(APPLY_PERMISSIONS, currApplyPermissions);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        currApplyPermissions = getIntent().getStringArrayExtra(APPLY_PERMISSIONS);

        ActivityCompat.requestPermissions(this, currApplyPermissions, 1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        int size = permissions.length;

        // 分类数据
        ArrayList<String> grantedPermissions = new ArrayList<>();
        ArrayList<String> deniedPermissions = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                grantedPermissions.add(permissions[i]);
            } else {
                deniedPermissions.add(permissions[i]);
            }
        }

        if (grantedPermissions.size() > 0) {
            onPermissionResultCallback.onGranted(grantedPermissions.toArray(new String[0]));

            if (grantedPermissions.size() == currApplyPermissions.length)
                onPermissionResultCallback.onAllGranted();
        }
        if (deniedPermissions.size() > 0)
            onPermissionResultCallback.onDenied(deniedPermissions.toArray(new String[0]));

        finish();
    }
}
