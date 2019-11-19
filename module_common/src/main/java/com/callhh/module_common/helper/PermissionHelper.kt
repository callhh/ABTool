package com.callhh.module_common.helper

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.PowerManager
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.widget.Toast
import java.util.*

/**
 * Email: thebititmin@outlook.com
 * Blog: Bitmin.tech
 * 危险权限处理辅助类
 */
object PermissionHelper {

    const val NAME = "RequestBatteryOptimizations"
    const val IS_NEED = "isNeed"

    private var needRequestBatteryOptimizations: Boolean? = null

    private val unGrantedPermissions: ArrayList<String> by lazy { ArrayList<String>() } //记录未通过的权限

    fun checkPermission(activity: Activity, permission: String): Boolean {
        return PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(activity, permission)
    }

    private fun getIsNeedRequestBatteryOptimizations(context: Context): Boolean {
        if (needRequestBatteryOptimizations == null) {
            val preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE)
            needRequestBatteryOptimizations = preferences.getBoolean(IS_NEED, true)
        }
        return needRequestBatteryOptimizations!!
    }

    /**
     * 申请权限
     * 如果已经申请通过的权限不再申请
     */
    @Synchronized
    fun requestPermissions(
        activity: Activity,
        vararg permissions: String,
        requestCode: Int = 0,
        requestPrompt: String = ""
    ) {
        unGrantedPermissions.clear()
        permissions.forEach {
            //用户上次点击拒绝权限，为 true，用户点击不在提示，返回 false
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, it)) {
                if (requestPrompt.isNotEmpty()) {
                    Toast.makeText(activity, requestPrompt, Toast.LENGTH_LONG).show()
                }
            }
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(activity, it)) {
                unGrantedPermissions.add(it)
            }
        }
        if (unGrantedPermissions.isEmpty()) {
            return
        }
        ActivityCompat.requestPermissions(activity, unGrantedPermissions.toArray(emptyArray()), requestCode)
    }

    /**
     * 申请电池白名单
     */
    fun requestBatteryOptimizations(context: Context) {
        if (!getIsNeedRequestBatteryOptimizations(context)) {
            return
        }
        // 在Android 6.0及以上系统，若定制手机使用到doze模式，请求将应用添加到白名单。
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val packageName = context.applicationContext.packageName
            val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
            val isIgnoring = powerManager.isIgnoringBatteryOptimizations(packageName)
            if (!isIgnoring) {
                val intent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS)
                intent.data = Uri.parse("package:$packageName")
                try {
                    context.startActivity(intent)
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
            }
        }
    }

    fun requestLocationPermissions(activity: Activity, requestCode: Int = 0) {
        requestPermissions(
            activity,
            Manifest.permission.INTERNET,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,
            Manifest.permission.CHANGE_WIFI_STATE,
            requestCode = requestCode,
            requestPrompt = "为使用地图功能，请允许必要权限"
        )
    }
}