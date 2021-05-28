package com.example.permissionner.permission.callback;

import com.example.permissionner.permission.Permissionner;
import com.example.permissionner.permission.dialog.BaseDialog;

/**
 * 权限申请前回调
 */
public interface ApplyBeforeCallback {
    /**
     * 权限申请前回调
     *
     * 如果有需要在权限申请前展示提示，则实现此方法
     * 并在方法中调用 ApplyPermissionTask.run()，以实现继续流程
     * @param unGrantedPermissions
     */
    BaseDialog beforeApplication(String[] unGrantedPermissions);
}
