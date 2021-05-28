package com.example.permissionner.permission;

import android.content.Context;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.example.permissionner.permission.activity.PermissionFragment;
import com.example.permissionner.permission.callback.OnPermissionResultCallback;
import com.example.permissionner.permission.callback.ApplyBeforeCallback;
import com.example.permissionner.permission.dialog.BaseDialog;

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
    private FragmentManager fragmentManager;

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
    private ApplyBeforeCallback applyBeforeCallback;


    public static Builder with(Context context) {
        return new Builder(context);
    }

    public static Builder with(Fragment fragment) {
        return new Builder(fragment);
    }

    public static Builder with(FragmentActivity fragmentActivity) {
        return new Builder(fragmentActivity);
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
        } else if (applyBeforeCallback == null) { // 如果没有权限解释框，那么直接申请权限。否则的话先调用弹出权限解释框，再申请权限
            applyPermission();
        } else {
            final BaseDialog permissionExplainDialog = applyBeforeCallback.beforeApplication(unGrantedPermissions);
            permissionExplainDialog.getPositionButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    permissionExplainDialog.dismiss();
                    applyPermission();
                }
            });

            permissionExplainDialog.getNegativeButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    permissionExplainDialog.dismiss();
                }
            });

            permissionExplainDialog.show();
        }
    }

    /**
     * 申请权限
     */
    private void applyPermission() {
        // 开始申请权限
        PermissionFragment.requestPermission(fragmentManager, unGrantedPermissions, onPermissionResultCallback);
    }


    /**
     * 构建者
     */
    public static class Builder {
        private Permissionner permissionner = new Permissionner();

        Builder(Context context) {
            permissionner.context = context;
        }

        Builder(Fragment fragment) {
            permissionner.context = fragment.getContext();
            permissionner.fragmentManager = fragment.getFragmentManager();
        }

        Builder(FragmentActivity activity) {
            permissionner.context = activity;
            permissionner.fragmentManager = activity.getSupportFragmentManager();
        }

        public Builder addPermissions(String... applyPermissions) {
            permissionner.applyPermissions = applyPermissions;
            return this;
        }

        public Builder addPreApplyCallback(ApplyBeforeCallback applyBeforeCallback) {
            permissionner.applyBeforeCallback = applyBeforeCallback;
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
