package com.example.permissionner.permission.callback;

import android.content.Intent;

import java.io.Serializable;

/**
 * Activity Result回调
 * wp
 * 2020-3-31 15:36:47
 */
public interface ActivityResultCallback extends Serializable {
    void onActivityResult(int requestCode, int resultCode, Intent data);
}
