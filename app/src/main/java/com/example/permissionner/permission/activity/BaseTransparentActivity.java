package com.example.permissionner.permission.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;

public class BaseTransparentActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setOnePx();
    }

    /**
     * 设置Activity为1像素
     * 解决退出Activity时，状态栏有动画的问题
     */
    private void setOnePx() {
        Window window = getWindow();
        //左上角显示
        window.setGravity(Gravity.START | Gravity.TOP);

        //设置1像素大小
        WindowManager.LayoutParams params = window.getAttributes();
        params.x = 0;
        params.y = 0;
        params.width = 1;
        params.height = 1;
        window.setAttributes(params);
    }
}
