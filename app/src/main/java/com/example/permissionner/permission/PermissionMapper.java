package com.example.permissionner.permission;

import android.Manifest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 权限对应
 * wp
 * 2021-5-28 17:00:04
 */
public class PermissionMapper {
    private HashMap<String, String> mapper = new HashMap<>();

    public PermissionMapper() {
        mapper.put(Manifest.permission.CAMERA, "相机权限");
    }

    public List<String> getPermissionList(String[] permissions) {
        ArrayList<String> list = new ArrayList<>();
        for (String permission: permissions) {
            String permissionStr = mapper.get(permission);
            if (permissionStr != null) {
                list.add(permissionStr);
            }
        }

        return list;
    }
}
