package com.example.permissionner.permission.activity;

import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.example.permissionner.permission.callback.OnPermissionResultCallback;

import java.util.ArrayList;

/**
 * wp
 */
public class PermissionFragment extends Fragment {
    private static final String APPLY_PERMISSIONS = "apply_permissions";
    private static OnPermissionResultCallback onPermissionResultCallback;
    private final int REQUEST_PERMISSION_CODE = 1;

    /**
     * 本次申请的权限
     */
    private String[] currApplyPermissions;


    public static void requestPermission(FragmentManager fragmentManager, String[] currApplyPermissions, OnPermissionResultCallback onPermissionResultCallback) {
        PermissionFragment.onPermissionResultCallback = onPermissionResultCallback;

        PermissionFragment permissionFragment = new PermissionFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArray(APPLY_PERMISSIONS, currApplyPermissions);
        permissionFragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(permissionFragment, "PermissionFragment");
        fragmentTransaction.addToBackStack("");
        fragmentTransaction.commit();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getArguments() != null) {
            currApplyPermissions = getArguments().getStringArray(APPLY_PERMISSIONS);
            if (currApplyPermissions != null)
                requestPermissions(currApplyPermissions, REQUEST_PERMISSION_CODE);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        int size = permissions.length;

        // 分类数据
        ArrayList<String> grantedPermissions = new ArrayList<>();
        ArrayList<String> deniedPermissions = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                grantedPermissions.add(permissions[i]);
            } else {
                deniedPermissions.add(permissions[i]);
            }
        }

        if (grantedPermissions.size() > 0) {
            onPermissionResultCallback.onGranted(grantedPermissions.toArray(new String[0]));

            if (grantedPermissions.size() == currApplyPermissions.length)
                onPermissionResultCallback.onAllGranted();
        }
        if (deniedPermissions.size() > 0) {
            onPermissionResultCallback.onDenied(deniedPermissions.toArray(new String[0]));
        }


        if (getFragmentManager() != null) {
            getFragmentManager().popBackStack();
        }
    }
}
