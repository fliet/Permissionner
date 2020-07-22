# Permissionner
动态权限申请库  

包含权限申请前解释框回调、蓝牙权限申请（打开蓝牙）、定位权限申请（打开定位服务）等功能  
可一次申请多个权限

## 使用方法
### 注册Activity
清单文件中注册要使用的Activity

	<activity
            android:name=".permission.activity.BluetoothActivity"
            android:launchMode="singleTop"
            android:theme="@style/TransparentStyle" />
	<activity
	    android:name=".permission.activity.LocationActivity"
	    android:launchMode="singleTop"
	    android:theme="@style/TransparentStyle" />
	<activity
	    android:name=".permission.activity.PermissionActivity"
	    android:launchMode="singleTop"
	    android:theme="@style/TransparentStyle" />
其中，PermissionActivity是必须要注册的，其余的为可选

### 创建样式
	<style name="TransparentStyle" parent="Theme.AppCompat.Light.NoActionBar">
        <item name="android:windowBackground">@android:color/transparent</item>
        <item name="android:windowIsTranslucent">true</item>
    </style>

### 注册权限
在清单文件中注册我们需要的权限  

	<uses-permission android:name="android.permission.CAMERA" />

### 申请权限
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
### 注意
如果使用了权限申请前的回调，回调方法`beforePermission()`中会传入`ApplyPermissionTask`对象,  
需要调用`ApplyPermissionTask.run()`，才能完成权限的申请  
否则会中断权限申请流程