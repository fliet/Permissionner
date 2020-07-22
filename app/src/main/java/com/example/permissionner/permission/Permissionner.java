package com.example.permissionner.permission;

import android.content.Context;

import com.example.permissionner.permission.activity.PermissionActivity;
import com.example.permissionner.permission.callback.OnPermissionResultCallback;
import com.example.permissionner.permission.callback.PreApplyCallback;

/**
 * 权限申请者
 * wp
 * 2020-3-31
 * a. 判断targetSdkVersion是否超过23
 * b. 检测权限是否已经授权
 */
public class Permissionner {
    private static final String TAG = "Permissionner";
    private Context context;

    /**
     * 申请的权限
     */
    private String[] applyPermissions;
    /**
     * 未授权的权限
     */
    private String[] unGrantedPermissions;

    /**
     * 权限结果回调
     */
    private OnPermissionResultCallback onPermissionResultCallback;

    /**
     * 权限解释回调
     * 用于权限申请前，对权限的解释
     */
    private PreApplyCallback preApplyCallback;


    public static Builder with(Context context) {
        return new Builder(context);
    }

    /**
     * 开启权限申请
     */
    private void start() {
        // 过滤没有要申请的权限
        if (PermissionChecker.checkPermissionsNull(applyPermissions)) return;

        // 检测权限申请回调是否为空
        if (PermissionChecker.checkResultCallbackNull(onPermissionResultCallback)) return;

        // a. 判断targetSdkVersion是否超过23
        if (PermissionChecker.checkTargetSDKVersion(context)) return;


        // b. 检测权限是否已经授权
        unGrantedPermissions = PermissionChecker.checkPermission(context, applyPermissions);

        // 没有未授权的权限，直接调用成功回调
        if (unGrantedPermissions.length == 0) {
            onPermissionResultCallback.onAllGranted();
        } else if (preApplyCallback == null) { // 如果没有权限解释框，那么直接申请权限。否则的话先调用弹出权限解释框，再申请权限
            applyPermission();
        } else {
            preApplyCallback.beforePermission(new ApplyPermissionTask() {
                @Override
                public void run() {
                    applyPermission();
                }
            }, unGrantedPermissions);
        }
    }

    /**
     * 申请权限
     */
    private void applyPermission() {
        // 开始申请权限
        PermissionActivity.requestPermission(context, unGrantedPermissions, onPermissionResultCallback);
    }


    /**
     * 构建者
     */
    public static class Builder {
        private Permissionner permissionner = new Permissionner();

        Builder(Context context) {
            permissionner.context = context;
        }

        public Builder addPermissions(String... applyPermissions) {
            permissionner.applyPermissions = applyPermissions;
            return this;
        }

        public Builder addPreApplyCallback(PreApplyCallback preApplyCallback) {
            permissionner.preApplyCallback = preApplyCallback;
            return this;
        }

        public Builder addPermissionResultCallback(OnPermissionResultCallback onPermissionResultCallback) {
            permissionner.onPermissionResultCallback = onPermissionResultCallback;
            return this;
        }

        public void start() {
            permissionner.start();
        }
    }

    /**
     * 权限申请任务
     */
    public interface ApplyPermissionTask {
        void run();
    }
}
