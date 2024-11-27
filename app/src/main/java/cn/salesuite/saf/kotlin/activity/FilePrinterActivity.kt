package cn.salesuite.saf.kotlin.activity

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import cn.salesuite.saf.kotlin.domain.User
import com.safframework.log.L
import com.safframework.log.printer.FilePrinter
import com.safframework.log.printer.file.FileBuilder


/**
 *
 * @FileName:
 *          cn.salesuite.saf.kotlin.activity.FilePrinterActivity
 * @author: Tony Shen
 * @date: 2019-10-03 01:41
 * @version: V1.0 <描述当前版本功能>
 */
class FilePrinterActivity : Activity() {

    private lateinit var filePrinter:FilePrinter
    private val REQUEST_CODE = 0



    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {// Android 13 及以上

            if (!Environment.isExternalStorageManager()) {
                val intent = Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
                startActivityForResult(intent, REQUEST_CODE)
            } else {
                filePrinter = FileBuilder().folderPath("/storage/emulated/0/logs").build()
                L.addPrinter(filePrinter)
            }
        } else { // Android 12 及以下
            if (hasStoragePermissions(this)) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_CODE)
            } else {
                filePrinter = FileBuilder().folderPath("/storage/emulated/0/logs").build()
                L.addPrinter(filePrinter)
            }
        }

        L.i("111321frehtyjuyikuloil'0[xwrgrtehcytbk8ynfggrgr4hytj")

        val u = User()
        u.userName = "tony"
        u.password = "123456"

        val map = HashMap<String, User>()
        map["tony"] = u
        map["tt"] = u
        L.json(map)

        val map2 = HashMap<String, String>()
        map2["tony"] = "shen"
        map2["tt"] = "ziyu"
        L.json(map2)

        val map3 = HashMap<String, Boolean>()
        map3["tony"] = true
        map3["tt"] = false
        L.json(map3)
    }

    /**
     * 权限检查
     * @param context
     * @return
     */
    private fun hasStoragePermissions(context: Context): Boolean {
        //版本判断，如果比android 13 就走正常的权限获取
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            val readPermission = ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            val writePermission = ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            return readPermission == PackageManager.PERMISSION_GRANTED && writePermission == PackageManager.PERMISSION_GRANTED
        } else {
            val audioPermission =
                ContextCompat.checkSelfPermission(context, Manifest.permission.READ_MEDIA_AUDIO)
            val imagePermission =
                ContextCompat.checkSelfPermission(context, Manifest.permission.READ_MEDIA_IMAGES)
            val videoPermission =
                ContextCompat.checkSelfPermission(context, Manifest.permission.READ_MEDIA_VIDEO)
            return audioPermission == PackageManager.PERMISSION_GRANTED && imagePermission == PackageManager.PERMISSION_GRANTED && videoPermission == PackageManager.PERMISSION_GRANTED
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

        if (requestCode==0) {
            filePrinter = FileBuilder().folderPath("/storage/emulated/0/logs").build()
            L.addPrinter(filePrinter)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        L.removePrinter(filePrinter)
    }
}