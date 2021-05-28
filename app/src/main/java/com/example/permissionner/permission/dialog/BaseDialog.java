package com.example.permissionner.permission.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

/**
 * 权限申请对话框基类
 * wp
 * 2021-5-28 16:20:15
 */
public abstract class BaseDialog extends Dialog {

    public BaseDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public abstract View getPositionButton();

    public abstract View getNegativeButton();

}
