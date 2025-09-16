package com.example.miseryreminder

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.net.toUri

fun openGivenProfile(context: Context, profileUrl: String, packageName: String){
    val pm = context.packageManager
    val isInstalled = try {
        pm.getPackageInfo(packageName, 0)
        true
    } catch (e: PackageManager.NameNotFoundException){
        false
    }

    if (isInstalled){
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = profileUrl.toUri()
            setPackage(packageName)
        }
        context.startActivity(intent)
    } else {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = profileUrl.toUri()
        }
        context.startActivity(intent)
    }
}