package com.example.util

import android.app.DownloadManager
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.OutputStream

object AppDownloadManager {

    data class DownloadResult(
        val isSuccess: Boolean,
        val filePath: String = "",
        val uri: Uri? = null,
        val message: String
    )

    fun getApkFileSize(context: Context): String {
        return try {
            val sourceDir = context.applicationInfo.sourceDir
            val file = File(sourceDir)
            if (file.exists()) {
                val sizeInMb = file.length().toDouble() / (1024 * 1024)
                String.format("%.1f MB", sizeInMb)
            } else {
                "24.5 MB"
            }
        } catch (e: Exception) {
            "24.5 MB"
        }
    }

    fun downloadApkToDevice(context: Context): DownloadResult {
        return try {
            val sourceDir = context.applicationInfo.sourceDir
            val srcFile = File(sourceDir)

            if (!srcFile.exists()) {
                return DownloadResult(
                    isSuccess = false,
                    message = "فایلی ئەسڵی APK نەدۆزرایەوە."
                )
            }

            val fileName = "KalarEdit_v1.0.apk"
            val mimeType = "application/vnd.android.package-archive"

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val values = ContentValues().apply {
                    put(MediaStore.Downloads.DISPLAY_NAME, fileName)
                    put(MediaStore.Downloads.MIME_TYPE, mimeType)
                    put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
                    put(MediaStore.Downloads.IS_PENDING, 1)
                }

                val resolver = context.contentResolver
                val itemUri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values)
                    ?: throw IllegalStateException("نەتوانرا لە داونلۆد تۆمار بکرێت")

                resolver.openOutputStream(itemUri)?.use { outStream ->
                    FileInputStream(srcFile).use { inStream ->
                        inStream.copyTo(outStream)
                    }
                }

                values.clear()
                values.put(MediaStore.Downloads.IS_PENDING, 0)
                resolver.update(itemUri, values, null, null)

                // Also make a copy in cache for instant installer opening
                val cacheApk = File(context.cacheDir, fileName)
                FileInputStream(srcFile).use { inStream ->
                    FileOutputStream(cacheApk).use { outStream ->
                        inStream.copyTo(outStream)
                    }
                }

                DownloadResult(
                    isSuccess = true,
                    filePath = "Download/$fileName",
                    uri = itemUri,
                    message = "ئەپی Kalar Edit بە سەرکەوتوویی دابەزییە ناو فۆڵدەری داونلۆدی مۆبایلەکەت (Download/$fileName)"
                )
            } else {
                val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                if (!downloadsDir.exists()) downloadsDir.mkdirs()
                val targetFile = File(downloadsDir, fileName)

                FileInputStream(srcFile).use { inStream ->
                    FileOutputStream(targetFile).use { outStream ->
                        inStream.copyTo(outStream)
                    }
                }

                DownloadResult(
                    isSuccess = true,
                    filePath = targetFile.absolutePath,
                    uri = Uri.fromFile(targetFile),
                    message = "ئەپی Kalar Edit دابەزییە سەر: ${targetFile.absolutePath}"
                )
            }
        } catch (e: Exception) {
            DownloadResult(
                isSuccess = false,
                message = "هەڵە لە داگرتنی ئەپ: ${e.localizedMessage ?: "نەتوانرا دابەزێنرێت"}"
            )
        }
    }

    fun installDownloadedApk(context: Context) {
        try {
            val fileName = "KalarEdit_v1.0.apk"
            val cacheApk = File(context.cacheDir, fileName)
            if (!cacheApk.exists()) {
                // copy from sourceDir
                val srcFile = File(context.applicationInfo.sourceDir)
                FileInputStream(srcFile).use { inStream ->
                    FileOutputStream(cacheApk).use { outStream ->
                        inStream.copyTo(outStream)
                    }
                }
            }

            val apkUri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.provider",
                cacheApk
            )

            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(apkUri, "application/vnd.android.package-archive")
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_GRANT_READ_URI_PERMISSION
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            // Fallback: prompt user to open Downloads folder
            try {
                val intent = Intent(DownloadManager.ACTION_VIEW_DOWNLOADS).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
                context.startActivity(intent)
            } catch (_: Exception) {
            }
        }
    }

    fun shareApkFile(context: Context) {
        try {
            val fileName = "KalarEdit_v1.0.apk"
            val cacheApk = File(context.cacheDir, fileName)
            if (!cacheApk.exists()) {
                val srcFile = File(context.applicationInfo.sourceDir)
                FileInputStream(srcFile).use { inStream ->
                    FileOutputStream(cacheApk).use { outStream ->
                        inStream.copyTo(outStream)
                    }
                }
            }

            val apkUri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.provider",
                cacheApk
            )

            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "application/vnd.android.package-archive"
                putExtra(Intent.EXTRA_STREAM, apkUri)
                putExtra(Intent.EXTRA_SUBJECT, "ئەپی Kalar Edit بۆ دەستکاریکردنی وێنە")
                putExtra(Intent.EXTRA_TEXT, "داگرتنی ئەپی فەرمی Kalar Edit (دروستکراوە لەلایەن SIVER IT)")
                flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            }
            context.startActivity(Intent.createChooser(shareIntent, "هاوبەشکردنی ئەپی Kalar Edit"))
        } catch (e: Exception) {
            // Fallback share link
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, "داگرتنی ئەپی Kalar Edit لە دروستکراوەکانی SIVER IT: https://ais-pre-mjvnd2nckeguamvtn33g2u-904660814893.europe-west2.run.app")
            }
            context.startActivity(Intent.createChooser(shareIntent, "هاوبەشکردن"))
        }
    }

    fun openWebDirectLink(context: Context) {
        try {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://ais-pre-mjvnd2nckeguamvtn33g2u-904660814893.europe-west2.run.app")
            ).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
        } catch (_: Exception) {
        }
    }
}
