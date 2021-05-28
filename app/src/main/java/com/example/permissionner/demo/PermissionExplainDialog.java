package com.example.permissionner.demo;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;

import androidx.annotation.NonNull;

import com.example.permissionner.R;
import com.example.permissionner.permission.PermissionMapper;
import com.example.permissionner.permission.dialog.BaseDialog;

/**
 * 申请权限前的解释框
 * wp
 * 2021-5-28 16:22:41
 */
public class PermissionExplainDialog extends BaseDialog {

    public PermissionExplainDialog(@NonNull Context context, String[] unGrantedPermissions) {
        super(context, R.style.DialogStyle);

        setContentView(R.layout.dialog_permission_explain);

        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(layoutParams);

        ListView listView = findViewById(R.id.list_view);
        listView.setAdapter(new ListAdapter(context, new PermissionMapper().getPermissionList(unGrantedPermissions)));
    }

    @Override
    public View getPositionButton() {
        return findViewById(R.id.tv_confirm);
    }

    @Override
    public View getNegativeButton() {
        return findViewById(R.id.tv_cancel);
    }
}
