package com.example.permissionner.permission.callback;

import com.example.permissionner.permission.Permissionner;

/**
 * 权限申请前回调
 */
public interface PreApplyCallback {
    /**
     * 权限申请前回调
     *
     * 如果有需要在权限申请前展示提示，则实现此方法
     * 并在方法中调用 ApplyPermissionTask.run()，以实现继续流程
     * @param task
     * @param unGrantedPermissions
     */
    void beforePermission(Permissionner.ApplyPermissionTask task, String[] unGrantedPermissions);
}
