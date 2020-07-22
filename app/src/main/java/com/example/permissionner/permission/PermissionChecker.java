package com.example.permissionner.permission;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.example.permissionner.permission.callback.OnPermissionResultCallback;

import java.util.ArrayList;

class PermissionChecker {
    static final String TAG = "PermissionChecker";

    static boolean checkTargetSDKVersion(Context context) {
        return context.getApplicationInfo().targetSdkVersion < 23;
    }

    /**
     * 检测 权限是否被授权
     *
     * @param permissions
     * @return 未经授权的权限
     */
    static String[] checkPermission(Context context, String[] permissions) {
        ArrayList<String> unGrantedPermissions = new ArrayList<>(0);
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(context, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                unGrantedPermissions.add(permission);
            }
        }

        return unGrantedPermissions.toArray(new String[0]);
    }

    static boolean checkResultCallbackNull(OnPermissionResultCallback onPermissionResultCallback) {
        if (onPermissionResultCallback == null){
            RuntimeException runtimeException = new RuntimeException("onPermissionResultCallback不能为空");
            StackTraceElement[] stackTraceElements = runtimeException.getStackTrace();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onPermissionResultCallback不能为空\n");
            for (StackTraceElement stackTraceElement : stackTraceElements) {
                stringBuilder.append(stackTraceElement.toString()).append("\n");
            }
            Log.e(TAG, stringBuilder.toString());

            return true;
        }

        return false;
    }

    static boolean checkPermissionsNull(String[] applyPermissions) {
        if (applyPermissions == null || applyPermissions.length == 0) {
            StackTraceElement[] stackTraceElements = new RuntimeException("未申请任何权限，请检查").getStackTrace();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("未申请任何权限，请检查\n");
            for (StackTraceElement stackTraceElement : stackTraceElements) {
                stringBuilder.append(stackTraceElement.toString()).append("\n");
            }
            Log.e(TAG, stringBuilder.toString());

            return true;
        }

        return false;
    }
}