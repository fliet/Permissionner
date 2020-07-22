package com.example.permissionner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.permissionner.permission.BluetoothPermissionner;
import com.example.permissionner.permission.LocationPermissionner;
import com.example.permissionner.permission.Permissionner;
import com.example.permissionner.permission.callback.ActivityResultCallback;
import com.example.permissionner.permission.callback.OnPermissionResultCallback;
import com.example.permissionner.permission.callback.PreApplyCallback;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button takePhotoBtn = findViewById(R.id.take_photo_btn);
        Button openBluetoothBtn = findViewById(R.id.open_bluetooth_btn);
        Button openLocationBtn = findViewById(R.id.open_location_btn);

        takePhotoBtn.setOnClickListener(this);
        openBluetoothBtn.setOnClickListener(this);
        openLocationBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.take_photo_btn:
                Permissionner
                        .with(this)
                        .addPermissions(Manifest.permission.CAMERA) // 要申请的权限
                        .addPreApplyCallback(new PreApplyCallback() {  // 权限申请前的回调
                            @Override
                            public void beforePermission(Permissionner.ApplyPermissionTask applyPermissionTask, String[] unGrantedPermissions) {
                                showPermissionExplainDialog(MainActivity.this, applyPermissionTask, unGrantedPermissions);
                            }
                        })
                        .addPermissionResultCallback(new OnPermissionResultCallback() { // 结果回调
                            @Override
                            public void onAllGranted() {
                                takePhoto();
                                Toast.makeText(MainActivity.this, "已授予所有权限", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onGranted(String[] grantedPermissions) {
                                Toast.makeText(MainActivity.this, "本次授予的权限：" + Arrays.toString(grantedPermissions), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onDenied(String[] deniedPermissions) {
                                showDeniedExplainDialog(MainActivity.this, deniedPermissions);
                            }
                        })
                        .start();
                break;
            case R.id.open_bluetooth_btn:
                BluetoothPermissionner.openBluetooth(this, 1, new ActivityResultCallback() {
                    @Override
                    public void onActivityResult(int requestCode, int resultCode, Intent data) {
                        if (requestCode == 1 && resultCode == RESULT_OK) {
                            Toast.makeText(MainActivity.this, "蓝牙开启成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "蓝牙开启失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            case R.id.open_location_btn:
                Permissionner
                        .with(this)
                        .addPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
                        .addPermissionResultCallback(new OnPermissionResultCallback() {
                            @Override
                            public void onGranted(String[] grantedPermissions) {

                            }

                            @Override
                            public void onAllGranted() {
                                Toast.makeText(MainActivity.this, "定位权限已打开", Toast.LENGTH_SHORT).show();

                                LocationPermissionner.openLocationOpenActivity(MainActivity.this, 2, new ActivityResultCallback() {
                                    @Override
                                    public void onActivityResult(int requestCode, int resultCode, Intent data) {
                                        if (requestCode == 2 && resultCode == RESULT_OK) {
                                            Toast.makeText(MainActivity.this, "定位服务已打开", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(MainActivity.this, "定位服务未打开", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }

                            @Override
                            public void onDenied(String[] deniedPermissions) {
                                Toast.makeText(MainActivity.this, "定位权限未打开", Toast.LENGTH_SHORT).show();

                            }
                        })
                        .start();
                break;
            default:
                break;
        }
    }

    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivity(intent);
    }

    private void showPermissionExplainDialog(final Context context, final Permissionner.ApplyPermissionTask applyPermissionTask, String[] unGrantedPermissions) {
        new AlertDialog.Builder(context)
                .setTitle("申请权限前，权限解释框")
                .setMessage("申请的权限有：" + Arrays.toString(unGrantedPermissions))
                .setPositiveButton("知道了", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        applyPermissionTask.run();
                    }
                })
                .show();
    }

    private void showDeniedExplainDialog(final Context context, String[] deniedPermissions) {
        //解释原因，并且引导用户至设置页手动授权

        new AlertDialog.Builder(context)
                .setTitle("权限解释")
                .setMessage(Arrays.toString(deniedPermissions) + "没有被授权")
                .setPositiveButton("去授权", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //引导用户至设置页手动授权
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", context.getApplicationContext().getPackageName(), null);
                        intent.setData(uri);
                        context.startActivity(intent);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //引导用户手动授权，权限请求失败
                        Toast.makeText(context, "请到“手机设置”页面进行手动设置", Toast.LENGTH_SHORT).show();
                    }
                })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        //引导用户手动授权，权限请求失败
                        Toast.makeText(context, "请到“手机设置”页面进行手动设置", Toast.LENGTH_SHORT).show();
                    }
                }).show();

        /*
               如果没有选择“禁止再次弹出”的话，shouldShowRequestPermissionRationale()会返回true
               这样，我们可以在用户点击禁止后，弹出对话框，引导用户去设置里打开权限

               但是如果我们想要只要是拒绝了权限（包括勾选“禁止再次弹出”的拒绝），就引用用户去设置
               那么就没有必要通过shouldShowRequestPermissionRationale()来判断
          */
        /*if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context,
                Manifest.permission.CAMERA)) {
        }*/
    }
}
