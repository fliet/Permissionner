package com.example.permissionner.permission.callback;

/**
 * 权限申请结果回调
 */
public interface OnPermissionResultCallback {
    /**
     * 申请的权限全部通过的回调
     */
    void onAllGranted();

    /**
     * 本次申请通过的权限的回调
     *
     * @param grantedPermissions 通过的权限
     */
    void onGranted(String[] grantedPermissions);

    /**
     * 本次申请拒绝的权限的回调
     *
     * @param deniedPermissions 拒绝的权限
     */
    void onDenied(String[] deniedPermissions);
}
