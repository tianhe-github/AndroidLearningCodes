//package com.he.share
//
//import android.app.Activity
//import android.content.ClipboardManager
//import android.content.Context
//import android.content.Intent
//import android.graphics.Bitmap
//import android.net.Uri
//import android.os.Build
//import android.os.Environment
//import androidx.core.content.FileProvider
//import com.bumptech.glide.Glide
//import com.bumptech.glide.request.FutureTarget
//import com.gamepind.base.extension.toast
//import com.gamepind.soulmate.R
//import com.gamepind.soulmate.mvp.model.bean.ShareInfo
//import java.io.File
//import java.io.FileNotFoundException
//import java.io.FileOutputStream
//import java.io.IOException
//import java.util.concurrent.Executors
//
//
//object ShareUtil2 {
//    private const val TITLE_SHARE = "share"
//    private const val FILE_NAME = "pfg_share_image.jpg"
//    private const val WHATSAPP = "whatsapp"
//    private const val FACEBOOK = "facebook"
//    private const val MESSAGE = "message"
//    private const val COPYLINK = "copylink"
//    private const val MORE = "more"
//
//
//    fun share(context: Context, type: String, shareInfo: ShareInfo) {
//        when (type) {
//            WHATSAPP -> {
//                if (ApiUtils.isApplicationAvaiable(context, "com.whatsapp")) {
//                    Executors.newSingleThreadExecutor().execute {
//                        val url = shareInfo.contentImageUrl
//                        val bitmapTarget: FutureTarget<Bitmap> = Glide.with(context)
//                                .asBitmap()
//                                .load(url)
//                                .submit()
//                        val bitmap = bitmapTarget.get()
//                        val fileUri = saveBitmap(context, bitmap)
//                        Glide.with(context).clear(bitmapTarget)
//
//                        val shareIntent = Intent(Intent.ACTION_SEND)
//                        shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareInfo.title)
//                        shareIntent.putExtra(Intent.EXTRA_TEXT, shareInfo.getMessage())
//                        shareIntent.`package` = "com.whatsapp"
//                        shareIntent.type = "image/*"
//                        shareIntent.putExtra(Intent.EXTRA_STREAM, fileUri)
//                        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
//                        if (shareIntent.resolveActivity(context.packageManager) != null) {
//                            val chooser = Intent.createChooser(shareIntent, TITLE_SHARE)
//                            if (context !is Activity) {
//                                chooser.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//                            }
//                            context.startActivity(chooser)
//                        }
//                    }
//                } else {
//                    context.toast(context.getString(R.string.please_install_whatsapp))
//                }
//            }
//            FACEBOOK -> {
//                if (ApiUtils.isApplicationAvaiable(context, "com.facebook.katana")) {
//                    val shareIntent = Intent(Intent.ACTION_SEND)
//                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareInfo.title)
//                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareInfo.getMessage())
//                    shareIntent.`package` = "com.facebook.katana"
//                    shareIntent.type = "text/plain"
//                    if (shareIntent.resolveActivity(context.packageManager) != null) {
//                        val chooser = Intent.createChooser(shareIntent, TITLE_SHARE)
//                        if (context !is Activity) {
//                            chooser.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//                        }
//                        context.startActivity(chooser)
//                    }
//                } else {
//                    context.toast(context.getString(R.string.please_install_facebook))
//                }
//            }
//            MESSAGE -> {
//                val smsToUri = Uri.parse("smsto:")
//                val shareIntent = Intent(Intent.ACTION_VIEW, smsToUri)
//                shareIntent.putExtra("sms_body", shareInfo.getMessage())
//                if (shareIntent.resolveActivity(context.packageManager) != null) {
//                    val chooser = Intent.createChooser(shareIntent, TITLE_SHARE)
//                    if (context !is Activity) {
//                        chooser.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//                    }
//                    context.startActivity(chooser)
//                }
//            }
//            COPYLINK -> {
//                val cm = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
//                cm.text = shareInfo.deepLink
//                context.toast(context.getString(R.string.share_link_is_copied))
//            }
//            MORE -> {
//                val shareIntent = Intent(Intent.ACTION_SEND)
//                shareIntent.type = "text/plain"
//                shareIntent.putExtra(Intent.EXTRA_TEXT, shareInfo.getMessage())
//                if (shareIntent.resolveActivity(context.packageManager) != null) {
//                    val chooser = Intent.createChooser(shareIntent, TITLE_SHARE)
//                    if (context !is Activity) {
//                        chooser.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//                    }
//                    context.startActivity(chooser)
//                }
//
//            }
//        }
//    }
//
//    private fun saveBitmap(context: Context, bm: Bitmap ): Uri? {
//        try {
//            val dir = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
//            val file = File(dir, FILE_NAME)
//            if (!file.exists()) {
//                file.parentFile?.mkdirs()
//                file.createNewFile()
//            }
//            val out = FileOutputStream(file)
//            bm.compress(Bitmap.CompressFormat.JPEG, 80, out)
//            out.flush()
//            out.close()
//
//            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                FileProvider.getUriForFile(context, context.applicationContext.packageName + ".provider", file)
//            } else {
//                Uri.fromFile(file)
//            }
//        } catch (e: FileNotFoundException) {
//            e.printStackTrace()
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//        return null
//    }
//}
